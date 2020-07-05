package com.fb.web.utils;

import com.fb.web.exception.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@ApiModel("接口返回结果模型")
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonObject<T> implements Serializable {


    private static final int DEFAULT_ERR_CODE = -1;
    private static final int CORRECT_CODE = 0;
    private static final long serialVersionUID = 1L;


    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    /**
     * 调用结果代码 0代表成功 反之代表失败
     */
    @ApiModelProperty(value = "调用结果代码 0代表成功 反之代表失败", required = true)
    private Integer code = 0;

    /**
     * 出错信息， 输出出错码对应的出错信息
     */
    @ApiModelProperty(value = "调用结果代码 0代表成功 反之代表失败")
    private String msg = "成功";


    /**
     * 生成一个标准的正确返回对象，{"code": 0}
     */
    public JsonObject(T data) {
        this.code = CORRECT_CODE;
        this.data = data;
    }

    /**
     * 生成一个标准的错误返回DTO，{"code": != 0,"errmsg" : "msg"}
     *
     * @param code
     * @param msg
     * @return
     */
    public static JsonObject newErrorJsonObject(Integer code, String msg) {
        JsonObject errorReturnObject = new JsonObject();
        errorReturnObject.setCode(code);
        errorReturnObject.setMsg(msg);
        return errorReturnObject;
    }

    /**
     * 生成一个标准的data为Map正确返回对象，{"code": 0}
     *
     * @return 标准正确返回对象，不带data值
     */
    public static JsonObject newCorrectJsonObject(Object object) {
        JsonObject correctJsonObject = new JsonObject(object);
        return correctJsonObject;
    }

    /**
     * 生成一个标准的data正确返回对象，{"code": 0}
     *
     * @return 标准正确返回对象，不带data值
     */
    public static JsonObject newCorrectMapDataJsonObject() {
        JsonObject<Map<String, Object>> correctJsonObject = new JsonObject<Map<String, Object>>(new HashMap<String, Object>());
        return correctJsonObject;
    }


    /**
     * 生成一个标准的错误返回DTO，{"code": -1,"errmsg" : "msg"}
     *
     * @param msg
     * @return
     */
    public static JsonObject newErrorJsonObject(String msg) {
        return JsonObject.newErrorJsonObject(JsonObject.DEFAULT_ERR_CODE, msg);
    }

    public static JsonObject newErrorJsonObject(BaseResponse baseResponse) {
        return JsonObject.newErrorJsonObject(baseResponse.getCode(), baseResponse.getDesc());
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return CORRECT_CODE == this.code;
    }

    /**
     * 拿到容器中的key对应的值
     *
     * @param key
     * @return
     */
    public Object getValueDate(String key) {
        if (this.data == null) {
            return null;
        }

        if (data instanceof Map) {
            return ((Map<String, Object>) data).get(key);
        } else {
            throw new RuntimeException("the value object is not-map object");
        }

    }

    /**
     * 使用List<Object>作为业务数据容器，添加业务数据
     *
     * @param object
     * @return
     */
    public JsonObject putData(T object) {
        if (object == null) {
            return this;
        }

        if (this.data == null) {
            this.data = object;
        }
        return this;
    }

    /**
     * 使用一个HashMap作为业务数据容器，添加业务数据
     *
     * @param key
     * @param value
     */
    public JsonObject putData(String key, Object value) {

        if (StringUtils.isEmpty(key)) {
            return this;
        }

        if (data instanceof Map) {
            ((Map<String, Object>) data).put(key, value);
        } else {
            throw new RuntimeException("Can't put k-v into not-map object");
        }
        return this;
    }
}