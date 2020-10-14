package com.better.pattern.chain.ok_chain;

import java.io.IOException;

/**
 * 网络链接拦截器
 */
public class ConnectionInterceptor implements Interceptor {
    @Override
    public String intercept(Chain chain) throws IOException {
        String request = chain.request();
        request += "开始链接到服务器--->";
        String response = chain.process(request);
        return response;
    }
}
