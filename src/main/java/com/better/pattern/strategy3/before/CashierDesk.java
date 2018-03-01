package com.better.pattern.strategy3.before;

/**
 * 收银台
 * Created by zhaoyu on 2016/11/24.
 */
public class CashierDesk {

    public double daZhe(double originalPrice) {
        double result = originalPrice;
        if (originalPrice > 1000) {
            result = originalPrice - 200;
        } else if (originalPrice > 500) {
            result = originalPrice * 0.9;
        } else if (originalPrice > 200) {
            result = 200 + (originalPrice - 200) * 0.8;
        } else if(originalPrice > 100) {
            result = 100 + ((originalPrice - 100) * 0.9);
        }
        return result;
    }

}
