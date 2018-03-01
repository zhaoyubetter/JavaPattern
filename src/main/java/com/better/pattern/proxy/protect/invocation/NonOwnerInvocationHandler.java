package com.better.pattern.proxy.protect.invocation;

import com.better.pattern.proxy.protect.abs.PersonBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhaoyu on 2017/1/2.
 */
public class NonOwnerInvocationHandler implements InvocationHandler {

    PersonBean personBean;

    public NonOwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("get") || method.getName().startsWith("setHotOrNotRating")) {
            return method.invoke(personBean, args);
        } else if (method.getName().startsWith("set")) {
            return new IllegalAccessError("Your can not set in others");
        }
        return null;
    }
}
