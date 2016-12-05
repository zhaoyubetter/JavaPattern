package com.better.pattern.proxy.abs;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 远程调用接口
 * Created by zhaoyu on 2016/11/22.
 */
public interface GumballMachineRemote extends Remote {
    int getCount() throws RemoteException;

    String getLocation() throws RemoteException;

    State getCurrentState() throws RemoteException;
}
