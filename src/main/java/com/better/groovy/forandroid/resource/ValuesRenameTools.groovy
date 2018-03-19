package com.better.groovy.forandroid.resource

import com.better.groovy.forandroid.module_separate.Tools

import java.util.regex.Matcher

/**
 2.values: string、string-array、attr、color、dimens、style
 ---------------------------------------------------------------
 values   类型         源代码中                xml 资源中
 a. string:            R.string.XXX         @string/XXX  与其值        <string name="empty">@string/xxx</string>
 b. color              R.color.XXX          @color/XXX   与其值        <color name="color">@color/xxx</string>
 c. dimens             R.dimen.XXX          @dimen/XXX   与其值        <dimens name="xx_16">@dimens/xxx</string>
 d. style              R.style.XXX          @style/XXX   与其parent    <style name="DialogLightDatePicker" parent="@style/XXX">
 e. arrays:            R.array.XXX
 f. attr               R.styleable.FlowMainItemView_fv_title  (比较复杂) 特殊处理
 */
final class ValuesRenameTools {

    /* ================== values     Start  ====================================== */
    /**
     * 重名 values 的name
     * @param file
     * @param set
     * @param regx
     */
    static void replaceValuesName(File file, Set<String> set, regx) {
        // 1.获取所有values开头的文件夹
        File[] dirs = file.listFiles(new ResReName.DirNamePrefixFilter("values"))
        // 2.遍历文件夹下各个资源文件xml后缀，获取资源名称
        dirs?.each { dir ->
            dir.eachFile { it ->
                if (it.name.endsWith(".xml")) {
                    handleResFile(it, set, regx)
                }
            }
        }
    }

    private static void handleResFile(File file, Set<String> set, regex) {
        boolean hasUpdate = false                 // 是否有修改
        StringBuilder sb = new StringBuilder()    // 文件内容
        file.each { line ->
            Matcher matcher = line =~ regex
            StringBuffer tSb = new StringBuffer()
            while (matcher.find()) {
                String oldResName = matcher.group(2)
                if (set.contains(oldResName)) {
                    String newResName = ResReName.NEW_FREFIX + oldResName
                    if (oldResName.startsWith(ResReName.OLD_FREFIX)) {
                        newResName = ResReName.NEW_FREFIX + oldResName.substring(ResReName.OLD_FREFIX.length())
                    }
                    matcher.appendReplacement(tSb, "\$1$newResName\$3") // 拼接 保留$1分组,替换组2
                } else {
                    matcher.find()               // 继续下一次查找，避免死循环
                }
            }
            if (tSb.length() > 0) {              // 如果包含了，则重新赋值line，并拼接余下部分
                matcher.appendTail(tSb)
                hasUpdate = true
                line = tSb.toString()
            }

            sb.append(line).append("\r\n")
        }

        // 有修改了，才重新写入文件
        if (hasUpdate) {
            file.write(sb.toString())
        }
    }


    /* ================== values     End  ====================================== */

    static void main(args) {
//        def strRegex = ~/(<string\s+name\s*=\s*\")(.+?)(\">)/
//        StringBuffer tSb = new StringBuffer()
//        Matcher matcher = " <string name=\"me_loading_message\">正在加载…</string>\n" +
//                "    <string name=\"me_no_network\">当前无网络</string>\n" +
//                "    <string name=\"me_todo\">@string/app_name</string>" =~ strRegex
//        while (matcher.find()) {
//            matcher.appendReplacement(tSb, "\$1bbcc\$3") // 拼接 保留$1分组,替换组2
//        }
//        matcher.appendTail(tSb)
//        println(tSb.toString())
    }
}
