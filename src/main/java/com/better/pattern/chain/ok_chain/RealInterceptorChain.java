package com.better.pattern.chain.ok_chain;

import java.io.IOException;
import java.util.List;

/**
 * 无论外界拦截器如何变化，此类都不需要动
 * Chain 只有这一个实现类，不断实例化她来形成最终链条，并且每个 chain 实例对应特定的 Interceptor
 * <p>
 * 在RealInterceptorChain拦截链中，只有创建下一节点拦截链、获取当前拦截器、执行当前拦截器并返回结果的方法，这里并没有对拦截器的具体实现产生依赖，
 * 其拦截器容器中定义的泛型，都是对Interceptor这个抽象接口产生的依赖。
 * 这样的设计就实现了拦截链RealInterceptorChain与客户端调用之间的松耦合，
 * 即使某个拦截器发生了改变，或流程顺序发生改变等，RealInterceptorChain都不需要进行任何调整
 */
class RealInterceptorChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;
    /**
     * 拦截器光标，根据此光标访问并获取下一个拦截器，并将其执行，从而实现链式调用
     */
    private int index;
    // 客户端请求
    private String request;

    /**
     * @param interceptors
     * @param index
     * @param request
     */
    public RealInterceptorChain(List<Interceptor> interceptors, int index, String request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public String process(String request) throws IOException {
        if (index > interceptors.size()) {
            return null;
        }
        // 创建下一个拦截链，并将index更新传入，从而使拦截链可执行 interceptors的下一个拦截器
        RealInterceptorChain next = new RealInterceptorChain(
                interceptors,
                index + 1,
                request);
        // 获取当前拦截器
        Interceptor interceptor = interceptors.get(index);
        // 执行拦截器操作
        String response = interceptor.intercept(next);
        return response;
    }

    @Override
    public String request() {
        return this.request;
    }
}