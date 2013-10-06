package joni.dependencychecker.beans;

import lombok.Data;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jonatan Ivanov
 */
@Data
@JsonIgnoreProperties({ "id", "p", "timestamp", "tags", "ec" })
public class Dependency implements Comparable<Dependency> {
    @JsonProperty("g")
    private String groupId;
    @JsonProperty("a")
    private String artifactId;
    @JsonProperty("v")
    private ArtifactVersion version;

    public void setVersion(final String version) {
        this.version = new DefaultArtifactVersion(version);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dependency other = (Dependency) obj;
        if (artifactId == null) {
            if (other.artifactId != null)
                return false;
        }
        else if (!artifactId.equals(other.artifactId))
            return false;
        if (groupId == null) {
            if (other.groupId != null)
                return false;
        }
        else if (!groupId.equals(other.groupId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (artifactId == null ? 0 : artifactId.hashCode());
        result = prime * result + (groupId == null ? 0 : groupId.hashCode());
        return result;
    }

    @Override
    public int compareTo(final Dependency o) {
        return this.version.compareTo(o.version);
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }
}
