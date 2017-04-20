package com.better.groovy.base.string

import com.better.groovy.app.CodeLineCountRegex2

/**
 * Created by zhaoyu on 2017/4/17.
 */

def file = new File("/Users/zhaoyu/Documents/Java/Pattern/src/com/better/")

if(file.exists()) {
    println("文件存在")

    CodeLineCountRegex2 regex2 = new CodeLineCountRegex2()
    regex2.codeLineCount(file)


    println("行数：${regex2.codeLine}")
    println("注释：${regex2.explainLine}")
    println("空行：${regex2.spaceLine}")
}



