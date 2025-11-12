package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FindDataDirectory {
    public static Path findDataDirectory() {
        Path current = Paths.get(System.getProperty("user.dir"));

        List<Path> candidates = List.of(
                current.resolve("data"),
                current.resolve("../data"),
                current.resolve("../../data")
        );

        for (Path candidate : candidates) {
            if (Files.exists(candidate) && Files.isDirectory(candidate)) {
                return candidate.normalize();
            }
        }

        throw new RuntimeException("Data directory not found");
    }


}
