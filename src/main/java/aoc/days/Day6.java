package aoc.days;

import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;

public class Day6 {

    @Getter
    @Setter@AllArgsConstructor
    private static class Point {
        private int x;
        private int y;
    }

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(6, 1);

        List<Point> points = new ArrayList<>();
        for(String entry : entries) {
            points.add(new Point(Integer.parseInt(entry.substring(0, entry.indexOf(","))),
                    Integer.parseInt(entry.substring(entry.indexOf(",") + 2))));
        }

        // On calcule les positions min et max des points
        Integer xMin = points.stream().map(Point::getX).min(Comparator.naturalOrder()).get();
        Integer xMax = points.stream().map(Point::getX).max(Comparator.naturalOrder()).get();
        Integer yMin = points.stream().map(Point::getY).min(Comparator.naturalOrder()).get();
        Integer yMax = points.stream().map(Point::getY).max(Comparator.naturalOrder()).get();

        // On va stocker dans une
        Map<Point, Integer>

        // On boucle sur tous les points du repère et on note le point le plus proche
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(6, 1);

    }
}
