package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observable;
import com.better.pattern.compound.observe.Observer;

/**
 * 鸭鸣器
 * Created by zhaoyu on 2017/1/2.
 */
public class DuckCall implements Quackable {

    Observable observable;

    public DuckCall() {
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("鸭鸣器 发出声音");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }
}
