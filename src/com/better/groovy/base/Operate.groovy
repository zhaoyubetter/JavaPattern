package com.better.groovy.base

/**
 * 运算符
 * Created by zhaoyu on 2017/5/2.
 */
// ?:
def a = 'better'
println a ?: 'aaa'     // 有，打印 better，没有打印 aaa
def b = 'bbb'
println b ?: 'ccc'

// ?.
def value = ['1','2']
value?.each { it ->
    println(it)
}

//
def value2 = []
def func = 'var list = art.dialog.list; for (var i in list) {list[i].close();} var thisEle=this; \tvar get=jQuery.get(\'plugin.php?id=attachment_download:attachment_download&aid=MTMxMjU1Nzl8Y2E0NmRjMzF8MTQ5MzcwODMxOXwwfDExMDQ2OTA0&file_name=%E4%BD%8E%E7%A7%91%E6%8A%80%E4%B8%9B%E4%B9%A6+%5B%E5%A5%97%E8%A3%85%E5%85%B16%E5%86%8C%5D.part2.rar\',{},function(data){jQuery.dialog({follow: thisEle,drag: false,resize: false,content:data});},\'html\');'
def matcher = func =~ /'([^']+)'/

!matcher ?: value2 << matcher[0][1]

value2?.each { it ->
    println(it)
}
