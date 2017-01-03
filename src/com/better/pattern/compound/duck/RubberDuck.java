package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;

/**
 * 橡皮鸭子
 * Created by zhaoyu on 2017/1/2.
 */
public class RubberDuck implements Quackable {
    @Override
    public void quack() {
        System.out.println("橡皮鸭子 发出声音");
    }
}
