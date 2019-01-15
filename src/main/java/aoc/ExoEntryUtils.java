package aoc;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ExoEntryUtils {

    public static String getEntry(int day) throws IOException {
        String resourceFilename = "day" + day;
        return FileUtils.readFileToString(getResource(resourceFilename));
    }

    public static String[] getEntries(int day) throws IOException {
        return getEntry(day).split("\r\n");
    }

    public static File getResource(String resourceFilename) {
        ClassLoader classLoader = ExoEntryUtils.class.getClassLoader();
        return new File(classLoader.getResource(resourceFilename).getFile());
    }
}
