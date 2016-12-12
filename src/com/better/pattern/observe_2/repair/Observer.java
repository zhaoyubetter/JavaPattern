package com.better.pattern.observe_2.repair;

/**
 * Created by zhaoyu on 2016/12/12.
 */
public abstract class Observer {
    protected String name;
    protected String msg;

    public abstract void update(int pressure, int fret);

    public String getMsg() {
        return msg;
    }
}
