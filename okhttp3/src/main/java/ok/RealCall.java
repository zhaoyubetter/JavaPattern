package ok;

import ok.interceptor.Interceptor;
import ok.interceptor.LastInterceptor;
import ok.interceptor.RealInterceptorChain;
import ok.interceptor.RetryAndFollowUpInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyu1
 * @date 2020/10/15 5:40 PM
 */
final class RealCall implements Call {

    final OkClient client;
    final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

    /**
     * The application's original request unadulterated by redirects or auth headers.
     */
    final Request originalRequest;

    // Guarded by this.
    private boolean executed;

    RealCall(OkClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client);
    }

    @Override
    public Request request() {
        return originalRequest;
    }

    @Override
    public Response execute() throws IOException {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }
        try {
            client.dispatcher().executed(this);
            Response result = getResponseWithInterceptorChain();
            if (result == null) throw new IOException("Canceled");
            return result;
        } finally {
            client.dispatcher().finished(this);
        }
    }


    @Override
    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }
        client.dispatcher().enqueue(new AsyncCall(responseCallback));
    }

    @Override
    public void cancel() {
        retryAndFollowUpInterceptor.cancel();
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public boolean isCanceled() {
        return retryAndFollowUpInterceptor.isCanceled();
    }

    @Override
    public Call clone() {
        return new RealCall(client, originalRequest);
    }

    Response getResponseWithInterceptorChain() throws IOException {
        // Build a full stack of interceptors.
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.interceptors);
        interceptors.add(retryAndFollowUpInterceptor);
        interceptors.add(new LastInterceptor());
//        interceptors.add(new CallServerInterceptor(forWebSocket));
        Interceptor.Chain chain = new RealInterceptorChain(interceptors, 0, originalRequest);
        return chain.proceed(originalRequest);
    }

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        AsyncCall(Callback responseCallback) {
            super("OkHttp %s", "");
            this.responseCallback = responseCallback;
        }

        String host() {
            return originalRequest.url();
        }

        Request request() {
            return originalRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        @Override
        protected void execute() {
            boolean signalledCallback = false;
            try {
                Response response = getResponseWithInterceptorChain();
                if (retryAndFollowUpInterceptor.isCanceled()) {
                    signalledCallback = true;
                    responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                } else {
                    signalledCallback = true;
                    responseCallback.onResponse(RealCall.this, response);
                }
            } catch (IOException e) {
                if (signalledCallback) {
                    // Do not signal the callback twice!
//                    Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
                } else {
                    responseCallback.onFailure(RealCall.this, e);
                }
            } finally {
                client.dispatcher().finished(this);
            }
        }
    }
}
