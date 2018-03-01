package com.better.pattern.compound.duck_factory;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.duck.DuckCall;
import com.better.pattern.compound.duck.MallardDuck;
import com.better.pattern.compound.duck.RedHeadDuck;
import com.better.pattern.compound.duck.RubberDuck;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class DuckFactory extends AbstractDuckFactory {
    @Override
    public Quackable createMallardDuck() {
        return new MallardDuck();
    }

    @Override
    public Quackable createRedHeadDuck() {
        return new RedHeadDuck();
    }

    @Override
    public Quackable createDuckCall() {
        return new DuckCall();
    }

    @Override
    public Quackable createRubberDuck() {
        return new RubberDuck();
    }
}
