package com.better.groovy.app.getImage

import com.better.groovy.app.reptile.BookItem


/**
 * findViewById
 * Created by zhaoyu on 2017/4/19.
 */
class BaiduImage {

    //           http://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=%E8%8B%B9%E6%9E%9C&pn=20&gsm=3c&ct=&ic=0&lm=-1&width=0&height=0
    static final URL = "http://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=%E8%8B%B9%E6%9E%9C&ct=201326592&ic=0&lm=-1&width=&height=&v=flip"


    /**
     * 获得 html 源代码
     * @param requestUrl
     * @return
     */
    def getHtml(requestUrl) {
        def html = null
        try {
            def url = new URL(requestUrl)
            def connection = url.openConnection()
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:59.0) Gecko/20100101 Firefox/59.0")
            connection.connect()
            def parser = new XmlParser(new org.ccil.cowan.tagsoup.Parser())
            html = parser.parse(connection.inputStream)
        } catch (e) {
            println "\tRequest Failed:$requestUrl 继续请求!" + e
//            getHtml(requestUrl)
        }
        html
    }

    def getHtmlFromFile() {
        InputStream is = this.getClass().getResourceAsStream("source.html")
        def parser = new XmlParser(new org.ccil.cowan.tagsoup.Parser())
        return parser.parse(is)
    }

    public static void main(String[] args) {
        def html = new BaiduImage().getHtmlFromFile()
        if (null != html) {
            def thItems = html.body.div.div.div.div.ul.li.div.'**'.a.img
            thItems?.each {
                if (it instanceof Node) {
                    Node node = it as Node
                    String src = node.attributes()["src"]
                    if (src.startsWith("http")) {
                        println(src)
                    }
                }
            }
        }
    }
}
