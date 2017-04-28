package com.better.groovy.base

/**
 * Created by zhaoyu on 2017/4/25.
 */
// 闭包默认参数值
def say = { message, name = 'world' -> println("${message} ${name}") }
say("hello", "better")
say("hello")

println("========= 递归闭包 =========")
def factorial = { n ->
    return (n == 0) ? 1 : n * call(n - 1)  // not n*factorial(n-1)
}
println(factorial(5))

println("========= 如果闭包的形参，没有指定默认值，则调用必须写全 =========")
def clos = { a, b, c -> println("${a} ${b} ${c}") }
clos(1, 2, 3)
clos(1, 2)// error
