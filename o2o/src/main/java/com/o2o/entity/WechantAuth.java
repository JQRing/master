package com.o2o.entity;

import java.util.Date;

public class WechantAuth {
    private Long wechantAuthId;
    private String openId;
    private Date createTime;
    private PersonInfo personInfo;

    public Long getWechantAuthId() {
        return wechantAuthId;
    }

    public void setWechantAuthId(Long wechantAuthId) {
        this.wechantAuthId = wechantAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
