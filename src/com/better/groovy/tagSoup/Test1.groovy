package com.better.groovy.tagSoup


/**
 * Created by zhaoyu on 2017/5/2.
 */
// 分析网页下的新闻链接
def url = 'http://news.feng.com/'
def mainNode = getHtmlNode(url)
if (null != mainNode) {
    // html.body.'**'.th.em.a;
    def thItems = mainNode.body.'**'.div.h1.a; // '**' 拿到body 下所有元素，
    thItems?.each {
        if (it instanceof Node) {
            Node node = it as Node
            String href = node.attribute("href")
            String text = node.text()

            println(text)
        }
    }
}

/**
 * 获取html源代码 Node 节点
 * @param htmlUrl
 */
/**
 * 获得 html 源代码
 * @param requestUrl
 * @return
 */
def getHtmlNode(requestUrl) {
    def html = null
    try {
        def url = new URL(requestUrl);
        def connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        connection.connect();
        def parser = new XmlParser(new org.ccil.cowan.tagsoup.Parser())
        html = parser.parse(connection.inputStream)
    } catch (e) {
        println "\tRequest Failed:$requestUrl!"
        // getHtmlNode(requestUrl)
    }
    html
}

/**
 * 获取html源代码
 * @param requestUrl
 * @return
 */
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