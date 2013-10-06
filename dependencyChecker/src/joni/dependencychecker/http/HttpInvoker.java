/**
 * 
 */
package joni.dependencychecker.http;

import java.util.List;
import java.util.concurrent.TimeUnit;

import joni.dependencychecker.beans.Dependency;
import joni.dependencychecker.beans.SearchResult;

import org.eclipse.jetty.client.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jonatan Ivanov
 */
public class HttpInvoker {
    private static final String mvnUrl = "http://search.maven.org";
    private static final String format = mvnUrl + "/solrsearch/select?q=g:%s+AND+a:%s&core=gav&rows=1&wt=json";

    private static final HttpClient client = new HttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        initalize();
    }

    protected HttpInvoker() {
    }

    public Dependency getLatestDependency(final Dependency dependency) throws Exception {
        Dependency result = null;

        String uri = String.format(format, dependency.getGroupId(), dependency.getArtifactId());
        String response = client.GET(uri).getContentAsString();
        List<Dependency> dependencies = mapper.readValue(response, SearchResult.class).getResponse().getDependencies();
        if (!dependencies.isEmpty()) {
            result = dependencies.get(0);
        }

        return result;
    }

    public void start() throws Exception {
        client.start();
    }

    public void stop() throws Exception {
        client.stop();
    }

    private static void initalize() {
        client.setAddressResolutionTimeout(TimeUnit.SECONDS.toMillis(3));
        client.setConnectTimeout(TimeUnit.SECONDS.toMillis(3));
        client.setIdleTimeout(TimeUnit.SECONDS.toMillis(5));
        client.setFollowRedirects(true);
    }
}
