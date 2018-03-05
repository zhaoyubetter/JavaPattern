package com.better.groovy.forandroid.module_separate

import java.util.regex.Matcher

/**
String 资源文件 分离工具
 功能说明：
1. 从分离的模块中（独立git库）从
   java，layout文件中找出所有 R.string.xxx，（后续考虑kotlin）
   并匹配到宿主工程中的strings.xml（可能有多个）,然后输出该模块中用到的所有 strings，记为 [模块名_strings.xml]
2. 通过1的步骤，默认语言，已经有了，然后再考虑其他语言的资源:
   a. 将  [模块名_strings.xml]，匹配主工程的 所有 strings_其他语言.xml, 有对应的key拿过来，没有，直接拿本文件的；
   b. 将a步骤中的 strings，组合成 [模块名_strings_语言.xml]

 TODO:
 3. 测试没问题时，考虑将 strings key 整体重命名
 */

class StringFinder {

    // 正则匹配java文件内的string
    def JAVA_REGX = ~/(R\.string\.(.+?))[;)},\s]/        // R.string.aaabb
    // 正则匹配xml文件内的string
    def XML_REGX = ~/(@string\/(.+?))"/          // @+id/string.xxxx

    def xml_header = '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n'
    def xml_footer = "\r\n<resources>"
    def string_res_item_template = "<string name=\"%s\">%s</string>\r\n"

    // ==== 字符串资源
    def string_res_locale = ""  // (-en 为英文资源，对应 values-en)
    // 字符串资源目录
    def string_res = "values%s"

    // xml文件过滤器
    private final xmlFileFilter = new FileFilter() {
        @Override
        boolean accept(File pathname) { return pathname.name.endsWith(".xml") }
    }

    /**
     * 对外方法，设置 资源后缀 ，如-en，表示从 values-en中获取
     * @param locale
     */
    def setResoureLocale(String locale) {
        string_res_locale = locale
    }

    /**
     * 主方法一，获取模块内的 strings.xml
     * @param moduleDir 模块路径
     * @param appResDir 主工程Res资源路径
     * @return
     */
    def exportModuleStringXml(String moduleDir, String appResDir) {
        Map<String, String> moduleStringMap = new HashMap<>()
        File moduleDirFile = new File(moduleDir)
        if (!moduleDirFile.exists() || !moduleDirFile.isDirectory()) {
            throw new RuntimeException("the Aodule ${dir} Directory is wrong!")
        }

        // app 主工程资源目录，默认values
        File tappResDir = new File(appResDir)
        if (!tappResDir.exists() || !tappResDir.isDirectory()) {
            throw new RuntimeException("the App  ${dir} Res Directory is wrong!")
        }

        // 1.=== 模块中（ XXX to R.string.XXX || XXX to @string/XXX）
        exportInnerModuleStringXml(moduleDirFile, moduleDirFile, moduleStringMap)
        // 2.=== 主app/res中 （XXX to value）  ===
        Map<String, String> appStringResMap = exportPartStringFromApp(appResDir)
        // 3.==== 集合处理（交集） ===
        Set<String> retainSet = new HashSet<>(moduleStringMap.keySet())
        retainSet.retainAll(appStringResMap.keySet())
        // 4.==== 输出结果
        def sb = new StringBuilder(xml_header)
        retainSet.each { it -> sb.append(String.format(string_res_item_template, it, appStringResMap.get(it))) }
        sb.append(xml_footer)
        println(sb.toString())
    }

    private exportInnerModuleStringXml(File file, File target, map) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> exportInnerModuleStringXml(it, target, map) }
            } else {
                anyliseModule(file, map)
            }
        }

        if (file == target) {    // 递归结束
            return map
        }
    }

    private anyliseModule(File file, Map<String, String> moduleStringMap) {
        String fileName = file.name
        def regex = null
        if (fileName.endsWith(".java") || fileName.endsWith(".kt")) {
            regex = JAVA_REGX
        } else if (fileName.endsWith(".xml")) { // xml
            regex = XML_REGX
        }

        if (regex != null) {
            file.each { line ->
                Matcher matcher = line =~ regex
                while (matcher.find()) {
                    // XXX to R.string.XXX || XXX to @string/XXX
                    moduleStringMap.put(matcher.group(2), matcher.group(1))
                }
            }
        }
    }

    private def exportPartStringFromApp(String appResFolder) {
        File stringFolder = new File(appResFolder, String.format(string_res, string_res_locale))
        LinkedList<File> allFiles = new LinkedList<>()      // 所有资源文计
        LinkedList<File> allFolder = new LinkedList<>()     // 文件夹
        stringFolder.eachFile { it ->
            if (it.isDirectory())
                allFolder.add(it)
            else {
                allFiles.add(it)
            }
        }
        while (!allFolder.isEmpty()) {
            File dir = allFolder.removeFirst()
            dir.eachFile { it ->
                if (it.isDirectory())
                    allFolder.add(it)
                else {
                    allFiles.add(it)
                }
            }
        }
        // 遍历所有文件
        defStringXmlFileParser(allFiles)
    }

    private def defStringXmlFileParser(List<File> xmlResFiles) {
        Map<String, String> stringMap = new HashMap<>()
        xmlResFiles.each { it ->
            def root = new XmlParser(false, false).parse(it.absolutePath)
            // 直接获取string节点
            root."string"?.each { node ->
                stringMap.put(node.attribute('name'), node.text())
            }
        }
        stringMap
    }

    // 测试
    static void main(args) {
        def moduleDir = Tools.MODULE_DIR
        def appResDir = Tools.APP_RES_DIR
        def stringFinder = new StringFinder()
        stringFinder.setResoureLocale("-en")
        stringFinder.exportModuleStringXml(moduleDir, appResDir)
    }
}