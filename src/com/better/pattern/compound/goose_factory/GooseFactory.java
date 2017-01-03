package com.better.pattern.compound.goose_factory;

import com.better.pattern.compound.abs.Quackable;
import com.better.pattern.compound.goose_adapter.Goose;
import com.better.pattern.compound.goose_adapter.GooseAdapter;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class GooseFactory extends AbstractGooseFactory {
    @Override
    public Goose createGoose() {
        return new Goose();
    }

    @Override
    public Quackable createQuackGoose() {
        return new GooseAdapter(new Goose());
    }
}
