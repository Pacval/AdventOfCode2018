package aoc;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ExoEntryUtils {

    private static String getEntry(int day) throws IOException {
        String resourceFilename = "day" + day;
        return FileUtils.readFileToString(getResource(resourceFilename));
    }

    public static String[] getEntries(int day) throws IOException {
        return getEntry(day).split("\n");
    }

    private static File getResource(String resourceFilename) {
        ClassLoader classLoader = ExoEntryUtils.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(resourceFilename)).getFile());
    }
}
