package com.fb.order.dto;

import lombok.Data;

@Data
public class OrderUserInfoBO {

    private Long id;
    /*用户名称*/
    private String userName;

    /*用户电话*/
    private String userPhone;

    /*身份证号*/
    private String userCardNo;

}
