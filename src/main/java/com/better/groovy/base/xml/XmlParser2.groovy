package com.better.groovy.base.xml


/**
 * Created by zhaoyu on 2017/4/17.
 */

//def mTable = new MarkupBuilder(new extend('tables.xml').newPrintWriter())
//mTable.tables() {
//    mTable.table(name:'Book') {
//        filed(name:'title', taskType:'text')
//        filed(name:'isbn', taskType:'text')
//        filed(name:'price', taskType:'int')
//        filed(name:'author', taskType:'id')
//        filed(name:'publisher', taskType:'id')
//    }
//
//    mTable.table(name:'Author') {
//        filed(name:'surname', taskType:'text')
//        filed(name:'forename', taskType:'text')
//    }
//
//    mTable.table(name:'Publisher') {
//        filed(name:'name', taskType:'text')
//        filed(name:'url', taskType:'text')
//    }
//}

def parser = new XmlParser()
def doc = parser.parse("tables.xml")
println(doc.table.filed['@name'])       // 输出所有name