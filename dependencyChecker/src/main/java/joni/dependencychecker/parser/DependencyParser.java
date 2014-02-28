package joni.dependencychecker.parser;

import java.util.ArrayList;
import java.util.List;

import joni.dependencychecker.beans.Dependency;

/**
 * @author Jonatan Ivanov
 */
public class DependencyParser {
    public List<Dependency> parse(final List<String> list) {
        List<Dependency> dependencies = new ArrayList<>();

        if (list != null) {
            for (String str : list) {
                Dependency dependency = parse(str);
                if (dependency != null) {
                    dependencies.add(dependency);
                }
                else {
                    System.err.println("input failure: " + str);
                }
            }
        }
        else {
            System.err.println("input failure: " + list);
        }

        return dependencies;
    }

    public Dependency parse(final String str) {
        Dependency dependency = null;

        if (str != null) {
            String[] splitted = str.split(":");
            if (splitted != null && splitted.length > 2) {
                if (dependency == null) {
                    dependency = new Dependency();
                }
                dependency.setGroupId(splitted[0]);
                dependency.setArtifactId(splitted[1]);
                dependency.setVersion(splitted[2]);
            }
        }

        return dependency;
    }
}
