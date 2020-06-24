package com.fb.user.domain;

import com.fb.user.repository.UserPO;

/**
 * 入驻用户
 */
public class MerchantUser extends AbstractUser {

    //以后可能会扩展资质字段，比如用户资质等等


    @Override
    public AbstractUser convertByPO(UserPO userPO) {
        return null;
    }

    @Override
    public UserPO convert2PO() {
        return null;
    }
}
