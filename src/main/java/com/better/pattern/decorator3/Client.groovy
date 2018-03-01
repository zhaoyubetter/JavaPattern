package com.better.pattern.decorator3

import com.better.pattern.decorator3.after.Phone
import com.better.pattern.decorator3.after.abs.BlueToothEarPhone
import com.better.pattern.decorator3.after.abs.PhoneShell
import com.better.pattern.decorator3.after.abs.TieMo
import com.better.pattern.decorator3.before.NormalPhone
import com.better.pattern.decorator3.before.PhoneWithBluetooth

/**
 * Created by zhaoyu on 2016/11/23.
 */
class Client {

    // 需求变更：
    // 1. 我要买个带保护壳，带蓝牙耳机，带2层贴膜，的手机，怎么搞？

    static void main(String[] args) {
        //before();
        after();
    }

    def static before() {
        def phone = new PhoneWithBluetooth("华为", 3000);
        phone.getDescription();

//        def phone = new NormalPhone("苹果", 5000);
//        phone.getDescription();

        // 针对新需求 1
    }

    def static after() {
        def phone = new Phone("华为", 3000);

//        def blueTooth = new BlueToothEarPhone(phone);
//        println(blueTooth.getDescription() + " 价格：" + blueTooth.cost());
//
//        def tiemmo = new TieMo(blueTooth);
//        println(tiemmo.getDescription() + " 价格：" + tiemmo.cost());

        // 针对新需求
        def shell = new PhoneShell(phone);      // 带个保护壳
        def blueTooth2 = new BlueToothEarPhone(shell);  // 带个蓝牙
        def tiemo1 = new TieMo(blueTooth2);     // 贴膜1
        def tiemo2 = new TieMo(tiemo1);     // 贴膜2
        print(tiemo2.getDescription() + " 价格：" + tiemo2.cost());
    }
}
