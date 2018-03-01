package com.better.pattern.proxy.static_proxy.decorator;

import com.better.pattern.proxy.static_proxy.LoginInterface;

/**
 * Created by zhaoyu on 2016/12/18.
 */
public class LoginDecorator implements LoginInterface {

    LoginInterface mLogin;

    public LoginDecorator(LoginInterface mLogin) {
        this.mLogin = mLogin;
    }

    @Override
    public void doLogin(String name) {
        mLogin.doLogin(name);
        System.out.println("欢迎 " + name + " 使用系统");
    }
}
