package com.better.groovy.base.in_action

/** xml 解析
 * Created by zhaoyu on 2017/5/6.
 */
def file = new XmlSlurper().parse(new File('customers.xml'))
def customers = file.corporate.customer
for (customer in customers) {
    println("${customer.@name}, =====》 ${customer.@company}")      // 取属性值 @
}


println("====== 使用 XmlParser =====")
// 使用 XmlParser
def parser = new XmlParser()
def doc = parser.parse("customers.xml")
println(doc.corporate.customer['@name'])       // 输出所有name @name 取属性值