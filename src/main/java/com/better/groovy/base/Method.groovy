package com.better.groovy.base

/**
 * 方法
 * Created by zhaoyu on 2017/4/13.
 */
def hello(name) {
    println("你好：${name}")
}
hello('better');

// 默认参数
def hello2(place, name='better') {
    println("welcome to ${place} ${name}")
}
hello2('Beijing','Chelsea')
hello2('Changsha')
