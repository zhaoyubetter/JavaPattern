package com.better.pattern.proxy.state;

import com.better.pattern.proxy.GumbailMachine;
import com.better.pattern.proxy.abs.State;

import java.util.Random;

/**
 * Created by zhaoyu on 2016/11/18.
 */

public class HasCoinState implements State {

    Random random;
    transient GumbailMachine machine;

    public HasCoinState(GumbailMachine machine) {
        this.machine = machine;
        random = new Random();
    }

    // 此状态下，不恰当的的动作
    @Override
    public void insertCoin() {
        System.out.println("-- 有币状态下， 投币请求--，不能再投额外的币了");
    }

    @Override
    public void ejectCoin() {
        System.out.println("-- 有币状态下， 退币请求--，币已退");
        machine.setCurrentState(machine.getNoCoinState());
    }

    @Override
    public void turnCrank() {
        System.out.println("--有币状态下，转动曲柄--, 请等待");
        int num = random.nextInt(10);    // 20%
        if (num == 0 && machine.getCount() > 1) {
            machine.setCurrentState(machine.getWinState());
        } else {
            machine.setCurrentState(machine.getSoldState());
        }
    }

    // 此状态下，不恰当的的动作
    @Override
    public void dispense() {
        System.out.println("--有币状态下-- dispense 无糖果分配");
    }

    public String toString() {
        return "已投币";
    }
}