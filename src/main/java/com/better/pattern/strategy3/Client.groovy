package com.better.pattern.strategy3

import com.better.pattern.strategy3.after.CashierDeskContext
import com.better.pattern.strategy3.after.starategy.IPriceStrategy
import com.better.pattern.strategy3.after.starategy.OverFiveHundredStrategy
import com.better.pattern.strategy3.after.starategy.OverOneHundredStrategy
import com.better.pattern.strategy3.after.starategy.OverThousandStrategy
import com.better.pattern.strategy3.before.CashierDesk

/**
 * Created by zhaoyu on 2016/11/23.
 */
class Client {

    // 添加新需求， 满 100 ，超过 100 的部分9折；
    // 老板发疯了，全场5折

    static void main(args) {
        //before();
        after();
    }

    static void before() {
        double cost = 2000;

        CashierDesk cashierDesk = new CashierDesk();
        double result = cashierDesk.daZhe(2000);
        println(String.format("原价：%.2f, 现优惠价格：%.2f", cost, result));

        // 来新需求了，
    }

    static void after() {
        // 具体的策略由客户端来定

        double cost = 560;
//
//        CashierDeskContext context = new CashierDeskContext();
//        context.setStrategy(new OverFiveHundredStrategy());
//        double result = context.daZhe(cost)
//        println(String.format("原价：%.2f, 现优惠价格：%.2f", cost, result));

//        cost = 560;
//        context.setStrategy(new OverFiveHundredStrategy());
//        println(String.format("原价：%.2f, 现优惠价格：%.2f", cost, context.daZhe(cost)));
//
//        cost = 120;
//        context.setStrategy(new OverOneHundredStrategy());
//        println(String.format("原价：%.2f, 现优惠价格：%.2f", cost, context.daZhe(cost)));


        // 老板发疯策略，

        CashierDeskContext context = new CashierDeskContext();
        context.setStrategy(new IPriceStrategy() {
            @Override
            double getDazhePrice(double price) {
                return price / 2;
            }
        });

        println(String.format("原价：%.2f, 现优惠价格：%.2f", cost, context.daZhe(cost)));

    }
}
