package com.better.pattern.strategy3.after.starategy;

/**
 * Created by zhaoyu on 2016/11/24.
 */
public class OverFiveHundredStrategy implements IPriceStrategy {

    @Override
    public double getDazhePrice(double price) {
        return price > 500 ? price * 0.9 : price;
    }
}
