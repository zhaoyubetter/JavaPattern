package com.better.pattern.compound.observe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 辅助类
 * Created by zhaoyu on 2017/1/3.
 */
public class Observable implements QuackObservable {

    // 观察者
    List<Observer> observers = new ArrayList<>();
    // 被观察者
    QuackObservable duck;

    public Observable(QuackObservable duck) {
        this.duck = duck;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().update(duck);
        }
    }
}
