package com.better.pattern.proxy.dynamic_proxy;

/**
 * Created by zhaoyu on 2016/12/22.
 */
public class LoginImpl implements LoginInterface {
    @Override
    public void login(String userName) {
        System.out.println("" + userName + " 执行了登录请求");
    }
}
