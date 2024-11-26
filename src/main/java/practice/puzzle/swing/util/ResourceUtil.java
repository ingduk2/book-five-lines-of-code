package practice.puzzle.swing.util;

import practice.puzzle.swing.component.HowToWinLabel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResourceUtil {
    public static String getPath(String name) {
        URL resource = HowToWinLabel.class.getClassLoader().getResource(name);
        return resource != null ? resource.toString() : "";
    }

    public static String getContent(String name) {
        URL resource = HowToWinLabel.class.getClassLoader().getResource(name);

        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + name);
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return String.join("\n", lines);
    }
}
