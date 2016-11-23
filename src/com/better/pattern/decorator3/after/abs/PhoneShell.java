package com.better.pattern.decorator3.after.abs;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class PhoneShell extends Decorator {

    public PhoneShell(Component component) {
        super(component);
    }

    @Override
    public double cost() {
        return component.cost() + 20;
    }

    @Override
    public String getDescription() {
        return component.getDescription() + " -（含手机壳）- ";
    }
}
