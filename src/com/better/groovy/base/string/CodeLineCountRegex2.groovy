package com.better.groovy.base.string

/**
 * Created by zhaoyu on 2017/4/17.
 */
class CodeLineCountRegex2 {
    // ===== 注释类型 ===
// 我是注释1  单号
// ---> /* 我是注释2  头
// ---> */ 我是注释2  尾
    def explainStr1 = 'def a = \'ddd\' //cc'
    def explainStr2 = '/***wojfjsjf'
    def explainStr3 = '*/  '
    def explainStr4 = '/*adfdfsfdsdf*/'
    def spaceLineStr = '    '

    def regex1 = ~/^\s*\/\/.*/          // 表示注释1
    def regex2 = ~/^\s*\/\*.*\*\/\s*$/  // 表示注释2
    def regex2_start = ~/^\s*\/\*.*/    // 表示注释2头
    def regex2_end = ~/.*\*\/\s*$/      // 表示注释2尾
    def regex_space = ~/^\s*\s*$/       // 空行

    def explainLine = 0
    def codeLine = 0
    def spaceLine = 0
    def begin = false

    def codeLineCount(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { current ->
                    codeLineCount(current)
                }
            } else {
                anylise(file)
            }
        }
    }

    def anylise(file) {
        file.each { line ->
            if (begin) {
                explainLine++
                if ((line =~ regex2_end).matches()) {
                    explainLine++
                    begin = false
                }
            } else {
                if ((line =~ regex1).matches()) {
                    explainLine++
                } else if ((line =~ regex2).matches()) {
                    explainLine++
                } else if ((line =~ regex2_start).matches()) {
                    begin = true;
                } else if ((line =~ regex_space).matches()) {
                    spaceLine++
                } else {
                    codeLine++
                }
            }
        }
    }
}
