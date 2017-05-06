package com.better.groovy.base.in_action

/**
 * Created by zhaoyu on 2017/5/6.
 */
def classes = [String, List, File]
for (clazz in classes) {
    println(clazz.'package'.name)       // package 是个关键字
}

println(classes.'package'.name)     // 打印包名