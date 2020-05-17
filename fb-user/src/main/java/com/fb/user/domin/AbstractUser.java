package com.fb.user.domin;

public abstract class AbstractUser {

    private Long uid;

    private String name;

    private Integer count;


    /**
     * 判断用户登录
     * @return
     */
    public boolean login() {
        return false;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
