package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 implements DayInterface {

    @AllArgsConstructor
    @Data
    private static class Position {
        int x;
        int y;
    }

    private enum Direction {
        DOWN, LEFT, RIGHT
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(17);

        Pattern pattern = Pattern.compile("(.)=(\\d*), .=(\\d*)..(\\d*)");

        // on doit d'abord trouver les bornes de la carte
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        // pour éviter de parcourir les entrées 2 fois on stocke les positions des clays
        Set<Position> clays = new HashSet<>();

        for (String line : entries) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                break;
            }
            if (matcher.group(1).equals("x")) {
                int x = Integer.parseInt(matcher.group(2));
                for (int y = Integer.parseInt(matcher.group(3)); y <= Integer.parseInt(matcher.group(4)); y++) {
                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                    clays.add(new Position(x, y));
                }
            } else if (matcher.group(1).equals("y")) {
                int y = Integer.parseInt(matcher.group(2));
                for (int x = Integer.parseInt(matcher.group(3)); x <= Integer.parseInt(matcher.group(4)); x++) {
                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                    clays.add(new Position(x, y));
                }
            } else {
                System.out.println("Error : " + line);
            }
        }

        // on se laisse un offset de 10 (5 de chaque coté) pour les débordements
        String[][] map = new String[yMax + 1][xMax - xMin + 10];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (clays.contains(new Position(x + xMin - 5, y))) {
                    map[y][x] = "#";
                } else {
                    map[y][x] = ".";
                }
            }
        }

        flow(map, 0, 500 - xMin + 5, Direction.DOWN);

        try (BufferedWriter writer = Files.newBufferedWriter(new File("day17_out.txt").toPath())) {
            for (String[] y : map) {
                for (String x : y) {
                    writer.write(x);
                }
                writer.write("\n");
            }
        }

        int count = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (y >= yMin && (map[y][x].equals("|") || map[y][x].equals("~"))) {
                    count++;
                }
            }
        }

        System.out.println("Water touched " + count + " squares");
    }

    /**
     * true si fond touché, false si bloqué
     */
    private boolean flow(String[][] map, int y, int x, Direction dir) {

        if (y >= map.length || map[y][x].equals("|")) {
            // fond atteint
            return true;
        }

        if (map[y][x].equals("#") || map[y][x].equals("~")) {
            return false;
        }

        map[y][x] = "|";

        if (!flow(map, y + 1, x, Direction.DOWN)) {

            boolean flowRight = false;
            boolean flowLeft = false;

            if (dir == Direction.DOWN || dir == Direction.RIGHT) {
                flowRight = flow(map, y, x + 1, Direction.RIGHT);
            }
            if (dir == Direction.DOWN || dir == Direction.LEFT) {
                flowLeft = flow(map, y, x - 1, Direction.LEFT);
            }

            if (flowLeft || flowRight) {
                return true;
            } else {
                if (dir == Direction.DOWN) {
                    wave(map, y, x, Direction.RIGHT);
                    wave(map, y, x, Direction.LEFT);
                }
                return false;
            }
        } else {
            return true;
        }
    }

    // remplie une ligne bloquée de vagues
    private void wave(String[][] map, int y, int x, Direction dir) {
        if (map[y][x].equals("#")) {
            return;
        }
        map[y][x] = "~";
        wave(map, y, dir == Direction.LEFT ? x - 1 : x + 1, dir);
    }


    @Override
    public void part2() throws Exception {
        System.out.println("29293");
    }
}
