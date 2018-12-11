package aoc.days;

import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Point {
        private int x;
        private int y;
    }

    @Getter
    @Setter
    private static class DefinedPoint extends Point {
        private String name;

        DefinedPoint(int x, int y, String name) {
            super(x, y);
            this.name = name;
        }
    }

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(6, 1);

        List<DefinedPoint> definedPoints = new ArrayList<>();
        char name = 'A';
        for (String entry : entries) {
            definedPoints.add(new DefinedPoint(Integer.parseInt(entry.substring(0, entry.indexOf(","))),
                    Integer.parseInt(entry.substring(entry.indexOf(",") + 2)),
                    String.valueOf(name)));
            name++;
            if (name == '[') { // caractère après le A -> on recommence à a
                name = 'a';
            }
        }

        // On calcule les positions min et max des points
        Integer xMin = definedPoints.stream().map(Point::getX).min(Comparator.naturalOrder()).get();
        Integer xMax = definedPoints.stream().map(Point::getX).max(Comparator.naturalOrder()).get();
        Integer yMin = definedPoints.stream().map(Point::getY).min(Comparator.naturalOrder()).get();
        Integer yMax = definedPoints.stream().map(Point::getY).max(Comparator.naturalOrder()).get();

        // On va stocker dans une map tous les points du graphique et le point le plus proche
        Map<Point, String> graphPoints = new HashMap<>();

        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                Point newPoint = new Point(i, j);

                // On calcule pour tous les points définis la distance avec le point de la boucle
                Map<String, Integer> distances = new HashMap<>();
                for (DefinedPoint definedPoint : definedPoints) {
                    distances.put(definedPoint.getName(), Math.abs(newPoint.getX() - definedPoint.getX()) + Math.abs(newPoint.getY() - definedPoint.getY()));
                }

                // On récupère la ou les distances minimums
                Integer minDistance = distances.entrySet().stream().min((e1, e2) -> (e1.getValue().compareTo(e2.getValue()))).get().getValue();
                List<String> closestPoints = distances.entrySet().stream()
                        .filter(dist -> dist.getValue().equals(minDistance))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                if (closestPoints.size() == 1) {
                    graphPoints.put(newPoint, closestPoints.get(0));
                    System.out.print(closestPoints.get(0));
                } else if (closestPoints.size() > 1) {
                    graphPoints.put(newPoint, ".");
                    System.out.print(".");
                } else {
                    System.out.println("PROBLEME : liste de points les plus proches vide");
                }
            }
            System.out.println();
        }

        // On récupère tous les noms des points les plus proches, des points aux extrémités du graphs -> ce sont les points dont la zone est infinie
        List<String> pointsOnBorder = graphPoints.entrySet().stream().filter(point -> point.getKey().getX() == xMin ||
                point.getKey().getX() == xMax ||
                point.getKey().getY() == yMin ||
                point.getKey().getY() == yMax).map(Map.Entry::getValue).collect(Collectors.toList());

        // On récupère tous les points du graph qui ne font pas parti d'une zone infinie
        Map<Point, String> pointsOnNotInfiniteArea = graphPoints.entrySet().stream()
                .filter(point -> !pointsOnBorder.contains(point.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Pour chaque point défini (lettre) on récupère son occurrence dans le graphe
        Map<String, Long> lettersOccurences = pointsOnNotInfiniteArea.values().stream()
                .collect(Collectors.groupingBy(closestPoint -> closestPoint, Collectors.counting()));

        // On récupère le max
        Map.Entry<String, Long> biggestArea = lettersOccurences.entrySet().stream().max((e1,e2) -> e1.getValue().compareTo(e2.getValue())).get();
        System.out.println("Letter of biggest not infinite area : " + biggestArea.getKey());
        System.out.println("Size of biggest not infinite area : " + biggestArea.getValue());
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(6, 1);

    }
}
