package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;

/**
 * 鸭鸣器
 * Created by zhaoyu on 2017/1/2.
 */
public class DuckCall implements Quackable {
    @Override
    public void quack() {
        System.out.println("鸭鸣器 发出声音");
    }
}
