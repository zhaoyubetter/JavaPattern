package ok;

import ok.interceptor.Interceptor;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyu1
 * @date 2020/10/15 5:32 PM
 */
public class OkClient implements Cloneable, Call.Factory {

    final Dispatcher dispatcher;
    final List<Interceptor> interceptors;
    final List<Interceptor> networkInterceptors;
    final boolean followRedirects;
    final boolean retryOnConnectionFailure;
    final int connectTimeout;
    final int readTimeout;
    final int writeTimeout;
    final int pingInterval;


    public OkClient() {
        this(new Builder());
    }

    OkClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.interceptors = Util.immutableList(builder.interceptors);
        this.networkInterceptors = Util.immutableList(builder.networkInterceptors);
        boolean isTLS = false;

        this.followRedirects = builder.followRedirects;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.pingInterval = builder.pingInterval;
    }

    @Override
    public Call newCall(Request request) {
        return new RealCall(this, request);
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }

    public static final class Builder {
        Dispatcher dispatcher;
        Proxy proxy;
        final List<Interceptor> interceptors = new ArrayList<>();
        final List<Interceptor> networkInterceptors = new ArrayList<>();
        ProxySelector proxySelector;
        SocketFactory socketFactory;
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        boolean followSslRedirects;
        boolean followRedirects;
        boolean retryOnConnectionFailure;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        int pingInterval;

        public Builder() {
            dispatcher = new Dispatcher();
            proxySelector = ProxySelector.getDefault();
            socketFactory = SocketFactory.getDefault();
            followSslRedirects = true;
            followRedirects = true;
            retryOnConnectionFailure = true;
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
            pingInterval = 0;
        }

        /** Default connect timeout (in milliseconds). */
        public int connectTimeoutMillis() {
            return connectTimeout;
        }

        /** Default read timeout (in milliseconds). */
        public int readTimeoutMillis() {
            return readTimeout;
        }

        /** Default write timeout (in milliseconds). */
        public int writeTimeoutMillis() {
            return writeTimeout;
        }

        /** Web socket ping interval (in milliseconds). */
        public int pingIntervalMillis() {
            return pingInterval;
        }

        /**
         * Sets the connection pool used to recycle HTTP and HTTPS connections.
         *
         * <p>If unset, a new connection pool will be used.

        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) throw new NullPointerException("connectionPool == null");
            this.connectionPool = connectionPool;
            return this;
        }
         */
    }
}
