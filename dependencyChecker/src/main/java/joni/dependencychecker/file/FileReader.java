package joni.dependencychecker.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author Jonatan Ivanov
 */
public class FileReader {
    public List<String> readLines(final String file) throws IOException {
        return readLines(new FileInputStream(file));
    }

    public List<String> readLines(final File file) throws IOException {
        return readLines(new FileInputStream(file));
    }

    public List<String> readLines(final InputStream stream) throws IOException {
        return IOUtils.readLines(stream);
    }
}
