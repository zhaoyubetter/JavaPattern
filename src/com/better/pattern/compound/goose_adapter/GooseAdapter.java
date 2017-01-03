package com.better.pattern.compound.goose_adapter;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observable;
import com.better.pattern.compound.observe.Observer;

/**
 * 适配器类
 * Created by zhaoyu on 2017/1/2.
 */
public class GooseAdapter implements Quackable {

    Goose goose;

    Observable observable;

    public GooseAdapter(Goose goose) {
        this.goose = goose;
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        goose.honk();
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
