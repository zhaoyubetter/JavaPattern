package com.better.pattern.proxy.protect;

import com.better.pattern.proxy.protect.abs.PersonBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class HandlerFactory {
    public static <T> T getProxy(Class<InvocationHandler> clazz, PersonBean personBean) throws Exception {
        Constructor c = clazz.getConstructor(PersonBean.class);
        InvocationHandler handler = (InvocationHandler) c.newInstance(personBean);
        return (T) Proxy.newProxyInstance(personBean.getClass().getClassLoader(), personBean.getClass().getInterfaces(), handler);
    }
}
