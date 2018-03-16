package com.better.groovy.forandroid.resource

/**
 * raw 文件内容引用名称的重命名与raw文件名的rename
 * raw 目前只在源代码中使用,这个工具类，只修改文件名
 * 1.. 文件名： xxx.xml  ---> 前缀_xxx.xml
 *
 */
final class RawRenameTools {
    /**
     * 文件名： xxx.xml  ---> 前缀_xxx.xml
     * @param file 资源文件目录根目录 res
     */
    static void renameFile(File file) {
        File[] dirs = file.listFiles(new ResReName.DirNamePrefixFilter("raw"))
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
                    println("raw 文件 ${it.name} 重命名失败！，请手动修改成：${newFile.name}")
                }
            }
        }
    }
}
