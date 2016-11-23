package com.better.pattern.decorator3.before;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class NormalPhone extends AbstractPhone {

    final double cost = 0;

    public NormalPhone(String name, double price) {
        super(name, price);
    }

    @Override
    public void getDescription() {
        System.out.println(name + "手机" + ", 需花费￥ " + (price + cost));
    }
}
