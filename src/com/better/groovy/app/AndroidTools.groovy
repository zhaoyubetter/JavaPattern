package com.better.groovy.app

import groovy.xml.QName

/**
 * findViewById
 * Created by zhaoyu on 2017/4/19.
 */
class AndroidTools {
    def findViewItemMap = []
    def idValuePattern = ~/.*\/(.+)/

    def fieldFormat = 'private %s %s;\n'      // define
    def findViewFormat = '%s %s = (%s) %sfindViewById(%s);\n' // findViewById

    /**
     * 返回findViewById字符串
     * @param xmlFilePath
     */
    def getFindViewString(xmlFilePath) {
        if (findViewItemMap == null || findViewItemMap.size() == 0) {
            anylise(xmlFilePath)
        }

        def sb = new StringBuilder()
        findViewItemMap.each { it ->
            sb.append(String.format(findViewFormat, it[0], it[1], it[0], "", "R.id." + it[1]))
        }

        return sb.toString();
    }

    /**
     * 获取字段定义字符串
     * @param xmlFilePath
     * @return
     */
    def getFieldString(xmlFilePath) {
        if (findViewItemMap == null || findViewItemMap.size() == 0) {
            anylise(xmlFilePath)
        }

        def sb = new StringBuilder()
        findViewItemMap.each { it ->
            sb.append(String.format(fieldFormat, it[0], it[1]));
        }
        sb.toString()
    }

    /**
     * 解析xml
     * @param xmlFilePath
     * @return
     */
    private def anylise(xmlFilePath) {
        def parser = new XmlParser()
        def doc = parser.parse(xmlFilePath)
        eachXmlNode(doc, 0)
    }

    def eachXmlNode(Node node, int level) {
        // 读属性
        if (node.attributes()) {
            def findAttr = node.attributes().find { it ->
                it.key instanceof QName && it.key.localPart == 'id' // 带有 namespace的属性，如：android:id
            }
            if (findAttr) {
                def mather = findAttr.value =~ idValuePattern
                findViewItemMap << [node.name(), mather[0][1]]
            }
        }

        // 递归执行
        if (node.children()) {
            node.children().each { it ->
                eachXmlNode(it, level++)
            }
        }
    }

    public static void main(String[] args) {
        //xmlFilePath = "file:///Users/zhaoyu/Documents/basenet/app/src/main/res/layout/activity_okhttp_cache.xml"
        def xmlFilePath = 'file:///Users/zhaoyu/Documents/Java/Pattern/src/com/better/groovy/base/xml/1.xml'
        def tools = new AndroidTools();
        println(tools.getFieldString(xmlFilePath));
        println()
        println(tools.getFindViewString(xmlFilePath))
    }
}
