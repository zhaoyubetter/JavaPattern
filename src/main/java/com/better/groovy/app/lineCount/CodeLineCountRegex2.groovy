package com.better.groovy.app.lineCount

/**
 * Created by zhaoyu on 2017/4/17.
 */
class CodeLineCountRegex2 extends Observable {
    // ===== 注释类型 ===
// 我是注释1  单号
// ---> /* 我是注释2  头
// ---> */ 我是注释2  尾

    def explainStr1 = 'def a = \'ddd\' //cc'
    def explainStr2 = '/***wojfjsjf'
    def explainStr3 = '*/  '
    def explainStr4 = '/*adfdfsfdsdf*/'
    def spaceLineStr = '    '

    def log_dir = "======》分析文件夹：%s"
    def log_file = "======》分析文件：%s"

    def regex1 = ~/^\s*\/\/.*/          // 表示注释1
    def regex2 = ~/^\s*\/\*.*\*\/\s*$/  // 表示注释2
    def regex2_start = ~/^\s*\/\*.*/    // 表示注释2头
    def regex2_end = ~/.*\*\/\s*$/      // 表示注释2尾
    def regex_space = ~/^\s*\s*$/       // 空行

    def explainLine = 0
    def codeLine = 0
    def spaceLine = 0
    def regex_begin = false       // 是否注释2开始了

    def message = ""
    def isStop = false

    def start(File file) {
        isStop = false
        message = ""
        explainLine = 0
        codeLine = 0
        spaceLine = 0
        regex2 = false

        startAnylise(file, file)
    }

    private def startAnylise(file, target) {
        if (isStop) {
            return
        }

        if (file.exists()) {
            if (file.isDirectory()) {
                file.eachFile { current ->
                    startAnylise(current, target)
                }
            } else {
                anylise(file)
            }
        }

        // 判断递归是否结束
        if (file == target) {
            stop()
        }
    }

    def stop() {
        if(!isStop) {
            isStop = true
            setChanged()
            notifyObservers()
        }
    }

    private def log(filename) {
        setChanged()
        notifyObservers(String.format(log_file, filename))
    }

    private def anylise(file) {
        if (isStop) {
            return
        }

        def fileName = file.name
        if (fileName.endsWith("java") || fileName.endsWith("groovy")) {
            log(fileName)
            file.each { line ->
                if (regex_begin) {
                    explainLine++
                    if ((line =~ regex2_end).matches()) {
                        explainLine++
                        regex_begin = false
                    }
                } else {
                    if ((line =~ regex1).matches()) {
                        explainLine++
                    } else if ((line =~ regex2).matches()) {
                        explainLine++
                    } else if ((line =~ regex2_start).matches()) {
                        regex_begin = true
                    } else if ((line =~ regex_space).matches()) {
                        spaceLine++
                    } else {
                        codeLine++
                    }
                }
            }
        }
    }
}
