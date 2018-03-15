package com.better.groovy.forandroid.resource

import com.better.groovy.forandroid.module_separate.Tools

import java.util.regex.Matcher

/**
 layout 文件中的资源重命名

 <pre>
 1. <ViewStub  android:layout="@layout/mae_widget_frame_error_container" />
 2. `<include layout="@layout/mae_mia_msg_progress"/>
 3.  tools:listitem="@layout/around_item_message"
 看到了第三种，我就知道只能用正则表达式了
 </pre>
 */
final class LayoutRenameTools {

    /**
     * 文件重命名
     * @param file 资源文件目录
     */
    static void renameLayoutFile(File file) {
        File[] layoutDirs = file.listFiles(new Tools.DirNamePrefixFilter("layout"))
        // 遍历
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                if (it.name.endsWith(".xml")) {     // 只处理xml文件
                    String oldName = it.name
                    String newName = ResReName.NEW_FREFIX + oldName
                    if (oldName.startsWith(ResReName.OLD_FREFIX)) {
                        newName = ResReName.NEW_FREFIX + oldName.substring(ResReName.OLD_FREFIX.length())
                    }
                    File newFile = new File(it.getParent(), newName)
                    if (newFile.exists()) {
                        newFile.delete()
                    }
                    if (!it.renameTo(newFile)) {
                        println("layout 文件 ${it.name} 重命名失败！，请手动修改成：${newFile.name}")
                    }
                }
            }
        }
    }

    /**
     * layout 文件中的资源重命名
     * @param file
     * @param set
     * @param regx
     */
    static void replaceResDir(File file, Set<String> set, regx) {
        // 只处理 layout 开头的文件名
        File[] layoutDirs = file.listFiles(new Tools.DirNamePrefixFilter("layout"))
        // 遍历
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                if (it.name.endsWith(".xml")) {     // 只处理xml文件
                    handleResFile(it, set, regx)
                }
            }
        }
    }

    // def xml_regx = ~/(@layout\/(w+?))"/
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
                    matcher.appendReplacement(tSb, "\$1$newResName") // 拼接 保留$1分组,替换组2
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

    /*
    static void main(args) {
        File file = new File("C:/FlowCenterActivity.java")
        File dest = new File(file.getParent(), "aaaa.java")
        println(file.exists())
        if (dest.exists()) {
            dest.delete()
        }

        println(file.renameTo(dest))
    }*/
}
