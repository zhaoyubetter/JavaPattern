package com.better.pattern.compound.counter_decorator;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observer;

/**
 * 装饰，统计叫声的次数
 * Created by zhaoyu on 2017/1/2.
 */
public class QuackCounter implements Quackable {

    Quackable quackable;
    static int counter = 0;


    public QuackCounter(Quackable quackable) {
        this.quackable = quackable;
    }

    @Override
    public void quack() {
        quackable.quack();
        counter++;
    }

    public static int getQuacks() {
        return counter;
    }

    @Override
    public void registerObserver(Observer observer) {
        quackable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        quackable.notifyObservers();
    }
}
