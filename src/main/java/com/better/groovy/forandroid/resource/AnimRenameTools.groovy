package com.better.groovy.forandroid.resource

/**
 * anim 文件内容引用名称的重命名与anim文件名的rename
 * 1. 内容：   @anim/XXX ---> @anim/前缀_XXX
 * 2. 文件名： xxx.xml  ---> 前缀_xxx.xml
 *
 */
final class AnimRenameTools {

    /**
     * 所有res下的文件中的资源重命名
     * @param file
     * @param set
     * @param regx
     */
    static void replaceResDir(File file, Set<String> set, regx) {
        // 只处理 layout 开头的文件名
        File[] dirs = file.listFiles()
        // 遍历
        dirs?.each { dir ->
            dir.eachFile { it ->
                if (it.name.endsWith(".xml")) {     // 只处理xml文件
                    ResReName.handleResFile(it, set, regx)
                }
            }
        }
    }

    /**
     * 文件名： xxx.xml  ---> 前缀_xxx.xml
     * @param file 资源文件目录根目录 res
     */
    static void renameFile(File file) {
        File[] dirs = file.listFiles(new ResReName.DirNamePrefixFilter("anim"))
        // 遍历
        dirs?.each { dir ->
            dir.eachFile { it ->
                String oldName = it.name
                String newName = ResReName.NEW_FREFIX + oldName
                if (oldName.startsWith(ResReName.OLD_FREFIX)) {
                    newName = ResReName.NEW_FREFIX + oldName.substring(ResReName.OLD_FREFIX.length())
                }
                File newFile = new File(it.getParent(), newName)
                if (newFile.exists()) {
                    newFile.delete()
                }
                if (!it.renameTo(newFile)) {
                    println("anim 文件 ${it.name} 重命名失败！，请手动修改成：${newFile.name}")
                }
            }
        }
    }
}
