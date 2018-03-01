package com.better.groovy.app.reptile

import java.util.concurrent.Callable


/**
 * Created by Administrator on 2016/11/20.
 * 分析威锋网epub 源码页,提取列表链接-条目分类,再提取其下载位置,写入文件
 */
// TagSoup是Java语言开发的，通过SAX引擎解析结构糟糕、令人抓狂的不规范HTML文档的小工具
@Grab(group = 'org.ccil.cowan.tagsoup', module = 'tagsoup', version = '1.2')
class HtmlWorker implements Callable<List<BookItem>> {
    def final List<BookItem> result
    def final start, end
    def final folder
    def final serverUrl
    def final url

    HtmlWorker(start, end, folder, serverUrl, url) {
        this.start = start
        this.end = end
        this.result = []
        this.folder = folder
        this.serverUrl = serverUrl
        this.url = url
    }

    /**
     * 返回 BookItem 集合
     * @return
     * @throws Exception
     */
    @Override
    List<BookItem> call() throws Exception {
        //第一步,获取所有当前页的内链
        int totalCount = 0
        List<BookItem> allItems = []
        while (start <= end) {
            def infoItems = []
            def bookItems = readHrefHtmlItem(String.format(url, start))

            for (int i = 0; i < bookItems.size(); i++) {
                //二步根据每一个提取的文章列表信息,提取文章信息
                def html = getHtml(bookItems[i].detailUrl)
                //三步根据文章详情,提取下载信息
                bookItems[i].url = readContentHtmlItem(html)
                println "\t列表:$i ${bookItems[i].book}-共发现${bookItems[i].url.size()}本书 ${0 == bookItems[i].url.size() ? "-Empty" : ""}"
            }
            //过滤未获取下载信息对象
            def filterItems = bookItems.findAll { 0 < it.url.size() }
            !filterItems ?: result.addAll(filterItems)

            filterItems.each { totalCount += it.url.size() }
            allItems += filterItems
//            def out=new StringBuffer()
//            infoItems.each { out.append(it)}
            println "第${start}页完成分析,共${bookItems.size()}条信息 获得:${filterItems.size()}个下载条目,过滤掉:${bookItems.size() - filterItems.size()}条数据,当前共:$totalCount}"
            start++
        }
        allItems
    }

    /**
     * 获得 html 源代码
     * @param requestUrl
     * @return
     */
    def getHtml(requestUrl) {
        def html = null
        try {
            def url = new URL(requestUrl);
            def connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.connect();
            def parser = new XmlParser(new org.ccil.cowan.tagsoup.Parser())
            html = parser.parse(connection.inputStream)
        } catch (e) {
            println "\tRequest Failed:$requestUrl 继续请求!" + e
            getHtml(requestUrl)
        }
        html
    }

    def getHtmlSource(requestUrl) {
        def html = null
        try {
            def url = new URL(requestUrl);
            def connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.connect();
            html = new String(connection.inputStream.bytes)
        } catch (e) {
            println "Request Failed:$requestUrl"
        }
        html
    }

    /**
     * 读取 html 源码页所有 th->em->a 标签下的 href页内链
     * @param requestUrl
     * @return
     */
    def readHrefHtmlItem(requestUrl) {
        def bookItems = []
        def html = getHtml(requestUrl)
        if (null != html) {
            def thItems = html.body.'**'.th.em.a;
            thItems?.each {
                if (it instanceof Node) {
                    Node node = it as Node
                    String href = node.attributes()["href"]
                    if (href.startsWith("forum.php?")) {
                        Node hrefNode = node.parent().parent().value()[1]       // 拿到第二个
                        bookItems << new BookItem(hrefNode.text(), node.text(), serverUrl + hrefNode.attributes()["href"])
                    }
                }
            }
        }
        bookItems
    }

    /**
     * 读取 html 源码页所有 th->a 标签文字是否匹配.epub
     * @param requestUrl
     * @return
     */
    def readContentHtmlItem(html) {
        def value = []
        //获取 a 标签内的跳转 url
        if (null != html) {
            def items = html.body.'**'.a
            if (items) {
                items?.each {
                    if (it instanceof Node) {
                        Node node = it as Node
                        if (node.text().endsWith(".epub") ||
                                node.text().endsWith(".pdf") ||
                                node.text().endsWith(".rar") ||
                                node.text().endsWith(".zip")) {
                            def func = node.attributes()["onclick"]
                            def matcher = func =~ /'([^']+)'/
                            !matcher ?: value << matcher[0][1]      // false 走后面的 value << matcher[0][1]
                        }
                    }
                }
            }
        }
        //二次处理获得下载 url
        def downloadUrl = []
        if (value) {
            value?.each {
                html = getHtmlSource(serverUrl + it)
                def matcher = html =~ /(<a\shref="([^"]+))/
                !matcher ?: downloadUrl << (serverUrl + matcher.group(2))
            }
        }
        downloadUrl
    }
}