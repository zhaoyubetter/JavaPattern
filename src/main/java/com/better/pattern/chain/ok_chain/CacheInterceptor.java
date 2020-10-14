package com.better.pattern.chain.ok_chain;

import java.io.IOException;

/**
 * 缓存拦截器具体实现
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public String intercept(Chain chain) throws IOException {
        String request = chain.request();
        request += "检查缓存，从缓存中获取数据--->";
        request += "缓存中无数据，接着从网络获取数据 ---> ";
        // 转发给下一个拦截器，确保拦截器执行
        final String response = chain.process(request);
        return response;
    }
}
