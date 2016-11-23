package com.better.pattern.decorator3.before;

/**
 * 手机类
 * Created by zhaoyu on 2016/11/23.
 */
public abstract class AbstractPhone {

    protected String name;
    protected double price;

    public AbstractPhone(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void getDescription();
}
