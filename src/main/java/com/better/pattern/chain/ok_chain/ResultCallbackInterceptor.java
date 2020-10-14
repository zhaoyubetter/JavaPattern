package com.better.pattern.chain.ok_chain;

import java.io.IOException;

/**
 * 结果响应到客户端拦截器
 */
public class ResultCallbackInterceptor implements Interceptor {
    @Override
    public String intercept(Chain chain) throws IOException {
        String request = chain.request();
        request += "向客户端返回数据";
        // 最后一步直接返回数据了，不再派发了
        return request;
    }
}
