package com.better.groovy.forandroid.refactor.base

import com.better.groovy.forandroid.refactor.Config

import java.util.regex.Matcher

abstract class BaseReplace {
    /**
     * 源代码路径
     */
    protected File srcDir
    /**
     * 资源文件路径
     */
    protected File resDir

    BaseReplace(srcFolderPath, resFolderPath) {
        this.srcDir = new File(srcFolderPath)
        this.resDir = new File(resFolderPath)
    }

    // ====== 源代码中的部分  start =============================================
    /**
     * 替换 src 源代码目录中的资源名
     * @param file 源代码目录
     * @param set 当前module下所有资源名 set
     * @param java_regx 匹配正则
     */
    protected void replaceSrcDir(File file, Set<String> set, java_regx) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> replaceSrcDir(it, set, java_regx) }
            } else {
                if (file.name.endsWith(".java") || file.name.endsWith(".kt")) {  // 只处理Java与Kt文件
                    handleSrcFile(file, set, java_regx)
                }
            }
        }
    }

    private void handleSrcFile(File file, Set<String> set, regex) {
        String fileContent = file.getText()              // 文件都是文本文件
        StringBuffer sb = new StringBuffer()             // 结果内容
        Matcher matcher = fileContent =~ regex
        while (matcher.find()) {
            String oldResName = matcher.group(6)   // 获取原有名称
            if (set.contains(oldResName)) {               // 本模块中包含的资源名，才替换
                String newResName = Config.NEW_PREFIX + oldResName
                if (oldResName.startsWith(Config.OLD_PREFIX)) {     // 替换掉旧的前缀
                    newResName = Config.NEW_PREFIX + oldResName.substring(Config.OLD_PREFIX.length())
                }
                matcher.appendReplacement(sb, "\$1$newResName") // 拼接 保留$1分组,替换$6分组
            } else {
                matcher.find()              // 继续下一次查找，避免死循环
            }
        }
        // 修改了文件时，才写入文件
        if (sb.length() > 0) {
            matcher.appendTail(sb)              // 添加结尾
            file.write(sb.toString())           // 写回文件
        }
    }
    // ====== 源代码中的部分  end =============================================

    // ====== 资源文件部分公用方法  start =====================
    // def xml_regx = ~/(@XXX\/)(w+)"/      xxx 表示各种资源，如：layout、drawable 等
    protected void handleResFile(File file, Set<String> set, regex) {
        boolean hasUpdate = false                 // 是否有修改
        StringBuilder sb = new StringBuilder()    // 文件内容
        file.each { line ->
            Matcher matcher = line =~ regex
            StringBuffer tSb = new StringBuffer()
            while (matcher.find()) {
                String oldResName = matcher.group(2)
                if (set.contains(oldResName)) {
                    String newResName = Config.NEW_PREFIX + oldResName
                    if (oldResName.startsWith(Config.OLD_PREFIX)) {
                        newResName = Config.NEW_PREFIX + oldResName.substring(Config.OLD_PREFIX.length())
                    }
                    matcher.appendReplacement(tSb, "\$1$newResName") // 拼接 保留$1分组,替换组2
                } else {
                    matcher.find()                          // 继续下一次查找，避免死循环
                }
            }
            if (tSb.length() > 0) {                         // 如果包含了，则重新赋值line，并拼接余下部分
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
    // ====== 资源文件部分公用方法  end =====================

    /**
     *  文件中的资源重命名
     * @param file
     * @param set
     * @param regx
     * @param dir_filter null no filter
     */
    protected def replaceResDir(File file, Set<String> set, regx, dir_filter) {
        // 只处理 layout 开头的文件名
        File[] dirs = file.listFiles(dir_filter)
        // 遍历
        dirs?.each { dir ->
            dir.eachFile { it ->
                if (it.name.endsWith(".xml")) {     // 只处理xml文件
                    handleResFile(it, set, regx)
                }
            }
        }
    }

}
