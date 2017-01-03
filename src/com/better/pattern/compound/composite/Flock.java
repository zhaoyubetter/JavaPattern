package com.better.pattern.compound.composite;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.observe.Observer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 组合模式
 * Created by zhaoyu on 2017/1/3.
 */
public class Flock implements Quackable {

    ArrayList<Quackable> quackers = new ArrayList<>();

    public void add(Quackable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            iterator.next().quack();
        }
    }

    /**
     * 给每个孩子注册观察者监听
     *
     * @param observer
     */
    @Override
    public void registerObserver(Observer observer) {
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            iterator.next().registerObserver(observer);
        }
    }

    /**
     * 每个鸭子负责自己通知观察者
     */
    @Override
    public void notifyObservers() {
    }
}
