package com.better.pattern.proxy.abs;

import java.io.Serializable;

/**
 * 状态模式接口
 * Created by zhaoyu on 2016/11/18.
 */
public interface State extends Serializable {
    void insertCoin();

    void ejectCoin();

    void turnCrank();

    void dispense();
}
