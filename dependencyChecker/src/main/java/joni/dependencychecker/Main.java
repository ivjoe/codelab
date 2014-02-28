package joni.dependencychecker;

import java.util.List;

import joni.dependencychecker.beans.Dependency;
import joni.dependencychecker.file.FileReader;
import joni.dependencychecker.http.HttpInvoker;
import joni.dependencychecker.http.HttpInvokerFactory;
import joni.dependencychecker.parser.DependencyParser;

/**
 * @author Jonatan Ivanov
 */
public class Main {
    private static final FileReader reader = new FileReader();
    private static final DependencyParser parser = new DependencyParser();
    private static final HttpInvoker httpInvoker = HttpInvokerFactory.getInstance();

    /**
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        List<String> lines = reader.readLines("dependencies.txt");
        List<Dependency> dependencies = parser.parse(lines);

        try {
            httpInvoker.start();
            for (Dependency dependency : dependencies) {
                Dependency searchResult = httpInvoker.getLatestDependency(dependency);
                if (dependency.equals(searchResult)) {
                    if (searchResult.compareTo(dependency) > 0) {
                        System.out.println("UPDATE FOUND " + searchResult);
                    }
                    else {
                        System.out.println(searchResult);
                    }
                }
                else {
                    System.out.println("not found: " + dependency);
                }
            }
        }
        finally {
            httpInvoker.stop();
        }
    }
}
