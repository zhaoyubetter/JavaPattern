package com.better.groovy.base.xml


/**
 * Created by zhaoyu on 2017/4/17.
 */

//def mTable = new MarkupBuilder(new extend('tables.xml').newPrintWriter())
//mTable.tables() {
//    mTable.table(name:'Book') {
//        filed(name:'title', type:'text')
//        filed(name:'isbn', type:'text')
//        filed(name:'price', type:'int')
//        filed(name:'author', type:'id')
//        filed(name:'publisher', type:'id')
//    }
//
//    mTable.table(name:'Author') {
//        filed(name:'surname', type:'text')
//        filed(name:'forename', type:'text')
//    }
//
//    mTable.table(name:'Publisher') {
//        filed(name:'name', type:'text')
//        filed(name:'url', type:'text')
//    }
//}

def parser = new XmlParser()
def doc = parser.parse("tables.xml")
println(doc.table.filed['@name'])       // 输出所有name