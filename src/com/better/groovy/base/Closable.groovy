package com.better.groovy.base

/**
 * 闭包
 * Created by zhaoyu on 2017/4/13.
 */

// 不带参数的闭包
def cols = { println 'Hello Better' }
cols.call()

// 带参数的闭包
def cols2 = { param -> println "Hello ${param}" }
cols2.call('Chelsea') // 使用call调用
cols2('Groovy')         // 直接调用，省略call

println('---------------------------------');
// 闭包单个隐参数 it
cols = { println "Hello ${it}" }
cols('better');

println('-------------- 阶乘 -------------------');
def factorial = 1
1.upto(5) { num -> factorial *= num }  // 闭包可写在外面 有参数的closure对象,可以直接像写方法一样写,这样就避免了放在()内写闭包
// or 1.upto(5, {num -> factorial *= num})

println("Factorial(5): ${factorial}")

println('-------------- each -------------------')
def nums = [1, 2, 3, 4, 5, 6, 7, 8]
nums.each { it -> if (it % 2 == 0) println it }
def users = ['Better': 29, 'Chelsea': 29, 'John': 22]
users.each { name, age -> if (age > 28) println name }
users.each { obj -> if (obj.value > 28) println obj.key }

println('-------------- find -------------------')
def value = [1, 3, 5, 7, 9]
value2 = value.find { element -> element > 6 }
println("Found: ${value2}")

users = ['Better': 29, 'Chelsea': 29, 'John': 22]
users.find { obj ->
    obj.value > 20
    println obj.key
}

println('-------------- any -------------------')
values = [11, 12, 13, 14]
println(values.any { element -> element > 12 })     // true

println('-------------- every -------------------')
println(values.every { element -> element > 12 })   // 是否全部大于12 false

println('-------------- collect -------------------')
println(values.collect { element -> return element * 2 })   // 放大2倍

println('-------------- collect 高级使用-------------------')
def doubles = { item -> 2 * item }
def isEvent = { item -> (item % 2 == 0) }

// 灵活的语法
def map(cols, list) {
    return list.collect(cols)
}

values = [11, 12, 13, 14, 15, 16]
println(map(doubles, values))
println(map(isEvent, values))

println('-------------- inject 高级使用-------------------')
values = [2, 3, 4, 5]
def cols4 = { prev, cur -> prev * cur }
println(values.inject(1, cols4))

println('-------------- 闭包当参数传-------------------')

def filter(list, operator) {
    return list.findAll(operator)
}

def isEven = { x -> return (x % 2 == 0) }
def isOdd = { x -> return !isEven(x) }

def table = [11, 12, 13, 14, 15, 16]
println(filter(table, isEven))
println(filter(table, isOdd))