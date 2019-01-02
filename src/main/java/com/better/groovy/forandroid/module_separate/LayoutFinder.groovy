package com.better.groovy.forandroid.module_separate


import java.util.regex.Matcher

/**
 *  将 module 对应的 layout 进行抽离
 *  将module中（Java代码中的）R.layout.xxx 引用 从 宿主App的中 layout 资源对目标文件夹中( layout 包含各种兼容，规律是 layout为统一前缀,如：layout-sw360dp)，进行分离;
 *  操作步骤：
 *
 */
class LayoutFinder {
    def JAVA_REGX = ~/(R\.layout\.(.+?))[;)},\s]/     // R.layout.xxx
    def XML_REGX = ~/(@layout\/(.+?))"/                 // @layout/XXX
    def user_current_dir = System.getProperty("user.dir")

    def exportLayoutXml(String moduleDir, String appResDir) {
        Tools.checkDir(moduleDir, appResDir)
        File moduleDirFile = new File(moduleDir)

        // === 1.layout name map from module.  (XXX to R.layout.XXX)
        Map<String, String> moduleLayoutMap = new HashMap<>()
        getModuleLayoutMap(moduleDirFile, moduleDirFile, moduleLayoutMap)

        // === 2. layout name map from 'MainGo App Res'
        File appResDirFile = new File(appResDir)
        // folderName to layout filename list
        Map<String, List<String>> dirFileMap = getAppLayoutMap(appResDirFile, moduleLayoutMap)
        // === 3.according to 'moduleLayoutMap' key, find all layout from 'main app res' and copy layout.xml extend from dirs
        export(dirFileMap, appResDir)
    }

    private def getModuleLayoutMap(File file, File target, map) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> getModuleLayoutMap(it, target, map) }
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
                    // XXX to R.layout.XXX || XXX to @layout/xxx
                    map.put(matcher.group(2), matcher.group(1))
                }
            }
        }
    }

    private def getAppLayoutMap(File file, Map<String, String> map) {
        Map<String, List<String>> fileMap = new HashMap<>()
        File[] layoutDirs = file.listFiles(new Tools.DirNamePrefixFilter("layout"))
        layoutDirs?.each { layout ->
            final dirFiles = new ArrayList()
            fileMap.put(layout.name, dirFiles)
            layout.eachFile { it ->
                if (map.containsKey(it.name.substring(0, it.name.lastIndexOf("."))))
                    dirFiles.add(it.name)
            }
        }
        return fileMap
    }

    private def export(Map<String, List<String>> dirFileMap, String mainAppRes) {
        File output = new File(user_current_dir + File.separator + Tools.OUTPUT)
        if(!output.exists()) {
            output.mkdirs()
        }

        // it.key 表示文件夹名，it.value 为 该文件下，需要copy的文件名
        dirFileMap?.each { it ->
            File srclayoutDir = new File(mainAppRes, it.key)
            File destLayoutDir = new File(output, it.key)
            if(!destLayoutDir.exists()) {
                destLayoutDir.mkdirs()
            }
            it.value.each { fileName ->
                def src = new File(srclayoutDir, fileName)
                def dest = new File(destLayoutDir, fileName)
                dest << src.text
            }
        }

        println("layout output finish!")
    }

    // 测试
    static void main(args) {
        def moduleDir = Tools.MODULE_DIR
        def appResDir = Tools.APP_RES_DIR
        new LayoutFinder().exportLayoutXml(moduleDir, appResDir)
    }
}
