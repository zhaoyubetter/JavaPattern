package com.better.pattern.decorator3.after.abs;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public abstract class Component {
    /**
     * 组件价格
     *
     * @return
     */
    public abstract double cost();

    /**
     * 组件描述
     */
    public abstract String getDescription();
}
