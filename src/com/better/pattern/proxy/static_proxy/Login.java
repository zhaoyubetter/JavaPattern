package com.better.pattern.proxy.static_proxy;

/**
 * Created by zhaoyu on 2016/12/18.
 */
public class Login implements LoginInterface {
    @Override
    public void doLogin(String name) {
        System.out.println(name + " 登录了系统...");
    }
}
