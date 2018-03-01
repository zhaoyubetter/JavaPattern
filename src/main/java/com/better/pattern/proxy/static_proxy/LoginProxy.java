package com.better.pattern.proxy.static_proxy;

/**
 * Created by zhaoyu on 2016/12/18.
 */
public class LoginProxy implements LoginInterface {

    private LoginInterface login;

    public LoginProxy(LoginInterface login) {
        this.login = login;
    }

    @Override
    public void doLogin(String name) {
        System.out.println("登录之前做点事情。。。");

        if ("admin".equals(name)) {
            System.out.println("你滚...");
            return;
        }

        long startTime = System.currentTimeMillis();

        login.doLogin(name);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        long costTime = System.currentTimeMillis() - startTime;

        System.out.println("登录之后做点事情....");

        System.out.println("登录花费了 " + costTime + " 毫秒");
    }
}
