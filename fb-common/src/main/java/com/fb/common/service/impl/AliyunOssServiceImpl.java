package com.fb.common.service.impl;

import com.aliyun.oss.OSS;
import com.fb.common.model.UploadResult;
import com.fb.common.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AliyunOssServiceImpl implements OssService {
    // 文件最大50M
    private static long upload_maxsize = 80 * 1024 * 1024;

    // 文件允许格式

    private static String[] ALLOW_FILES = { ".rar", ".doc", ".docx", ".zip",
            ".pdf", ".txt", ".swf", ".xlsx", ".gif", ".png", ".jpg", ".jpeg",
            ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv",
            ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb" };

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    private static final String BUCKET_NAME = "t-bear-test";
    @Value("${oss.endpoint}")
    private String OSS_ENDPOINT;
    @Autowired
    private OSS aliyunOSS;
    private static final String PREFIX = "https://";
    private static final String PIC_PRE_URL = "images/";
    private static final String VIDEO_PRE_URL = "videos/";

    private static final String FILE_SEPARATOR = "/";
    private static final String NAME_SEPARATOR = "_";
    private static final String FILE_POINT = ".";


    /**
     * 上传的目录
     * 目录: 根据年月日归档
     * 文件名: 时间戳 + 随机数
     *
     * @param fileName
     * @return
     */

    private String getFilePath(String fileName, long uid, String fileType) {
        LocalDateTime dateTime = LocalDateTime.now();
        StringBuffer sb = new StringBuffer();
        sb.append(fileType);
        sb.append(dateTime.getYear());
        sb.append(FILE_SEPARATOR);
        sb.append(dateTime.getMonthValue());
        sb.append(FILE_SEPARATOR);
        sb.append(dateTime.getDayOfMonth());
        sb.append(FILE_SEPARATOR);
        sb.append(uid);
        sb.append(NAME_SEPARATOR);
        sb.append(System.currentTimeMillis());
        sb.append(NAME_SEPARATOR);
        sb.append(RandomUtils.nextInt(100, 9999));
        sb.append(FILE_POINT);
        sb.append(StringUtils.substringAfterLast(fileName, "."));
        return sb.toString();
    }

    private String getFileUrl(String bucketName, String filePath) {
        StringBuffer sb = new StringBuffer();
        sb.append(getPrefix(bucketName));
        sb.append(filePath);
        return sb.toString();
    }

    private String getPrefix(String bucketName) {
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX);
        sb.append(bucketName);
        sb.append(FILE_POINT);
        sb.append(OSS_ENDPOINT/*.replace(PRE_URL, FILE_POINT)*/);
        sb.append(FILE_SEPARATOR);
        return sb.toString();
    }

    @Override
    public Optional<UploadResult> uploadPicture(MultipartFile multipartFile, long uid) {
        // 1. 对上传的图片进行校验: 这里简单校验后缀名
        // 另外可通过ImageIO读取图片的长宽来判断是否是图片,校验图片的大小等。
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;  // 只要与允许上传格式其中一个匹配就可以
            }
        }
        UploadResult uploadResult = new UploadResult();
        // 格式错误, 返回与前端约定的error
        if (!isLegal) {
            uploadResult.setStatus(false);
            return Optional.ofNullable(uploadResult);
        }

        // 2. 准备上传API的参数
        String fileName = multipartFile.getOriginalFilename();
        String filePath = this.getFilePath(fileName, uid, PIC_PRE_URL);

        // 3. 上传至阿里OSS
        try {
            aliyunOSS.putObject(BUCKET_NAME, filePath, new ByteArrayInputStream(multipartFile.getBytes()));
        } catch (IOException e) {
            log.error("aliyun putObject is error", e);
            // 上传失败
            uploadResult.setStatus(false);
            return Optional.ofNullable(uploadResult);
        }

        // 上传成功
        uploadResult.setStatus(true);
        // 文件名(即直接访问的完整路径)
        uploadResult.setUrl(getFileUrl(BUCKET_NAME, filePath));
        return Optional.ofNullable(uploadResult);
    }

    @Override
    public Optional<UploadResult> uploadVideo(MultipartFile multipartFile, long uid) {
        boolean isLegal = false;
        for (String type : ALLOW_FILES) {
            if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;  // 只要与允许上传格式其中一个匹配就可以
            }
        }
        UploadResult uploadResult = new UploadResult();
        if (!isLegal) {
            log.warn("uploadVideo file type is error, type={}", multipartFile.getOriginalFilename());
            uploadResult.setStatus(false);
            return Optional.ofNullable(uploadResult);
        }
        if (multipartFile.getSize() > upload_maxsize) {
            log.warn("uploadVideo size type is error, type={}", multipartFile.getOriginalFilename());
            uploadResult.setStatus(false);
            return Optional.ofNullable(uploadResult);
        }
        // 2. 准备上传API的参数
        String fileName = multipartFile.getOriginalFilename();
        String filePath = this.getFilePath(fileName, uid, VIDEO_PRE_URL);

        // 3. 上传至阿里OSS
        try {
            aliyunOSS.putObject(BUCKET_NAME, filePath, new ByteArrayInputStream(multipartFile.getBytes()));
        } catch (IOException e) {
            log.error("aliyun putObject is error", e);
            // 上传失败
            uploadResult.setStatus(false);
            return Optional.ofNullable(uploadResult);
        }

        // 上传成功
        uploadResult.setStatus(true);
        // 文件名(即直接访问的完整路径)
        uploadResult.setUrl(getFileUrl(BUCKET_NAME, filePath));
        return Optional.ofNullable(uploadResult);
    }

    @Override
    public boolean deleteFile(String fileUrl, long uid) {
        if (fileUrl.split(NAME_SEPARATOR)[0].contains(uid + "")) {
            try {
                aliyunOSS.deleteObject(BUCKET_NAME, fileUrl.substring(getPrefix(BUCKET_NAME).length()));
            } catch (Exception e) {
                log.error("aliyun deletePicture is error", e);
                // 上传失败
                return false;
            }
            return true;
        }
        log.info("aliyun deletePicture is no auth, fileUrl={],uid={}", fileUrl, uid);
        return false;
    }


}


