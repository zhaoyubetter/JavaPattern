package com.better.pattern.observe_2.after;

/**
 * Created by zhaoyu on 2016/12/12.
 */
public interface Subject {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
