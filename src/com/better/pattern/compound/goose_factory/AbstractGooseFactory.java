package com.better.pattern.compound.goose_factory;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.goose_adapter.Goose;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public abstract class AbstractGooseFactory {
    public abstract Goose createGoose();

    public abstract Quackable createQuackGoose();
}
