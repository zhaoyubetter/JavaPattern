package com.better.pattern.chain.ok_chain;

import java.io.IOException;

/**
 * Interceptor
 * 此接口相当于责任链接口，每个具体实现，相当于责任链中的每个环节；
 * 每个环节完成后，将数据交给下一环节继续处理，每个环节更加独立
 */
interface Interceptor {

    /**
     * Chain
     * 拦截器链
     */
    interface Chain {
        // 返回请求
        String request();

        /**
         * 每次调用，都将创建 RealInterceptorChain 的 next 对象，并根据当前 index 索引
         * 取出当前拦截器，执行 intercept 方法，并传入 next
         *
         * @param request
         * @return
         * @throws IOException
         */
        String process(String request) throws IOException;
    }

    /**
     * 拦截操作：
     * 每个拦截器将会在此方法根据拦截器链 Chain
     * 触发对下一个拦截器的调用[ chain.process() ]，直到最后一个不进行触发
     *
     * @param chain 拦截器
     * @return 拦截器的处理结果
     */
    String intercept(Chain chain) throws IOException;
}