package com.better.pattern.chain.ok_chain;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * okhttp 中拦截器剥离
 *
 * @author zhaoyu1
 * https://blog.csdn.net/qq_15274383/article/details/78485648
 */
public class ZTest {
    @org.junit.Test
    public void test2() throws IOException {
        String request = "网络请求...";
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new CacheInterceptor());
        interceptors.add(new ConnectionInterceptor());
        interceptors.add(new ParserNetInterceptor());
        interceptors.add(new ResultCallbackInterceptor());
        // 关键的一步:
        // 创建拦截链，客户端调用时，需要确保框架从第一个拦截器开始，索引传递 0 ；
        RealInterceptorChain chain = new RealInterceptorChain(interceptors, 0, request);
        final String response = chain.process(request);
        System.out.println(response);
    }
}
