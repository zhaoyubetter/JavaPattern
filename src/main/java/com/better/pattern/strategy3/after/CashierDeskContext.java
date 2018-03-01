package com.better.pattern.strategy3.after;

import com.better.pattern.strategy3.after.starategy.IPriceStrategy;

/**
 * 收银台
 * Created by zhaoyu on 2016/11/24.
 */
public class CashierDeskContext {

    private IPriceStrategy strategy;

    public void setStrategy(IPriceStrategy strategy) {
        this.strategy = strategy;
    }

    public double daZhe(double originalPrice) {
        if (strategy == null) {
            throw new RuntimeException("没有策略，运行错误");
        }
        return strategy.getDazhePrice(originalPrice);
    }
}
