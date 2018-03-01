package com.better.pattern.proxy.static_proxy

import com.better.pattern.proxy.static_proxy.decorator.LoginDecorator

/**
 * Created by zhaoyu on 2016/12/18.
 */
// 原来的登录
def login = new Login();
login.doLogin("better");

// 使用静态代理测试代码
login = new Login();
def loginProxy = new LoginProxy(login);
loginProxy.doLogin("admin")


println '''
    ------
    对比
    ------
'''

// 包装器与代理对象对比
login = new Login();
LoginDecorator decorator = new LoginDecorator(login);
proxy = new LoginProxy(login);

decorator.doLogin("better")
proxy.doLogin("better")