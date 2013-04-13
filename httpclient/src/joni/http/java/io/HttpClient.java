package joni.http.java.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jonatan Ivanov
 */
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private HttpClient() {
    }

    /**
     * @param requestContext
     * @param responseContext
     * @throws IOException
     */
    public static void invoke(RequestContext rqContext, ResponseContext rsContext) throws IOException {
        if (rqContext == null)
            throw new IllegalArgumentException("RequestContext must not be null!");
        if (rsContext == null)
            throw new IllegalArgumentException("ResponseContext must not be null!");

        HttpURLConnection connection = getConnection(rqContext);
        String url = connection.getURL().toString();

        try {
            LOGGER.debug("Connecting: " + url);
            connection.connect();
            LOGGER.debug("Connected: " + url);
        }
        catch (IOException ioe) {
            LOGGER.error("Could not establish connection: " + url, ioe);
            throw ioe;
        }
        try {
            if (connection.getDoOutput()) {
                LOGGER.debug("Sending Request bytes: " + url);
                sendRequestBytes(connection, rqContext.getRequestBody());
                LOGGER.debug("Request bytes sent: " + url);
            }
        }
        catch (IOException ioe) {
            LOGGER.error("There was an I/O error, while sending Request bytes: " + url, ioe);
            throw ioe;
        }
        try {
            LOGGER.debug("Receiving Response: " + url);
            receiveResponse(connection, rsContext);
            LOGGER.debug("Response received: " + url);
        }
        catch (IOException ioe) {
            LOGGER.error("There was an I/O error, while receiving Response: " + url, ioe);
            throw ioe;
        }
        LOGGER.debug("Disonnecting: " + url);
        connection.disconnect();
        LOGGER.debug("Disonnected: " + url);
    }

    private static HttpURLConnection getConnection(RequestContext rqContext) throws IOException {
        URL url = rqContext.getUrl();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection == null)
            throw new IOException("Could not open connection: " + url);

        setupConnection(connection, rqContext);

        return connection;
    }

    private static void setupConnection(HttpURLConnection connection, RequestContext rqContext) throws ProtocolException {
        connection.setRequestMethod(rqContext.getRequestMethod());
        setHeaders(connection, rqContext.getHeaders());

        connection.setConnectTimeout(rqContext.getConnectTimeout());
        connection.setReadTimeout(rqContext.getReadTimeout());
        connection.setInstanceFollowRedirects(rqContext.isFollowRedirects());
        connection.setUseCaches(rqContext.isUseCaches());
        if (shouldDoOutput(rqContext)) {
            connection.setDoOutput(true);
        }
    }

    private static void setHeaders(HttpURLConnection connection, Map<String, List<String>> headers) {
        if (headers != null) {
            for (String key : headers.keySet()) {
                connection.addRequestProperty(key, StringUtils.join(headers.get(key), ", "));
            }
        }
    }

    private static boolean shouldDoOutput(RequestContext rqContext) {
        return (rqContext.getRequestBody() != null);
    }

    private static void sendRequestBytes(HttpURLConnection connection, byte[] requestBody) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
            if (outputStream != null) {
                outputStream.write(requestBody);
                outputStream.flush();
            }
        }
        finally {
            closeStream(outputStream);
        }
    }

    private static void receiveResponse(HttpURLConnection connection, ResponseContext rsContext) throws IOException {
        InputStream inputStream = null;
        byte[] responseBytes = null;

        rsContext.setHeaders(connection.getHeaderFields());
        try {
            rsContext.setResponseCode(connection.getResponseCode());
            rsContext.setResponseMessage(connection.getResponseMessage());

            inputStream = connection.getInputStream();
            responseBytes = (inputStream != null) ? IOUtils.toByteArray(inputStream) : null;
            rsContext.setResponseBytes(responseBytes);
        }
        catch (IOException ioe) {
            setErrorResponse(connection, rsContext);
            throw ioe;
        }
        finally {
            closeStream(inputStream);
        }
    }

    private static void setErrorResponse(HttpURLConnection connection, ResponseContext rsContext) {
        InputStream errorStream = connection.getErrorStream();
        if (errorStream != null) {
            try {
                rsContext.setResponseBytes(IOUtils.toByteArray(errorStream));
            }
            catch (IOException ioe) {
                LOGGER.error("There was an I/O error, while receiving Error Response: " + connection.getURL(), ioe);
            }
            finally {
                closeStream(errorStream);
            }
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            }
            catch (IOException ioe) {
                LOGGER.warn("Couldn't close stream", ioe);
            }
        }
    }
}
