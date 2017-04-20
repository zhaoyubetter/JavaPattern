package com.better.groovy.app

import groovy.xml.QName

/**
 * findViewById
 * Created by zhaoyu on 2017/4/19.
 */
class AndroidTools {
    def xmlFilePath
    def idFiledItemMap = []
    def idValuePattern = ~/.*\/(.+)/

    /**
     * findViewById
     * @param xmlFilePath
     */
    def findViewById(xmlFilePath) {
        //xmlFilePath = "file:///Users/zhaoyu/Documents/basenet/app/src/main/res/layout/activity_okhttp_cache.xml"
        xmlFilePath = 'file:///Users/zhaoyu/Documents/Java/Pattern/src/com/better/groovy/base/xml/1.xml'
        def parser = new XmlParser()
        def doc = parser.parse(xmlFilePath)
        eachXmlNode(doc, 0)


        def sb = new StringBuilder()
        idFiledItemMap.each { it ->
            sb.append(String.format("%s %s = %sfindViewById(%s)\n", it[0], it[1], "", "R.id." + it[1]))
        }

        println(sb)
    }

    def eachXmlNode(Node node, int level) {
        // 读属性
        if (node.attributes()) {
            def findAttr = node.attributes().find { it ->
                it.key instanceof QName && it.key.localPart == 'id' // 带有 namespace的属性，如：android:id
            }
            if (findAttr) {
                def mather = findAttr.value =~ idValuePattern
                idFiledItemMap << [node.name(), mather[0][1]]
            }
        }

        // 是否有孩子，有，递归
        if (node.children()) {
            node.children().each { it ->
                eachXmlNode(it, level++)
            }
        }
    }

    public static void main(String[] args) {
        def tools = new AndroidTools().findViewById();
    }
}
