package com.better.groovy.forandroid.module_separate

/**
 * 先不考虑重构
 */
abstract class BaseFinder {

    abstract String getJavaRegex()

    abstract String getXmlRegex()

    /**
     * 获取输出目录
     * @return
     */
    String getUser_current_dir() {
        return System.getProperty("user.dir") + File.separator + Tools.OUTPUT
    }

    void export(String moduleFolder, String appResFolder) {
        Tools.checkDir(moduleFolder, appResFolder)

        File moduleDirFile = new File(moduleDir)
        // === 1.layout name map from module.  (XXX to R.xxx.XXX)
        Map<String, String> moduleMap = new HashMap<>()
        moduleMap(moduleDirFile, moduleDirFile, moduleMap)
        // === 2. layout name map from 'Main App Res'
        File appResDirFile = new File(appResFolder)
        // folderName to layout filename list
        Map<String, List<String>> dirFileMap = getAppLayoutMap(appResDirFile, moduleLayoutMap)
        // === 3.according to 'moduleLayoutMap' key, find all layout from 'main app res' and copy layout.xml file from dirs
        export(dirFileMap, appResDir)
    }

    abstract def getModuleMap(File moduleFileDir, File targetFileDir, moduleLayoutMap)
}
