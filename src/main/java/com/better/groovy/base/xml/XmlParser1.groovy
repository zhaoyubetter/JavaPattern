package com.better.groovy.base.xml

import groovy.util.*

/**
 * 解析xml
 * Created by zhaoyu on 2017/4/17.
 */

def parser = new XmlParser()
def doc = parser.parse('lib.xml')

// println("${doc.book[0].title[0].text()}")
doc.book.each { bk ->
    println("${bk.title[0].text()}")
}

println('======> 代码简化==== ')
doc.book.title.each { title ->
    println("${title.text()}")
}
