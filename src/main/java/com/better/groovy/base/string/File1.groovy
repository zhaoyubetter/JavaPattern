package com.better.groovy.base.string

import java.io.*;

/**
 * Created by zhaoyu on 2017/4/17.
 */
def file = new File("StringTest.groovy")
println(file.exists())

// print each line
i = 0
file.each { line ->
    i += 1
    printf('%2s: ', [i])
    println("${line}")
}

println('======================')
rootFile = new File("/Users/zhaoyu/Documents/Java/Pattern/src/com/better/groovy/base/")

// 遍历文件
def listDir(dirFile) {
    dirFile.eachFile { file ->
        if(file.isDirectory()) {
            println(">>>>> ${file.path}")
            listDir(file)
        } else {
            println("${file.getName()}")
        }
    }
}

listDir(rootFile)