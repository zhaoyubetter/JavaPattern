package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observable;
import com.better.pattern.compound.observe.Observer;

/**
 * 橡皮鸭子
 * Created by zhaoyu on 2017/1/2.
 */
public class RubberDuck implements Quackable {

    Observable observable;

    public RubberDuck() {
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("橡皮鸭子 发出声音");
        observable.notifyObservers();
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
