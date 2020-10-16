package ok;

import java.net.URL;
import java.util.List;

/**
 * An HTTP request. Instances of this class are immutable if their {@link #body} is null or itself
 * immutable.
 */
public final class Request {
    final String url;
    final String method;
    final Object tag;

    Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.tag = builder.tag != null ? builder.tag : this;
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public Object tag() {
        return tag;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        String url;
        String method;
        Object tag;

        public Builder() {
            this.method = "GET";
        }

        Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.tag = request.tag;
        }

        /**
         * Sets the URL target of this request.
         *
         * @throws IllegalArgumentException if {@code url} is not a valid HTTP or HTTPS URL. Avoid this
         *                                  exception by calling {@link HttpUrl#parse}; it returns null for invalid URLs.
         */
        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");

            // Silently replace web socket URLs with HTTP URLs.
            if (url.regionMatches(true, 0, "ws:", 0, 3)) {
                url = "http:" + url.substring(3);
            } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
                url = "https:" + url.substring(4);
            }
            this.url = url;
            return this;
        }

        /**
         * Attaches {@code tag} to the request. It can be used later to cancel the request. If the tag
         * is unspecified or null, the request is canceled by using the request itself as the tag.
         */
        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Request build() {
            if (url == null) throw new IllegalStateException("url == null");
            return new Request(this);
        }
    }
}