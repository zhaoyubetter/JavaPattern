package com.better.pattern.proxy;

import com.better.pattern.proxy.abs.GumballMachineRemote;

import java.rmi.RemoteException;

/**
 * 监控类
 * Created by zhaoyu on 2016/11/22.
 */
public class GumballMonitor {
    // 远程对象
    private GumballMachineRemote machine;

    public GumballMonitor(GumballMachineRemote machine) {
        this.machine = machine;
    }

    public void report() throws RemoteException {
        System.out.println("Gumball Machine: " + machine.getLocation());
        System.out.println("还有: " + machine.getCount() + " 个糖果");
        System.out.println("当前状态： " + machine.getCurrentState());
    }
}
