package com.better.pattern.proxy.dynamic_proxy

import java.lang.reflect.Proxy

/**
 * Created by zhaoyu on 2016/12/22.
 */
def target = new LoginImpl();
def proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new LoggerInteceptor(target))
proxy.login("better")