package com.better.groovy.forandroid.refactor.base

/**
 * 资源Base
 * @auther better
 */
abstract class BaseFolderResReplace extends BaseReplace {

    BaseFolderResReplace(srcFolderPath, resFolderPath) {
        super(srcFolderPath, resFolderPath)
    }

    /**
     * 获取资源类型名称
     * @return
     */
    abstract String getResTypeName()

    /**
     * 匹配源代码的正则表达式
     * @return
     */
    abstract String getJavaRegex()

    /**
     * 匹配xml中的正则表达式
     * @return
     */
    abstract String getXmlRegex()

    /**
     * 获取特定类型的资源名集合
     * @return
     */
    abstract Set<String> getResNameSet()

    /**
     * 模板方法
     * @throws IOException
     */
    final void replaceThis() throws IOException {
        println("----- $resTypeName ----- 重命名 $resTypeName 资源开始")

        def java_regx = ~getJavaRegex()
        def xml_regx = ~getXmlRegex()
        Set<String> resNameSet = getResNameSet()

        // 1.源代码目录部分
        replaceSrc(resNameSet, java_regx)
        // 2.资源目录部分
        replaceRes(resNameSet, xml_regx)

        println("----- $resTypeName ----- 重命名 $resTypeName 资源结束")
    }

    abstract void replaceSrc(Set<String> resNameSet, java_regx) throws IOException

    abstract void replaceRes(Set<String> resNameSet, xml_regx) throws IOException

    /*
    void export(String moduleFolder, String appResFolder) {
        Tools.checkDir(moduleFolder, appResFolder)

        File moduleDirFile = new File(moduleFolder)
        // === 1.layout name map from module.  (XXX to R.xxx.XXX)
        Map<String, String> moduleMap = new HashMap<>()
        moduleMap(moduleDirFile, moduleDirFile, moduleMap)
        // === 2. layout name map from 'MainGo App Res'
        File appResDirFile = new File(appResFolder)
        // folderName to layout filename list
        Map<String, List<String>> dirFileMap = getAppLayoutMap(appResDirFile, moduleLayoutMap)
        // === 3.according to 'moduleLayoutMap' key, find all layout from 'main app res' and copy layout.xml file from dirs
        export(dirFileMap, appResDir)
    }*/
}
