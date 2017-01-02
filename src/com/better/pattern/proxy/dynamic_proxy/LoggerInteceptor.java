package com.better.pattern.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 添加日志
 * Created by zhaoyu on 2016/12/22.
 */
public class LoggerInteceptor implements InvocationHandler {

    private Object target;      // 目标对象

    public LoggerInteceptor(Object target) {
        this.target = target;
    }

    private void before(Object proxy, Method method, Object[] args) {
        System.out.println("执行之前： " + target.getClass().getName() + "-" + method.getName() + ",参数 " + args[0]);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(proxy, method, args);
        Object o = method.invoke(target, args);     // 目标执行
        after(proxy, method, args);
        return o;
    }

    private void after(Object proxy, Method method, Object[] args) {
        System.out.println("执行之后：" + target.getClass().getName() + "-" + method.getName() + ", 参数 " + args[0]);
    }
}
