package com.better.groovy.forandroid.module_separate


class Tools {

    final static OUTPUT = "out/android/export/"
    final static USER_CURRENT_DIR = System.getProperty("user.dir")

    // 模块dir
    final static MODULE_DIR = ""
    // 宿主App对应的Res目录
    final static APP_RES_DIR = ""


    public static void checkDir(String moduleDir, String appResDir) {
        File moduleDirFile = new File(moduleDir)
        if (!moduleDirFile.exists() || !moduleDirFile.isDirectory()) {
            throw new RuntimeException("the Aodule ${moduleDir} Directory is wrong!")
        }
        // app 主工程资源目录，默认values
        File tappResDir = new File(appResDir)
        if (!tappResDir.exists() || !tappResDir.isDirectory()) {
            throw new RuntimeException("the App  ${appResDir} Res Directory is wrong!")
        }
    }

// 文件夹名称过滤
    final static class DirNamePrefixFilter implements FilenameFilter {
        def prefix = ""

        DirNamePrefixFilter(String prefix) {
            this.prefix = prefix
        }

        @Override
        boolean accept(File dir, String name) {
            return dir.isDirectory() && name.startsWith(prefix)
        }
    }

    final static class FileNameSuffixFilter implements FileFilter {
        def suffix = ""

        def FileNameSuffixFilter(suffix) {
            this.suffix = suffix
        }

        @Override
        boolean accept(File pathname) {
            return pathname.name.endsWith(suffix)
        }
    }
}