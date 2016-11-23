package com.better.pattern.decorator3.after.abs;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class TieMo extends Decorator {

    public TieMo(Component component) {
        super(component);
    }

    @Override
    public double cost() {
        return component.cost() + 10;
    }

    @Override
    public String getDescription() {
        return component.getDescription() + " -（含贴膜）- ";
    }
}
