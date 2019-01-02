package com.better.groovy.app.reptile

import java.text.DecimalFormat
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


/**
 * Created by Administrator on 2017/1/31.
 */
class HtmlManager {
    def final MAX_SIZE = 100
    def final THREAD_COUNT = 10
    def final folder = new File("")
    def final serverUrl = "http://bbs.feng.com/"
    def final url = "http://bbs.feng.com/thread-htm-fid-224-page-%s.html"
    def downloadTotalCount = 0
    // 一组任务下的 ，需要下载的书bean集合
    List<Future<List<BookItem>>> tasks = []
    def static final HtmlManager manager = new HtmlManager()

    private HtmlManager() {
        if (!folder.exists()) {
            folder.mkdir()
        }
    }

    /**
     * 启动任务
     * @return
     */
    def static execute() {
        manager.run()
    }

    def run() {
        def xmlFile = new File(folder, "books.xml")
        def result = []
        if (xmlFile.exists()) {
            //解析xml生成
            def root = new XmlParser().parse(new File(folder, "books.xml"))
            root.item.each { Node node ->
                def bookItem = new BookItem(node.attributes()["name"], node.attributes()["type"], null)
                node.children().each { bookItem.url << it.attributes()["url"] }
                result << bookItem
            }
        } else {
            //直接抓取
            final def executor = Executors.newFixedThreadPool(THREAD_COUNT)
            final def count = MAX_SIZE / THREAD_COUNT
            0.upto(THREAD_COUNT - 1) {
                // 不断丢任务
                //1-10,11-20...
                tasks << executor.submit(new HtmlWorker(it * count + 1, (it + 1) * count, folder, serverUrl, url))
            }
            // 30分钟 timeout
            tasks.each {
                result += it.get(30, TimeUnit.MINUTES)
            }
            def bookCount = 0
            result.each { bookCount += it.url.size() }
            println "全部分析完毕,共计:$bookCount"
            //生成 xml
            println "生成下载文件"
            def file = new File(folder, "download.txt")
            file.withWriterAppend { writer ->
                result.each {
                    it.url?.each { url -> writer.append(url + "\n") }
                }
            }
            println "开始生成备份xml"
            def fileWriter = new FileWriter(new File(folder, "books.xml"))
            def xml = new groovy.xml.MarkupBuilder(fileWriter)
            xml.define([ct: new Date().toLocaleString()]) {
                result.each { bookItem ->
                    if (bookItem.url) {
                        item([name: bookItem.book, type: bookItem.type]) {
                            bookItem.url.each {
                                url([url: it])
                            }
                        }
                    }
                }
            }
        }
        println "开始下载书藉"
//        downloadTotalCount = 0
//        def allFailedItems = []
//        def logFolder = new extend(folder, "log")
//        logFolder.exists() ?: logFolder.mkdir()
//        //执行前清理log文件
//        logFolder.eachFile { it.delete() }
//        //开始检测所有书藉
//        result.each {
//            try {
//                allFailedItems += downloadBook(it)
//            } catch (e) {
//                //写入失败log
//                println "下载失败:$it.book"
//                def stackTrace = new StringWriter();
//                e.printStackTrace(new PrintWriter(stackTrace))
//                def logFile = new extend(logFolder, getFileName(it.book) + ".txt")
//                logFile.withWriter { it.write(stackTrace.toString()) }
//            }
//        }
//        //记录所有下载失败书藉
//        def failedFile = new extend(logFolder, "log")
//        failedFile.withWriter { writer ->
//            allFailedItems.each { writer.write(it + "\n") }
//        }
    }

    /**
     * 下载书本
     * @param item
     * @return
     */
    def downloadBook(BookItem item) {
        def failedItems = []
        println "开始下载:$item.book 共计:${item.url.size()}本"
        item.url?.each {
            try {
                def URL = new URL(it)
                def stream = URL.openStream()
                def connection = URL.openConnection()
                def matcher = URL.query =~ /name=([^&]+)/
                if (!matcher) {
                    println "not found extend name:$it"
                } else {
                    def len
                    byte[] arr = new byte[100 * 1024 * 1024]
                    def fileName = new URLDecoder().decode(matcher[0][1], URLEncoder.dfltEncName)
                    //过滤掉非法路径关键字符
                    def fileSuffix
                    int index = fileName.lastIndexOf(".")
                    if (index) {
                        fileSuffix = fileName.substring(index)
                        fileName = fileName.substring(0, index)
                    }
                    def bookName = getFileName(fileName)
                    !fileSuffix ?: (bookName += fileSuffix)
                    def fileFolder = new File(folder, item.type)
                    fileFolder.exists() ?: fileFolder.mkdir()
                    def file = new File(fileFolder, bookName)
                    if (file.exists()) {
                        println "\t第${downloadTotalCount}本\t$bookName 己经存在!"
                    } else {
                        def out = new FileOutputStream(file)
                        while ((len = stream.read(arr)) != -1) {
                            out.write(arr, 0, len)
                        }
                        DecimalFormat formatter = new DecimalFormat("0.00")
                        println("\t第${downloadTotalCount}本\t$bookName 文件大小:${formatter.format(connection.getContentLength() / 1024 / 1024)}M 下载完成！")
                        out.close()
                    }
                }
                stream.close()
            } catch (e) {
                //一本书下载失败了
                println "下载失败:$it"
                failedItems << it
            }
            downloadTotalCount++
            failedItems
        }
    }

    /**
     * 获得过滤路径非法字符后的名称
     * @param fileName
     * @return
     */
    String getFileName(fileName) {
        fileName.replaceAll("[^\\-\\(\\)\\w\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9\uFF00-\uFFEF\u2E80-\u2EFF\u3000-\u303F\u31C0-\u31EF\u2F00-\u2FDF\u2FF0-\u2FFF\u3100-\u312F\u31A0-\u31BF\u3040-\u309F\u30A0-\u30FF\u31F0-\u31FF\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F\u4DC0-\u4DFF\uA000-\uA48F\uA490-\uA4CF\u2800-\u28FF\u3200-\u32FF\u3300-\u33FF\u2700-\u27BF\u2600-\u26FF\uFE10-\uFE1F\uFE30-\uFE4F]", "")
    }
}
