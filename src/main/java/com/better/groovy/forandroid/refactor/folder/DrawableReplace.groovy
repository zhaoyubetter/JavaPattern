package com.better.groovy.forandroid.refactor.folder

import com.better.groovy.forandroid.refactor.ResToolsConfig
import com.better.groovy.forandroid.refactor.Tools
import com.better.groovy.forandroid.refactor.base.BaseFolderResReplace

/**
 * drawable
 */
class DrawableReplace extends BaseFolderResReplace {

    private def final static DIR_FILTER = new Tools.DirNamePrefixFilter("drawable")
    private def final static RES_TYPE_NAME = "drawable"

    DrawableReplace(ResToolsConfig config) {
        super(config)
    }

    @Override
    String getResTypeName() {
        return RES_TYPE_NAME
    }

    @Override
    String getJavaRegex() {
        return "(R(\\s*?)\\.(\\s*?)drawable(\\s*?)\\.(\\s*?))(\\w+)"
    }

    @Override
    String getXmlRegex() {
        return "(@drawable/)(\\w+)"
    }

    @Override
    Set<String> getResNameSet() {
        Set<String> drawableNameSet = new HashSet<>()
        // 1.获取所有drawable开头的文件夹
        File[] dirs = resDir.listFiles(DIR_FILTER)
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

        return drawableNameSet
    }

    @Override
    void replaceSrc(Set<String> resNameSet, Object java_regx) throws IOException {
        println("---------- $RES_TYPE_NAME ----- replace source folder start...")
        replaceSrcDir(srcDir, resNameSet, java_regx)
        println("---------- $RES_TYPE_NAME ----- replace source folder end")
    }

    @Override
    void replaceRes(Set<String> resNameSet, Object xml_regx) throws IOException {
        // 1.替换文件内容
        println("---------- $RES_TYPE_NAME ----- replace res folder start...")
        replaceResDir(resDir, resNameSet, xml_regx, null)
        println("---------- $RES_TYPE_NAME ----- replace res folder end")


        // 2.修改文件名
        println("---------- $RES_TYPE_NAME ----- rename start...")
        renameFile(resDir, resNameSet, DIR_FILTER, RES_TYPE_NAME)
        println("---------- $RES_TYPE_NAME ----- rename end")


    }
}
