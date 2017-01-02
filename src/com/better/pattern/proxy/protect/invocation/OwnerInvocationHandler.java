package com.better.pattern.proxy.protect.invocation;

import com.better.pattern.proxy.protect.abs.PersonBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 拥有者
 * Created by zhaoyu on 2017/1/2.
 */
public class OwnerInvocationHandler implements InvocationHandler {

    PersonBean personBean;

    public OwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("get")) {
            return method.invoke(personBean, args);
        } else if (method.getName().startsWith("setHotOrNotRating")) {
            throw new IllegalAccessError("Your can not set Hor Or Not Rating On yourself!!!");
        } else if (method.getName().startsWith("set")) {
            return method.invoke(personBean, args);
        }
        return null;
    }
}
