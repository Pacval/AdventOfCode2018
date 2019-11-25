package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 implements DayInterface {

    private Set<Position> filledSquares;

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    @AllArgsConstructor
    @Data
    private class Position {
        int x;
        int y;
    }

    @AllArgsConstructor
    private class Water {
        int x;
        int y;

        int flow() {

            // si on passse en dessous de la dernière ligne comptée, on retourne direct
            if (y > yMax) {
                return 0;
            }

            int score = 0;

            // si case en dessous vide, on ajoute de l'eau
            if (!filledSquares.contains(new Position(x, y + 1))) {
                Water childDown = new Water(x, y + 1);
                score += childDown.flow();
            }

            // aller sur les cotés. Mais condition de sortie ?
            if (!filledSquares.contains(new Position(x - 1, y))) {
                Water childLeft = new Water(x - 1, y);
                score += childLeft.flow();
            }

            if (!filledSquares.contains(new Position(x + 1, y))) {
                Water childRight = new Water(x + 1, y);
                score += childRight.flow();
            }

            // si case dans zone de score, on comptabilise
            if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
                score++;
            }
            return score;
        }
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(17);

        Pattern pattern = Pattern.compile("(.)=(\\d*), .=(\\d*)..(\\d*)");

        // on va faire qu'une seule liste qui servira à stocker les positions impossible à remplir (clay + déjà remplies d'eau)
        filledSquares = new HashSet<>(); // format x.y

        xMin = Integer.MAX_VALUE;
        xMax = Integer.MIN_VALUE;
        yMin = Integer.MAX_VALUE;
        yMax = Integer.MIN_VALUE;

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
                    filledSquares.add(new Position(x, y));
                }
            } else if (matcher.group(1).equals("y")) {
                int y = Integer.parseInt(matcher.group(2));
                for (int x = Integer.parseInt(matcher.group(3)); x <= Integer.parseInt(matcher.group(4)); x++) {
                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                    filledSquares.add(new Position(x, y));
                }
            } else {
                System.out.println("Error : " + line);
            }
        }

        /*
        try (BufferedWriter writer = Files.newBufferedWriter(new File("day17_out.txt").toPath())) {
            for (int y = 0; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    writer.write(clays.contains(new Position(x, y)) ? "#" : ".");
                }
                writer.write("\n");
            }
        }*/

        Water start = new Water(500, 0);
        int nbSquareFlowded = start.flow();

        System.out.println();

    }

    @Override
    public void part2() throws Exception {

    }
}
