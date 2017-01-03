package com.better.pattern.compound.goose_adapter;

import com.better.pattern.compound.abs.Quackable;

/**
 * 适配器类
 * Created by zhaoyu on 2017/1/2.
 */
public class GooseAdapter implements Quackable {

    Goose goose;

    public GooseAdapter(Goose goose) {
        this.goose = goose;
    }

    @Override
    public void quack() {
        goose.honk();
    }
}
