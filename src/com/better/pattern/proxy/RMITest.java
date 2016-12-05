package com.better.pattern.proxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by zhaoyu on 2016/11/22.
 */
public class RMITest {
    public static void main(String[] args) throws MalformedURLException, RemoteException {
        // rmi 注册
        GumbailMachine machineRemote1 = new GumbailMachine("Beijing", 512);
        Naming.rebind("//" + "127.0.0.1" + "/gumbailMachine", machineRemote1);
    }
}
