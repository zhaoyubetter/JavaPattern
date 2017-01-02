package com.better.pattern.proxy.protect.abs;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class PersonBeanImpl implements PersonBean {

    String name;
    String gender;
    int rating;
    int ratingCount = 0;


    public PersonBeanImpl(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public int getHorOrNotRating() {
        if (ratingCount == 0) {
            return 0;
        }

        return (rating / ratingCount);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void setHotOrNotRating(int rating) {
        this.rating += rating;
        ratingCount++;
    }
}
