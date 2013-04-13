package joni.http.java.io;

import static java.lang.System.lineSeparator;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jonatan Ivanov
 */
public class ResponseContext {
    private static final String HEADER_SEPARATOR = ", ";

    private int responseCode;
    private String responseMessage;
    private Map<String, List<String>> headers;
    private String headersAsString;
    private byte[] responseBytes;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        this.headersAsString = getHeadersAsString(headers);
    }

    public String getHeadersAsString() {
        return headersAsString;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(byte[] responseBytes) {
        this.responseBytes = responseBytes;
    }

    private static String getHeadersAsString(Map<String, List<String>> headers) {
        StringBuilder sb = null;
        if (headers != null) {
            sb = new StringBuilder();
            for (String key : headers.keySet()) {
                if (key == null || key.equals("")) {
                    sb.append(StringUtils.join(headers.get(key), HEADER_SEPARATOR) + lineSeparator());
                }
                else {
                    sb.append(key + ": " + StringUtils.join(headers.get(key), HEADER_SEPARATOR) + lineSeparator());
                }
            }
        }

        return (sb != null) ? sb.toString() : null;
    }
}
