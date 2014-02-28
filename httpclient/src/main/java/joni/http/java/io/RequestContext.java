package joni.http.java.io;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Jonatan Ivanov
 */
public class RequestContext {
    private static final String DEF_RQ_METHOD = HttpMethod.GET.name();

    private URL url;
    private String requestMethod;
    private Map<String, List<String>> headers;
    private byte[] requestBody;

    private int readTimeout = 0;
    private int connectTimeout = 0;
    private boolean followRedirects = true;
    private boolean useCaches = true;

    public RequestContext(URL url) {
        this(url, (String) null, null, null);
    }

    public RequestContext(URL url, String requestMethod) {
        this(url, requestMethod, null, null);
    }

    public RequestContext(URL url, HttpMethod requestMethod) {
        this(url, requestMethod, null, null);
    }

    public RequestContext(URL url, Map<String, List<String>> headers) {
        this(url, (String) null, headers, null);
    }

    public RequestContext(URL url, HttpMethod requestMethod, Map<String, List<String>> headers) {
        this(url, requestMethod, headers, null);
    }

    public RequestContext(URL url, String requestMethod, Map<String, List<String>> headers) {
        this(url, requestMethod, headers, null);
    }

    public RequestContext(URL url, HttpMethod requestMethod, byte[] requestBody) {
        this(url, requestMethod, null, requestBody);
    }

    public RequestContext(URL url, String requestMethod, byte[] requestBody) {
        this(url, requestMethod, null, requestBody);
    }

    public RequestContext(URL url, HttpMethod requestMethod, Map<String, List<String>> headers, byte[] requestBody) {
        this(url, (requestMethod != null) ? requestMethod.name() : null, headers, requestBody);
    }

    public RequestContext(URL url, String requestMethod, Map<String, List<String>> headers, byte[] requestBody) {
        if (url == null) {
            throw new IllegalArgumentException("URL must not be null!");
        }

        this.url = url;
        this.headers = (headers != null) ? Collections.unmodifiableMap(headers) : null;
        this.requestMethod = (requestMethod != null) ? requestMethod : DEF_RQ_METHOD;
        this.requestBody = (requestBody != null) ? requestBody.clone() : null;
    }

    public URL getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public byte[] getRequestBody() {
        return (requestBody != null) ? requestBody.clone() : null;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public boolean isUseCaches() {
        return useCaches;
    }

    public void setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
    }
}
