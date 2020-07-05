package com.fb.common.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadResult {
    private boolean status;
    private String url;

}
