package ok.interceptor;

import ok.Request;
import ok.Response;

import java.io.IOException;

/**
 * 最后一个拦截器，直接走源头拿数据，即：最原始的 Response
 * 不执行 chain.proceed()，也就是递归终结条件
 */
public class LastInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = new Response
                .Builder()
                .code(200)
                .message("ok")
                .request(request)
                .build();
        return response;
    }
}
