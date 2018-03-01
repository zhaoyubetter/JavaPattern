package com.better.pattern.compound.duck_factory;

import com.better.pattern.compound.abs.Quackable;

/**
 * 抽象工厂类
 * Created by zhaoyu on 2017/1/2.
 */
public abstract class AbstractDuckFactory {
    public abstract Quackable createMallardDuck();

    public abstract Quackable createRedHeadDuck();

    public abstract Quackable createDuckCall();

    public abstract Quackable createRubberDuck();
}
