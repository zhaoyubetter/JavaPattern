package com.better.pattern.compound.observe;


/**
 * Created by zhaoyu on 2017/1/3.
 */
public interface QuackObservable {
    void registerObserver(Observer observer);

    void notifyObservers();
}
