package com.better.groovy.base.string

import java.util.regex.Pattern

/**
 * groovy 正则
 * 使用 ~'regex' 定义正则
 * Created by zhaoyu on 2017/4/17.
 */
def regex = ~'better'       // 创建正则，将字符串编译成 Pattern
def match = 'zhaoyubetter' =~ 'better'  // 将操作符左边的字符串跟右边的Pattern进行局部匹配，返回为Mather
def match1 = 'zhaoyubetter' =~ regex

println(match.find())   // true
println(match1.find())   // true

// === 精确匹配 ==~（左边整个字符串与右边的模式） ==
println( 'zhaoyubetter' ==~ 'better')   // false

// ===== 支持 // 来定义正则 =====
regex = ~/\w+@\w+(\.\w+)+/
println(('zhaoyubetter@126.com' =~ regex).find())   // true
