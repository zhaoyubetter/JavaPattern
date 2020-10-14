package com.better.pattern.chain.ok_chain;

import java.io.IOException;

/**
 * 处理网络请求拦截器
 */
public class ParserNetInterceptor implements Interceptor {
    @Override
    public String intercept(Chain chain) throws IOException {
        String request = chain.request();
        request += "链接成功，处理服务器返回数据 ---->";
        String response = chain.process(request);
        return response;
    }
}
