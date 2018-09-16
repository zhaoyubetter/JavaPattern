package com.better.groovy.forandroid.refactor.folder

import com.better.groovy.forandroid.refactor.Tools
import com.better.groovy.forandroid.refactor.base.BaseFolderResReplace

/**
 * anim
 */
class AnimReplace extends BaseFolderResReplace {

    private def final DIR_FILTER = new Tools.DirNamePrefixFilter("anim")
    private def final RES_TYPE_NAME = "anim"

    AnimReplace(Object srcFolderPath, Object resFolderPath) {
        super(srcFolderPath, resFolderPath)
    }

    @Override
    String getResTypeName() {
        return RES_TYPE_NAME
    }

    @Override
    String getJavaRegex() {
        return "(R(\\s*?)\\.(\\s*?)anim(\\s*?)\\.(\\s*?))(\\w+)"
    }

    @Override
    String getXmlRegex() {
        return "(@anim/)(\\w+)"
    }

    @Override
    Set<String> getResNameSet() {
        Set<String> resNameSet = new HashSet<>()
        // 1.获取所有drawable开头的文件夹
        File[] dirs = resDir.listFiles(DIR_FILTER)
        // 2.获取drawable名字并存储
        dirs?.each { dir ->
            dir.eachFile { it ->
                String fileName = it.name.substring(0, it.name.lastIndexOf("."))
                resNameSet.add(fileName)
            }
        }
        return resNameSet
    }

    @Override
    void replaceSrc(Set<String> resNameSet, Object java_regx) throws IOException {
        println("---------- $RES_TYPE_NAME ----- 替换源代码目录开始")
        replaceSrcDir(srcDir, resNameSet, java_regx)
        println("---------- $RES_TYPE_NAME ----- 替换源代码目录结束")
    }

    @Override
    void replaceRes(Set<String> resNameSet, Object xml_regx) throws IOException {
        println("---------- $RES_TYPE_NAME ----- 替换资源目录开始")

        // 1.替换文件内容
        replaceResDir(resDir, resNameSet, xml_regx, null)
        // 2.修改文件名
        renameFile(resDir, DIR_FILTER, RES_TYPE_NAME)

        println("---------- $RES_TYPE_NAME ----- 替换资源目录结束")
    }
}
