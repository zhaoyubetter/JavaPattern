package com.better.pattern.decorator3.after;

import com.better.pattern.decorator3.after.abs.Component;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public class Phone extends Component {

    private String name;
    private double price;

    public Phone(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double cost() {
        return price;
    }

    @Override
    public String getDescription() {
        return "手机 " + name;
    }
}
