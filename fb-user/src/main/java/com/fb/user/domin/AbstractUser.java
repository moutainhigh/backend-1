package com.fb.user.domin;

public abstract class AbstractUser {

    private Long uid;

    private String name;

    private Integer count;


    /**
     *
     * @return
     */
    public boolean isMerchant() {
        return this instanceof MerchantUser;
    }

    public Long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
