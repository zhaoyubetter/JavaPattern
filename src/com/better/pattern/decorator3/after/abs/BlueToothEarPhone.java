package com.better.pattern.decorator3.after.abs;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class BlueToothEarPhone extends Decorator {

    public BlueToothEarPhone(Component component) {
        super(component);
    }

    @Override
    public double cost() {
        return component.cost() + 200;
    }

    @Override
    public String getDescription() {
        return component.getDescription() + " -（含蓝牙组件）- ";
    }
}
