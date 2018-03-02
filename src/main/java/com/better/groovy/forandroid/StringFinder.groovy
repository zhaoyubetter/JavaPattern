import java.util.regex.Matcher

/*
 功能说明：
1. 从分离的模块中（独立git库）从
   java，layout文件中找出所有 R.string.xxx，（后续考虑kotlin）
   并匹配到宿主工程中的strings.xml（可能有多个）,然后输出该模块中用到的所有 strings，记为 [模块名_strings.xml]
2. 通过1的步骤，默认语言，已经有了，然后再考虑其他语言的资源:
   a. 将  [模块名_strings.xml]，匹配主工程的 所有 strings_其他语言.xml, 有对应的key拿过来，没有，直接拿本文件的；
   b. 将a步骤中的 strings，组合成 [模块名_strings_语言.xml]

 */

class StringFinder {

    // 正则匹配java文件内的string
    def JAVA_REGX = ~/(R\.string\.(.+?))[;)},\s]/        // R.string.aaabb
    // 正则匹配xml文件内的string
    def XML_REGX = ~/(@string\/(.+?))"/          // @+id/string.xxxx

    private File appResDir
    private boolean isStop
    private Map<String, String> moduleStringMap = new HashMap<>()

    // 字符串资源目录
    private final String string_res = "values%s"

    // xml文件过滤器
    private final xmlFileFilter = new FileFilter() {
        @Override
        boolean accept(File pathname) {
            return pathname.name.endsWith(".xml")
        }
    }

    /**
     * 主方法一，获取模块内的 strings.xml
     * @param moduleDir 模块路径
     * @param appResDir 主工程Res资源路径
     * @return
     */
    def exportModuleStringXml(String moduleDir, String appResDir) {
        moduleStringMap.clear()

        File file = new File(moduleDir)
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("the Aodule ${dir} Directory is wrong!")
        }

        // app 主工程资源目录，默认values
//        File tappResDir = new File(appResDir)
//        this.appResDir = tappResDir;
//        if (!tappResDir.exists() || !tappResDir.isDirectory()) {
//            throw new RuntimeException("the App  ${dir} Res Directory is wrong!")
//        }
//        innerModuleStringXml(file)

        // 1.=== 拿到模块中所有的 String key
        moduleStringMap.each { it -> println(it.key + "  --> " + it.value) }

        // 2.=== 从主app/res中查找 ===
        exportPartStringFromApp(moduleStringMap, appResDir)
    }

    private innerModuleStringXml(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { it -> innerModuleStringXml(it) }
            } else {
                anyliseModule(file)
            }
        }
    }

    private anyliseModule(File file) {
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

    private def exportPartStringFromApp(Map<String, String> moduleStringMap, String appResFolder) {
        File stringFolder = new File(appResFolder, String.format(string_res, ""))
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
            // 闭包的递归， 先定义，再赋值
            def eachNode
            eachNode = { node ->
                // 所有孩子-》节点-》遍历
                node.children().each { child ->
                    println(child)
//                    if(child.attributes().containKey("string")) {
//                        println(node.attributes()["string"])
//                    }
                }
                node.value()?.each {eachNode.call(it)}
            }
            eachNode.call(root)
        }
    }

// 测试
    static void main(args) {
        def moduleDir = "D:\\git.jd\\mae.aura.flowcenter\\aura-flowcenter"
        def appResDir = "D:\\git.jd\\jdme_android\\app\\src\\main\\res"
        new StringFinder().exportModuleStringXml(moduleDir, appResDir)
    }

}