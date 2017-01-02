package com.better.pattern.proxy.protect

import com.better.pattern.proxy.protect.abs.PersonBean
import com.better.pattern.proxy.protect.abs.PersonBeanImpl
import com.better.pattern.proxy.protect.invocation.NonOwnerInvocationHandler
import com.better.pattern.proxy.protect.invocation.OwnerInvocationHandler

import java.lang.reflect.Proxy

/**
 * Created by zhaoyu on 2017/1/2.
 */
def bean1 = new PersonBeanImpl("Better")
def bean2 = new PersonBeanImpl("Joy")

PersonBean ownProxy = HandlerFactory.getProxy(OwnerInvocationHandler.class, bean1)
println 'Name is: ' + ownProxy.getName()

try {
    //ownProxy.setHotOrNotRating(20)
} catch (Exception e) {
    println(e.getMessage())
}

println('''
    ------------------------------------
    使用非自身代理
    ------------------------------------
''')
PersonBean nonOwnProxy = HandlerFactory.getProxy(NonOwnerInvocationHandler.class, bean1)
nonOwnProxy.setHotOrNotRating(20)
println('Rating is : ' + nonOwnProxy.getHorOrNotRating())
