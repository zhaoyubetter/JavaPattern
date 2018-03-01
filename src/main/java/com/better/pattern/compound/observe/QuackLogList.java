package com.better.pattern.compound.observe;

/**
 * Created by zhaoyu on 2017/1/3.
 */
public class QuackLogList implements Observer {
    @Override
    public void update(QuackObservable duck) {
        System.out.println("收到鸭叫消息：" + duck + " just quacked.");
    }
}
