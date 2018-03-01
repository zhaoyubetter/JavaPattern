package com.better.pattern.proxy.protect.abs;

/**
 * 个人信息接口
 * Created by zhaoyu on 2017/1/2.
 */
public interface PersonBean {
    String getName();

    String getGender();

    int getHorOrNotRating();

    void setName(String name);

    void setGender(String gender);

    void setHotOrNotRating(int rating);
}
