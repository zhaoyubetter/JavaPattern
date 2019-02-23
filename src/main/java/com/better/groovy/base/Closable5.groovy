package com.better.groovy.base

/**
 * 闭包返回闭包
 * Created by zhaoyu on 2017/4/28.
 */
def add = { x, y -> return x + y }
def subtract = { x, y -> return x - y }
def multiply = { x, y -> return x * y }
def divide = { x, y -> return x / y }

// 返回闭包
def arithmetic = { arith ->
    switch (arith) {
        case 'insertLast': return add
        case 'sub': return subtract
        case 'mul': return multiply
        case 'div': return divide
        default: return add
    }
}

def addOperation = arithmetic('insertLast')
println(addOperation.call(3, 4))
println(arithmetic.call('mul').call(5, 8))

/*
 * 在调用curry()方法时，不需要提供所有实际参数，curry多的调用只引起闭包的部分应用程序，
 * 闭包的部分应用程序是另一个Closure对象
 */
println('--------- 闭包部分应用 (组合与合并闭包) ------------')
multiply = { x, y -> return x * y } // Closure
def triple = multiply.curry(3)      // triple = { y -> return 3 * y}
def quardruple = multiply.curry(4)  // quardruple = { y -> return 4 * y}
// 也就是说：参数 x 已经从 multiply定义中删除，x出现的地方，都被 3 or 4 替换了
println("triple(4) : ${triple(4)}")
println("quardrule(5): ${quardruple(5)}")

println('-------------------------------')
def rSub = { y, x -> return x - y }
def lSub = { x, y -> return x - y }
def sub10 = rSub.curry(10)          // sub10 = {x->return x-10}
def subFrom20 = lSub.curry(20)      // subFrom20 = {y->return 20-y}
println("sub10(20) : ${sub10(20)}")
println("subFrom20(14) : ${subFrom20(14)}")
