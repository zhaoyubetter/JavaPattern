package com.better.pattern.decorator3

import com.better.pattern.decorator3.after.Phone
import com.better.pattern.decorator3.after.abs.BlueToothEarPhone
import com.better.pattern.decorator3.after.abs.TieMo
import com.better.pattern.decorator3.before.NormalPhone
import com.better.pattern.decorator3.before.PhoneWithBluetooth

/**
 * Created by zhaoyu on 2016/11/23.
 */
class Client {
    static void main(String[] args) {
        before();
        // after();
    }

    def static before() {
        def phone = new PhoneWithBluetooth("华为", 3000);
        phone.getDescription();

        phone = new NormalPhone("苹果", 5000);
        phone.getDescription();

        // 如需要创建多个类呢，是不是要疯掉呢
    }

    def static after() {
        def phone = new Phone("华为", 3000);
        def blueTooth = new BlueToothEarPhone(phone);
        println(blueTooth.getDescription() + " 价格：" + blueTooth.cost());

        def tiemmo = new TieMo(blueTooth);
        println(tiemmo.getDescription() + " 价格：" + tiemmo.cost());
    }
}
