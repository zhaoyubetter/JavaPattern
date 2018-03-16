package com.better.groovy.forandroid.resource

import com.better.groovy.forandroid.module_separate.Tools

import java.util.regex.Matcher

/**
 layout 文件内容引用名称的重命名与layout文件名的rename
 * 1. 内容：   @layout/XXX ---> @layout/前缀_XXX
 * 2. 文件名： xxx.xml  ---> 前缀_xxx.xml

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
    static void renameFile(File file) {
        File[] layoutDirs = file.listFiles(new ResReName.DirNamePrefixFilter("layout"))
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
                    ResReName.handleResFile(it, set, regx)
                }
            }
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
