package com.better.groovy.forandroid.resource

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
 --------------------------------------------------------------
 资源类型             源代码中              xml 资源中\
 a. layout            R.layout.XXX          @layout/XXX
 b. drawable:         R.drawable.XXX        @drawable/XXX
 c. anim:             R.anim.XXX            @anim/XXX
 d. color:            R.color.XXX           @color/XXX
 e. menu:             R.menu.XXX
 f. raw:              R.raw.XXX
 g. xml:              R.xml.xxx

 2.values: string、string-array、attr、color、dimens、style
 ---------------------------------------------------------------
 values   类型         源代码中                xml 资源中
 a. string:            R.string.XXX         xml 中修改name @string/XXX  与 其值不改（如：<string name="empty">@string/xxx</string>）
 b. color              R.color.XXX          xml 中修改name @color/XXX   与 其值不改
 c. dimens             R.dimen.XXX          xml 中修改name @dimen/XXX   与 其值不改
 d. style              R.style.XXX          xml 中修改name @style/XXX   parent 如果在当前工程，需要修改
 e. arrays:            R.array.XXX          xml 中修改name
 f. attr               R.styleable.FlowMainItemView_fv_title  (比较复杂) 特殊处理

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
    def static final NEW_FREFIX = "mae_"
    // 老的资源前缀，可以保持空字符串(如：R.layout.jdme_layout_aa 会被替换成 R.layout.mae_layout_aa)
    def static final OLD_FREFIX = ""

    // 源代码路径
    File srcDir
    // 资源文件路径
    File resDir

    /**
     *
     * @param srcDirPath 源代码路径
     * @param resDirPath 资源路径
     */
    ResReName(String srcDirPath, String resDirPath) {
        srcDir = new File(srcDirPath)
        resDir = new File(resDirPath)
    }

    /* ================== 文件夹资源     Start  ====================================== */
    /**
     * rename Xml 资源
     * @return
     */
    def renameXml() {
        println("----- xml ----- 重命名xml资源开始")
        println("目前項目中，沒有直接使用xml資源，先不写实现")
        println("----- xml ----- 重命名xml资源开始")
    }

    /**
     * rename raw 資源
     * @return
     */
    def renameRaw() {
        println("----- raw ----- 重命名raw资源开始")

        Set<String> resNameSet = new HashSet<>()
        // 1.获取所有menu开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("raw"))
        // 2.获取menu名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                resNameSet.add(fileName)
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)raw(\s*?)\.(\s*?))(\w+)/
        // 修改 java 源代码部分的 R.raw.XXX
        replaceSrcDir(srcDir, resNameSet, java_regx)
        RawRenameTools.renameFile(resDir)

        println("----- raw ----- 重命名raw资源結束")
    }

    /**
     * rename Menu 资源
     * 目前menu资源只在源代码中使用了
     */
    def renameMenu() {
        println("----- menu ----- 重命名menu资源开始")

        Set<String> resNameSet = new HashSet<>()
        // 1.获取所有menu开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("menu"))
        // 2.获取menu名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                resNameSet.add(fileName)
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)menu(\s*?)\.(\s*?))(\w+)/
        // 修改 java 源代码部分的 R.menu.XXX
        replaceSrcDir(srcDir, resNameSet, java_regx)
        // 直接修改文件名
        MenuRenameTools.renameFile(resDir)

        println("----- menu ----- 重命名menu资源結束")
    }

    /**
     * rename Color 资源
     */
    def renameColor() {
        println("----- color ----- 重命名color资源开始")

        Set<String> resNameSet = new HashSet<>()
        // 1.获取所有color开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("color"))
        // 2.获取drawable名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                resNameSet.add(fileName)
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)color(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(@color\/)(\w+)/

        // 修改 java 源代码部分的 R.color.XXX
        replaceSrcDir(srcDir, resNameSet, java_regx)
        ColorRenameTools.replaceResDir(resDir, resNameSet, xml_regx)
        ColorRenameTools.renameFile(resDir)

        println("----- color ----- 重命名color资源结束")
    }

    /**
     * rename Anim 资源
     */
    def renameAnim() {
        println("----- anim ----- 重命名anim资源开始")
        Set<String> resNameSet = new HashSet<>()
        // 1.获取所有drawable开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("anim"))
        // 2.获取drawable名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                resNameSet.add(fileName)
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)anim(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(@anim\/)(\w+)/

        // 修改 java 源代码部分的 R.anim.XXX
        replaceSrcDir(srcDir, resNameSet, java_regx)
        AnimRenameTools.replaceResDir(resDir, resNameSet, xml_regx)
        AnimRenameTools.renameFile(resDir)

        println("----- anim ----- 重命名anim资源結束")
    }

    /**
     * 重命名Drawable资源
     * @return
     */
    def renameDrawable() {
        println("----- drawable ----- 重命名drawable资源开始")
        Set<String> drawableNameSet = new HashSet<>()
        // 1.获取所有drawable开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("drawable"))
        // 2.获取drawable名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                if (fileName.endsWith(".9")) {   // 可能有.9
                    fileName = fileName.substring(0, fileName.lastIndexOf("."))
                }
                drawableNameSet.add(fileName)
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)drawable(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(@drawable\/)(\w+)/

        // 修改 java 源代码部分的 R.drawable.XXX，有些哥们变态这样写 R. drawable.  XXX 都是支持的
        replaceSrcDir(srcDir, drawableNameSet, java_regx)
        // 修改资源文件中，资源文件不会出现空格现象
        DrawableRenameTools.replaceResDir(resDir, drawableNameSet, xml_regx)
        // 修改drawable文件名
        DrawableRenameTools.renameFile(resDir)

        println("----- drawable ----- 重命名drawable资源结束")
    }

    /**
     * 重命名layout资源
     * @return
     */
    def renameLayout() {
        println("----- layout ----- 重命名layout资源开始")

        Set<String> layoutNameSet = new HashSet<>()
        // 1.获取所有layout开头的文件夹
        File[] layoutDirs = resDir.listFiles(new DirNamePrefixFilter("layout"))
        // 2.获取layout名字并存储
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                layoutNameSet.add(it.name.substring(0, it.name.lastIndexOf(".")))
            }
        }

        def java_regx = ~/(R(\s*?)\.(\s*?)layout(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(@layout\/)(\w+)/

        // 修改 java 源代码部分的 R.layout.XXX，有些哥们变态这样写 R. layout.  XXX 都是支持的
        replaceSrcDir(srcDir, layoutNameSet, java_regx)
        // 修改资源文件中，资源文件不会出现空格现象
        LayoutRenameTools.replaceResDir(resDir, layoutNameSet, xml_regx)
        // 修改layout文件名
        LayoutRenameTools.renameFile(resDir)
        println("----- layout ----- 重命名layout资源结束")
    }

    /* ================== 文件夹资源     End ====================================== */

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

    // ====== 资源文件部分公用方法  start =====================
    // def xml_regx = ~/(@XXX\/)(w+)"/      xxx 表示各种资源，如：layout、drawable 等
    protected static void handleResFile(File file, Set<String> set, regex) {
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
    // ====== 资源文件部分公用方法  end =====================

    /* ================== values     Start  ====================================== */

    def renameValues() {
        Set<String> stringNameSet = new HashSet<>()     // 字符串
        Set<String> colorNameSet = new HashSet<>()      // 颜色
        Set<String> dimensNameSet = new HashSet<>()
        Set<String> styleNameSet = new HashSet<>()
        Set<String> arrayNameSet = new HashSet<>()

        // ===  1.strings : <string name="me_empty_message">XXX</string>  or <string name="me_empty_message">@string/xxx</string>
        // === /<string name=()>(.*)</string>/ ===
        // ==== 说明：string 节点的name需要改

        // 1.获取所有values开头的文件夹
        File[] dirs = resDir.listFiles(new DirNamePrefixFilter("values"))
        // 2.遍历文件夹下各个资源文件xml后缀，获取资源名称
        dirs?.each { dir ->
            dir.eachFile { it ->
                if (it.name.endsWith(".xml")) {
                    it.eachLine { line ->
                        Matcher matcher = line =~ /(<string\s+name\s*=\s*\")(.+?)(\">)/
                        while (matcher.find()) {
                            stringNameSet.add(matcher.group(2))
                        }

                        matcher = line =~ /(<color\s+name\s*=\s*\")(.+?)(\">)/
                        while (matcher.find()) {
                            colorNameSet.add(matcher.group(2))
                        }

                        matcher = line =~ /(<dimen\s+name\s*=\s*\")(.+?)(\">)/
                        while (matcher.find()) {
                            dimensNameSet.add(matcher.group(2))
                        }

                        // <style name="lightDialog" parent="Theme.AppCompat.Light.Dialog.Alert"> 注意这里的正则, style name 加入了 "
                        matcher = line =~ /(<style\s+name\s*=\s*\")(.+?\")(.*>)/
                        while (matcher.find()) {
                            styleNameSet.add(matcher.group(2))
                        }

                        matcher = line =~ /(<string-array\s+name\s*=\s*\")(.+?)(\">)/
                        while (matcher.find()) {
                            arrayNameSet.add(matcher.group(2))
                        }
                    }
                }
            }
        }

        println("------------ 处理 strings 资源")
        // ==== 1.string
        def java_regx = ~/(R(\s*?)\.(\s*?)string(\s*?)\.(\s*?))(\w+)/
        def xml_regx = ~/(<string\s+name\s*=\s*\")(.+?)(\">)/
        def xml_ref_regx = ~/(@string\/)(\w+)/
        replaceSrcDir(srcDir, stringNameSet, java_regx)                        // 修改源码中的
        ValuesRenameTools.replaceValuesName(resDir, stringNameSet, xml_regx)   // 修改xml中的名称
        ValuesRenameTools.replaceValuesRef(resDir, stringNameSet, xml_ref_regx)
        // 修改xml中的引用<string name="empty">@string/xxx</string>）

        println("------------ 处理 color 资源")
        // ===  2.color: 与string类似
        java_regx = ~/(R(\s*?)\.(\s*?)color(\s*?)\.(\s*?))(\w+)/
        xml_regx = ~/(<color\s+name\s*=\s*\")(.+?)(\">)/
        xml_ref_regx = ~/(@color\/)(\w+)/
        replaceSrcDir(srcDir, colorNameSet, java_regx)
        ValuesRenameTools.replaceValuesName(resDir, colorNameSet, xml_regx)   // 修改xml中的
        ValuesRenameTools.replaceValuesRef(resDir, colorNameSet, xml_ref_regx)   // 修改xml中的引用 @color/xxx

        println("------------ 处理 dimens 资源")
        // ===  3.dimens：与 string类似
        java_regx = ~/(R(\s*?)\.(\s*?)dimen(\s*?)\.(\s*?))(\w+)/
        xml_regx = ~/(<dimen\s+name\s*=\s*\")(.+?)(\">)/
        xml_ref_regx = ~/(@dimen\/)(\w+)/
        replaceSrcDir(srcDir, dimensNameSet, java_regx)
        ValuesRenameTools.replaceValuesName(resDir, dimensNameSet, xml_regx)   // 修改xml中的name
        ValuesRenameTools.replaceValuesRef(resDir, dimensNameSet, xml_ref_regx) // xml中的引用

        println("------------ 处理 string-array 资源")
        // ===  4.array, 只在源代码中使用
        java_regx = ~/(R(\s*?)\.(\s*?)array(\s*?)\.(\s*?))(\w+)/
        xml_regx = ~/(<string-array\s+name\s*=\s*\")(.+?)(\">)/
        replaceSrcDir(srcDir, arrayNameSet, java_regx)
        ValuesRenameTools.replaceValuesName(resDir, arrayNameSet, xml_regx)   // 修改xml中的name

        // ===  5.style 有个 parent
        java_regx = ~/(R(\s*?)\.(\s*?)style(\s*?)\.(\s*?))(\w+)/
        xml_regx = ~/(<style\s+name\s*=\s*\")(.+?\")(.*>)/
        xml_ref_regx = ~/(@style\/)(.+?\")/        // 注意这里的正则分组2加入了 "
        replaceSrcDir(srcDir, styleNameSet, java_regx)
        ValuesRenameTools.replaceValuesName(resDir, styleNameSet, xml_regx)     // name
        ValuesRenameTools.replaceValuesRef(resDir, styleNameSet, xml_ref_regx)   // 引用

        // ===  6.attr 特殊处理一下 暂时不做了
        /*
          <declare-styleable name="DynamicTextView">
            <attr name="dynamic_textColor" format="color"/>  修改里面的内容。而不是declare-styleable父标签的name
         </declare-styleable>
         */
    }

    /* ================== values     End  ====================================== */

    // 运行时，请使用副本运行（因为是直接操作文件，可能文件在使用，导致失败）
    static void main(args) {
        def src = "\\src"
        def res = "\\res"
        ResReName resReName = new ResReName(src, res)
        resReName.renameLayout()    // layout
        resReName.renameDrawable()  // drawable
        resReName.renameColor()     // color
        resReName.renameAnim()
        resReName.renameRaw()
        resReName.renameMenu()
        resReName.renameXml()
        resReName.renameValues()
    }
}