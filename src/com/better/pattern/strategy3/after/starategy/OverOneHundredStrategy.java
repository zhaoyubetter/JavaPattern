package com.better.pattern.strategy3.after.starategy;

/**
 * Created by zhaoyu on 2016/11/24.
 */
public class OverOneHundredStrategy implements IPriceStrategy {

    @Override
    public double getDazhePrice(double price) {
        return price > 100 ? 100 + ((price - 100) * 0.9) : price;
    }
}
