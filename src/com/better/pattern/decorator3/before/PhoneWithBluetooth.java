package com.better.pattern.decorator3.before;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class PhoneWithBluetooth extends AbstractPhone {

    final double cost = 100;

    public PhoneWithBluetooth(String name, double price) {
        super(name, price);
    }

    @Override
    public void getDescription() {
        System.out.println(name + "（带蓝牙耳机）手机，" + ", 需花费￥ " + (price + cost));
    }
}
