package com.better.groovy.forandroid.module_separate

import java.util.regex.Matcher

/**
 * 抽离drawable，需要copy 文件，drawable注意兼容(v21)
 * 从 module 的Java 文件中，xml 中，提取：R.drawable.XXX ,
 * 从 module 的Xml文件中(包含各种分辨率xx-hdpi)，提取 @drawable.XXX,
 * 目前主工程中，没有mipmap，暂时不考虑
 *
 * 注意：
 * drawable 后缀名有 .png, .9.png, .jpg, .xml 等，需要考虑进去
 */

class DrawableFinder {
    // 正则匹配java文件内的string
    def JAVA_REGX = ~/(R\.drawable\.(.+?))[;)},\s]/      // R.drawable.xxx
    // 正则匹配xml文件内的drawable
    def XML_REGX = ~/(@drawable\/(.+?))"/                  // @drawable/xxx

    /**
     * @param moduleDir
     * @param appResDir
     */
    def export(String moduleDir, String appResDir) {
        Tools.checkDir(moduleDir, appResDir)

        // === 1.drawable name map from module.(XXX to R.drawable.XXX or XXX to @drawable/XXX)
        File moduleDirFile = new File(moduleDir)
        Map<String, String> moduleDrawableMap = new HashMap<>()
        exportModuleMap(moduleDirFile, moduleDirFile, moduleDrawableMap)
        // === 2  from main app
        File tappResDir = new File(appResDir)
        // folderName to drawable filename list
        Map<String, List<String>> dirFileMap = getAppMap(tappResDir, moduleDrawableMap)
        // === 3.export
        innerExport(dirFileMap, tappResDir)
    }

    private def exportModuleMap(File file, File target, map) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> exportModuleMap(it, target, map) }
            } else {
                anyliseModule(file, map)
            }
        }
        if (file == target) {    // 递归结束
            return
        }
    }

    private def anyliseModule(File file, Map<String, String> map) {
        String fileName = file.name
        def regex = null
        if (fileName.endsWith(".java") || fileName.endsWith(".kt")) {
            regex = JAVA_REGX
        } else if (fileName.endsWith(".xml")) {
            regex = XML_REGX
        }
        if (regex != null) {
            file.each { line ->
                Matcher matcher = line =~ regex
                while (matcher.find()) {
                    // XXX to R.drawable.XXX || XXX to @drawable/xxx
                    map.put(matcher.group(2), matcher.group(1))
                }
            }
        }
    }

    private def getAppMap(File file, Map<String, String> map) {
        Map<String, List<String>> fileMap = new HashMap<>()
        // 获取所有以 drawable开头的文件夹
        File[] layoutDirs = file.listFiles(new Tools.DirNamePrefixFilter("drawable"))
        layoutDirs?.each { layout ->
            final dirFiles = new ArrayList()
            fileMap.put(layout.name, dirFiles)
            layout.eachFile { it ->
                // [.png. 9.png, .jpg, .xml] 都需要考虑
                String fileNameWithoutSuffix = it.name.substring(0, it.name.lastIndexOf("."))
                if(fileNameWithoutSuffix.endsWith(".9")) {
                    fileNameWithoutSuffix = fileNameWithoutSuffix.substring(0, fileNameWithoutSuffix.lastIndexOf("."))
                }
                if (map.containsKey(fileNameWithoutSuffix))
                    dirFiles.add(it.name)
            }
        }
        return fileMap
    }

    private def innerExport(Map<String, List<String>>dirFileMap, File appResDir) {
        File output = new File(Tools.USER_CURRENT_DIR + File.separator + Tools.OUTPUT)
        if(!output.exists()) {
            output.mkdirs()
        }

        // it.key 表示文件夹名，it.value 为 该文件下，需要copy的文件名
        dirFileMap?.each { it ->
            File srcDir = new File(appResDir, it.key)
            File destDir = new File(output, it.key)
            if(!destDir.exists()) {
                destDir.mkdirs()
            }
            // 2进制形式copy
            it.value.each { fileName ->
                def srcFile = new File(srcDir, fileName)
                def destFile = new File(destDir, fileName)
                destFile << srcFile.bytes
            }
        }

        println("drawable output finish!")
    }

    // 测试
    static void main(args) {
        def moduleDir = Tools.MODULE_DIR
        def appResDir = Tools.APP_RES_DIR
        new DrawableFinder().export(moduleDir, appResDir)
    }
}
