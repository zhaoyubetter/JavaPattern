package com.better.pattern.strategy3.after.starategy;

/**
 * Created by zhaoyu on 2016/11/24.
 */
public class OverThousandStrategy implements IPriceStrategy {

    @Override
    public double getDazhePrice(double price) {
        return price > 1000 ? price - 200 : price;
    }
}
