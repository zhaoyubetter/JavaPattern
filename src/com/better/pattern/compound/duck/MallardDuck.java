package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class MallardDuck implements Quackable {
    @Override
    public void quack() {
        System.out.println("Quack : " + "嘎嘎叫");
    }
}
