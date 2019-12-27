import org.junit.Test
import java.net.URL

class UrlTest {

    @Test
    fun test1() {
        val url = URL("http://wwww.sonar.vip:8080/aaa/bbb?appId=55&token=token123456")
        println(url.protocol)   // http
        println(url.port)       // 8080
        println(url.host)       // wwww.sonar.vip
        println(url.query)      // appId=55&token=token123456
        println(url.path)      // /aaa/bbb

        println("http://wwww.sonar.vip:8080/aaa/bbb?appId=55&token=token123456".substringBefore("?"))
        // 解析参数
        val params = HashMap<String, String>()
        url.query.split("&").forEach { it ->
            val list = it.split("=")
            params[list[0]] = list[1]
        }
        println(params)
    }
}