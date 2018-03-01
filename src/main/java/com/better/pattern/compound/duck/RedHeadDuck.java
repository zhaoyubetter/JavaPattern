package com.better.pattern.compound.duck;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observable;
import com.better.pattern.compound.observe.Observer;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class RedHeadDuck implements Quackable {

    Observable observable;

    public RedHeadDuck() {
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("红头鸭子 : " + "嘎嘎叫");
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
