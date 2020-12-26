package com.fb.common.service;

import com.fb.common.model.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface OssService {

    Optional<UploadResult> uploadPicture(MultipartFile multipartFile, long uid, String flag);

    Optional<UploadResult> uploadVideo(MultipartFile multipartFile, long uid);

    boolean deleteFile(String fileUrl, long uid);


}
