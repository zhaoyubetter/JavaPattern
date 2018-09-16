package com.better.groovy.forandroid.refactor.folder

import com.better.groovy.forandroid.refactor.Tools
import com.better.groovy.forandroid.refactor.base.BaseFolderResReplace

/**
 * layout 布局资源
 */
public class LayoutReplace extends BaseFolderResReplace {

    private def final DIR_FILTER = new Tools.DirNamePrefixFilter("layout")
    private def final RES_TYPE_NAME = "layout"

    LayoutReplace(Object srcFolderPath, Object resFolderPath) {
        super(srcFolderPath, resFolderPath)
    }

    @Override
    String getResTypeName() {
        return RES_TYPE_NAME
    }

    @Override
    String getJavaRegex() {
        // group 6 为名字
        return "(R(\\s*?)\\.(\\s*?)layout(\\s*?)\\.(\\s*?))(\\w+)"
    }

    @Override
    String getXmlRegex() {
        // group 2为名字
        return "(@layout/)(\\w+)"
    }

    @Override
    Set<String> getResNameSet() {
        Set<String> layoutNameSet = new HashSet<>()
        // 1.获取所有layout开头的文件夹
        File[] layoutDirs = resDir.listFiles(DIR_FILTER)
        // 2.获取layout名字并存储
        layoutDirs?.each { layoutDir ->
            layoutDir.eachFile { it ->
                layoutNameSet.add(it.name.substring(0, it.name.lastIndexOf(".")))
            }
        }
        return layoutNameSet
    }

    @Override
    void replaceSrc(Set<String> resNameSet, java_regx) throws IOException {
        println("---------- layout ----- 替换源代码目录开始")
        replaceSrcDir(srcDir, resNameSet, java_regx)
        println("---------- layout ----- 替换源代码目录结束")
    }

    @Override
    void replaceRes(Set<String> resNameSet, xml_regx) throws IOException {
        println("---------- layout ----- 替换资源目录开始")

        // 1.替换文件内容
        replaceResDir(resDir, resNameSet, xml_regx, DIR_FILTER)
        // 2.修改文件名
        renameFile(resDir, DIR_FILTER, RES_TYPE_NAME)

        println("---------- layout ----- 替换资源目录结束")
    }
}
