package com.better.groovy.base.string

/**
 * Created by zhaoyu on 2017/4/17.
 */

def file = new File("/Users/zhaoyu/Documents/Java/Pattern/src/com/better/groovy/base/CharTimes.groovy")

if(file.exists()) {
    println("文件存在")

    CodeLineCountRegex2 regex2 = new CodeLineCountRegex2()
    regex2.codeLineCount(file)


    println("行数：${regex2.codeLine}")
    println("注释：${regex2.explainLine}")
    println("空行：${regex2.spaceLine}")
}



