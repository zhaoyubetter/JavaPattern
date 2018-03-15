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
    def JAVA_REGX = ~/R((\s*?)\.(\s*?)string(\s*?)\.(\s*?)(\w+))?/  // R.string  R.anim
    def XML_REGX = ~/(@string\/(.+?))"/                             // @+id/string.xxxx

    // 源代码路径
    File srcDir
    // 资源文件路径
    File resDir

    ResReName(String srcDirPath, String resDirPath) {
        srcDir = new File(srcDirPath)
        resDir = new File(resDirPath)
    }

    def renameLayout() {
        Set<String> layoutNameSet = new HashSet<>()
        // 1.获取所有layout开头的文件夹
        File[] layoutDirs = resDir.listFiles(new Tools.DirNamePrefixFilter("layout"))
        // 2.获取layout名字并存储
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                layoutNameSet.add(it.name.substring(0, it.name.lastIndexOf(".")))
            }
        }

        def java_regx = ~/R((\s*?)\.(\s*?)layout(\s*?)\.(\s*?)(\w+))?/
        def xml_regx = ~/(@layout\/(.+?))"/

        replaceSrcDir(srcDir, layoutNameSet, java_regx)
    }

    private void replaceSrcDir(File file, set, java_regx) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> replaceSrcDir(file, set, java_regx) }
            } else {
                handleSrcFile(file, set, java_regx)
            }
        }
    }

    private void handleSrcFile(File file, Set<String> set, regex) {
        RandomAccessFile raf = new RandomAccessFile(file, "rw")
        long lastPoint = 0
        raf.each { line ->
            Matcher matcher = line =~ regex
            while (matcher.find()) {
                // R.ooo.XXX 需要转变成 to R.ooo.前缀_ooo
                String layoutName = matcher.group(6)

            }
            lastPoint = raf.getFilePointer()
        }
    }

    private void replaceDir2() {

    }

    // 测试
    static void main(args) {
        // 正则匹配java文件内的string
        def JAVA_REGX = ~/R((\s*?)\.(\s*?)string(\s*?)\.(\s*?)(\w+))?/  // R.string.aaabb  R . string. aa
        // 正则匹配xml文件内的string
        def XML_REGX = ~/(@string\/(.+?))"/             // @+id/string.xxxx

        String line = "String str = getString(  R  .   string  .   me_loadinbbb);" +
                "        String str = getString(  R  .   string  .   me_loadinga);" +
                "        String str = getString( R.string.me_loadidfsfnga);"
        Matcher matcher = line =~ JAVA_REGX

        while (matcher.find()) {
            println(matcher.group(6))
        }
    }
}
