package com.better.groovy.forandroid.resource

import com.better.groovy.forandroid.module_separate.Tools

import java.util.regex.Matcher

/**
 * 资源重命名，这里为新增前缀

 <pre>
 因Android 中，资源的名称不能重复，在抽取的模块可以完全独立运行时，我们用此工具重命名独立模块中的所有资源
 规则是添加前缀，如：mae_功能模块名_

 当前也可以使用开发工具自带的重命名方式来做

 ** 分析**
 资源的构成：
 1.资源类型（文件夹形式）：layout、drawable、anim、color、menu、raw、xml
 a. layout 访问形式： R.layout.XXX       与 @layout/XXX
 b. drawable:         R.drawable.XXX     与 @drawable/XXX
 c. anim:             R.anim.XXX         与 @anim/XXX
 d. color:            R.color.XXX        与 @color/XXX
 e. menu:             R.menu.XXX
 f. raw:              R.raw.XXX
 g. xml:              R.xml.xxx

 2.values: string、string-array、attr、color、dimens、style
 a. string:            R.string.XXX      与 @string/XXX
 b. arrays:            R.array.XXX
 c. attr               R.styleable.FlowMainItemView_fv_title      比较复杂
 d. color              R.color.XXX       与 @color/XXX
 e. dimens             R.dimen.XXX       与 @dimen/XXX
 f. style              R.style.XXX       与 @style/XXX

 ** 步骤：**
 注意：所有资源替换来源为本工程资源来源，因为有些资源有可能是公共库中的，替换就麻烦了
 1. 先替换layout；

 </pre>
 */
class ResReName {

    static final class DirNamePrefixFilter implements FilenameFilter {
        def prefix = ""

        DirNamePrefixFilter(String prefix) {
            this.prefix = prefix
        }

        @Override
        boolean accept(File dir, String name) {
            return dir.isDirectory() && name.startsWith(prefix)
        }
    }

    // java 源文件 such as  R.string.aaabb  R . string. aa
    def JAVA_REGX = ~/(R(\s*?)\.(\s*?)string(\s*?)\.(\s*?))(\w+)/  // R.string  R.anim
    def XML_REGX = ~/(@string\/(w+))"/                             // @+id/string.xxxx

    // 新资源前缀
    def static final NEW_FREFIX = "_flowcenter_"
    // 老的
    def static final OLD_FREFIX = "_"

    // 源代码路径
    File srcDir
    // 资源文件路径
    File resDir

    ResReName(String srcDirPath, String resDirPath) {
        srcDir = new File(srcDirPath)
        resDir = new File(resDirPath)
    }

    def renameLayout() {
        println("----- 修改layout资源开始")

        Set<String> layoutNameSet = new HashSet<>()
        // 1.获取所有layout开头的文件夹
        File[] layoutDirs = resDir.listFiles(new Tools.DirNamePrefixFilter("layout"))
        // 2.获取layout名字并存储
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                layoutNameSet.add(it.name.substring(0, it.name.lastIndexOf(".")))
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)layout(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(@layout\/)(\w+)"/

        // 修改 java 源代码部分的 R.layout.XXX，有些哥们变态这样写 R. layout.  XXX 都是支持的
        //replaceSrcDir(srcDir, layoutNameSet, java_regx)
        // 修改资源文件中，资源文件不会出现空格现象
        //LayoutRename.replaceResDir(resDir, layoutNameSet, xml_regx)
        LayoutRename.renameLayoutFile(resDir)
        println("----- 修改layout资源结束")
    }

    // ====== 源代码中的部分  start =============================================
    private void replaceSrcDir(File file, Set<String> set, java_regx) {
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
        String fileContent = file.getText()     // 文件都是文本文件
        StringBuffer sb = new StringBuffer()             // 结果内容
        Matcher matcher = fileContent =~ regex
        while (matcher.find()) {
            String oldResName = matcher.group(6)
            if (set.contains(oldResName)) {
                String newResName = NEW_FREFIX + oldResName
                if (oldResName.startsWith(OLD_FREFIX)) {
                    newResName = NEW_FREFIX + oldResName.substring(OLD_FREFIX.length())
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

    // 测试
    static void main(args) {
        def src = ""
        def res = ""
        ResReName resReName = new ResReName(src, res)
        resReName.renameLayout()
    }
}