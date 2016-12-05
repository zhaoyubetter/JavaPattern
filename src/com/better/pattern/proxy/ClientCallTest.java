package com.better.pattern.proxy;

import com.better.pattern.proxy.abs.GumballMachineRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 客户端调用
 * Created by zhaoyu on 2016/11/22.
 */
public class ClientCallTest {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String[] locs = {
                "rmi://better.com/gumbailMachine"
        };

        GumballMonitor[] monitors = new GumballMonitor[locs.length];

        for (int i = 0; i < monitors.length; i++) {
            GumballMachineRemote remote = (GumballMachineRemote) Naming.lookup(locs[i]);
            monitors[i] = new GumballMonitor(remote);
            monitors[i].report();
        }
    }
}
