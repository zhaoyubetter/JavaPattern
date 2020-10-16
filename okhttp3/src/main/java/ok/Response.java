package ok;

/**
 * @author zhaoyu1
 * @date 2020/10/15 5:31 PM
 */

import java.io.Closeable;

import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_MULT_CHOICE;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

/**
 * An HTTP response. Instances of this class are not immutable: the response body is a one-shot
 * value that may be consumed only once and then closed. All other properties are immutable.
 *
 * <p>This class implements {@link Closeable}. Closing it simply closes its response body. See
 */
public final class Response implements Closeable {
    final Request request;
    final int code;
    final String message;
    final Response networkResponse;
    final Response cacheResponse;
    final Response priorResponse;
    final long sentRequestAtMillis;
    final long receivedResponseAtMillis;


    Response(Builder builder) {
        this.request = builder.request;
        this.code = builder.code;
        this.message = builder.message;
        this.networkResponse = builder.networkResponse;
        this.cacheResponse = builder.cacheResponse;
        this.priorResponse = builder.priorResponse;
        this.sentRequestAtMillis = builder.sentRequestAtMillis;
        this.receivedResponseAtMillis = builder.receivedResponseAtMillis;
    }

    /**
     * The wire-level request that initiated this HTTP response. This is not necessarily the same
     * request issued by the application:
     *
     * <ul>
     * <li>It may be transformed by the HTTP client. For example, the client may copy headers like
     * {@code Content-Length} from the request body.
     * <li>It may be the request generated in response to an HTTP redirect or authentication
     * challenge. In this case the request URL may be different than the initial request URL.
     * </ul>
     */
    public Request request() {
        return request;
    }

    /**
     * Returns the HTTP status code.
     */
    public int code() {
        return code;
    }

    /**
     * Returns true if the code is in [200..300), which means the request was successfully received,
     * understood, and accepted.
     */
    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    /**
     * Returns the HTTP status message or null if it is unknown.
     */
    public String message() {
        return message;
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Returns true if this response redirects to another resource.
     */
    public boolean isRedirect() {
        switch (code) {
            case HTTP_MULT_CHOICE:
            case HTTP_MOVED_PERM:
            case HTTP_MOVED_TEMP:
            case HTTP_SEE_OTHER:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns the raw response received from the network. Will be null if this response didn't use
     * the network, such as when the response is fully cached. The body of the returned response
     * should not be read.
     */
    public Response networkResponse() {
        return networkResponse;
    }

    /**
     * Returns the raw response received from the cache. Will be null if this response didn't use the
     * cache. For conditional get requests the cache response and network response may both be
     * non-null. The body of the returned response should not be read.
     */
    public Response cacheResponse() {
        return cacheResponse;
    }

    /**
     * Returns the response for the HTTP redirect or authorization challenge that triggered this
     * response, or null if this response wasn't triggered by an automatic retry. The body of the
     * returned response should not be read because it has already been consumed by the redirecting
     * client.
     */
    public Response priorResponse() {
        return priorResponse;
    }


    /**
     * Returns a {@linkplain System#currentTimeMillis() timestamp} taken immediately before OkHttp
     * transmitted the initiating request over the network. If this response is being served from the
     * cache then this is the timestamp of the original request.
     */
    public long sentRequestAtMillis() {
        return sentRequestAtMillis;
    }

    /**
     * Returns a {@linkplain System#currentTimeMillis() timestamp} taken immediately after OkHttp
     * received this response's headers from the network. If this response is being served from the
     * cache then this is the timestamp of the original response.
     */
    public long receivedResponseAtMillis() {
        return receivedResponseAtMillis;
    }

    /**
     * Closes the response body. Equivalent to {@code body().close()}.
     */
    @Override
    public void close() {
//        body.close();
    }


    public static class Builder {
        Request request;
        int code = -1;
        String message;
        Response networkResponse;
        Response cacheResponse;
        Response priorResponse;
        long sentRequestAtMillis;
        long receivedResponseAtMillis;

        public Builder() {
        }

        Builder(Response response) {
            this.request = response.request;
            this.code = response.code;
            this.message = response.message;
            this.networkResponse = response.networkResponse;
            this.cacheResponse = response.cacheResponse;
            this.priorResponse = response.priorResponse;
            this.sentRequestAtMillis = response.sentRequestAtMillis;
            this.receivedResponseAtMillis = response.receivedResponseAtMillis;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder networkResponse(Response networkResponse) {
            this.networkResponse = networkResponse;
            return this;
        }

        public Builder cacheResponse(Response cacheResponse) {
            if (cacheResponse != null) checkSupportResponse("cacheResponse", cacheResponse);
            this.cacheResponse = cacheResponse;
            return this;
        }

        private void checkSupportResponse(String name, Response response) {
            if (response.networkResponse != null) {
                throw new IllegalArgumentException(name + ".networkResponse != null");
            } else if (response.cacheResponse != null) {
                throw new IllegalArgumentException(name + ".cacheResponse != null");
            } else if (response.priorResponse != null) {
                throw new IllegalArgumentException(name + ".priorResponse != null");
            }
        }

        public Builder sentRequestAtMillis(long sentRequestAtMillis) {
            this.sentRequestAtMillis = sentRequestAtMillis;
            return this;
        }

        public Builder receivedResponseAtMillis(long receivedResponseAtMillis) {
            this.receivedResponseAtMillis = receivedResponseAtMillis;
            return this;
        }

        public Response build() {
            if (request == null) throw new IllegalStateException("request == null");
            if (code < 0) throw new IllegalStateException("code < 0: " + code);
            return new Response(this);
        }
    }
}
