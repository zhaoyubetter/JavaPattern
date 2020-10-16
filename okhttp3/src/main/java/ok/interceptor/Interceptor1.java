package ok.interceptor;

import ok.Request;
import ok.Response;

import java.io.IOException;

public class Interceptor1 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
