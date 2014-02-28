/**
 * 
 */
package joni.dependencychecker.beans;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jonatan Ivanov
 * 
 */
@Data
@JsonIgnoreProperties({ "responseHeader" })
public class SearchResult {
    private Response response;

    @Data
    @JsonIgnoreProperties({ "numFound", "start" })
    public static class Response {
        @JsonProperty("docs")
        private List<Dependency> dependencies;
    }
}
