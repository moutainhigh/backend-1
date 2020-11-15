package com.fb.user.relation.service.DTO;

import lombok.Data;

/**
 * @author: pangminpeng
 * @create: 2020-11-07 23:52
 */
@Data
public class UserDTOForRelation {


    public UserDTOForRelation() {};

    public UserDTOForRelation(Long uid, String cityCode) {
        this.uid = uid;
        this.cityCode = cityCode;
    }

    private Long uid;

    private String cityCode;
}
