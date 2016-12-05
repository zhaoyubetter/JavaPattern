package com.better.pattern.strategy3.after.starategy;

/**
 * Created by zhaoyu on 2016/11/24.
 */
public class OverTwoHundredStrategy implements IPriceStrategy {

    @Override
    public double getDazhePrice(double price) {
        return price > 200 ? 200 + (price - 200) * 0.8 : 200;
    }
}
