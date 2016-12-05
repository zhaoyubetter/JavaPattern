package com.better.pattern.proxy.state;

import com.better.pattern.proxy.GumbailMachine;
import com.better.pattern.proxy.abs.State;

/**
 * 赢家状态
 * Created by zhaoyu on 2016/11/18.
 */
public class WinState implements State {

    transient GumbailMachine machine;

    public WinState(GumbailMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("-- 赢家状态下，投币请求	无意义 --");
    }

    @Override
    public void ejectCoin() {
        System.out.println("-- 赢家状态下，退币请求	无意义 --");
    }

    @Override
    public void turnCrank() {
        System.out.println("-- 赢家状态下，转动请求	无意义 --");
    }

    @Override
    public void dispense() {
        System.out.println("-- 恭喜你，你是赢家，将给你2颗糖 --");
        machine.releaseBall();
        if (machine.getCount() == 0) {
            machine.setCurrentState(machine.getSoldOutState());
        } else {
            machine.releaseBall();
            if (machine.getCount() > 0) {
                machine.setCurrentState(machine.getNoCoinState());
            } else {
                System.out.println("售罄啦。。。。");
                machine.setCurrentState(machine.getSoldOutState());
            }
        }
    }
}
