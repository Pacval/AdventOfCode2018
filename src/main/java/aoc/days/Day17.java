package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 implements DayInterface {

    @AllArgsConstructor
    @Data
    private static class Water {
        String position; // format : "x.y"
        boolean isBlocked;
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(17);

        Pattern pattern = Pattern.compile("(.)=(\\d*), .=(\\d*)..(\\d*)");

        Set<String> clays = new HashSet<>(); // format x.y

        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        // on va lister tous les carrés de clay (entries)
        for (String line : entries) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                break;
            }
            if (matcher.group(1).equals("x")) {
                int x = Integer.parseInt(matcher.group(2));
                for (int y = Integer.parseInt(matcher.group(3)); y <= Integer.parseInt(matcher.group(4)); y++) {
                    String pos = x + "." + y;
                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                    clays.add(pos);
                }
            } else if (matcher.group(1).equals("y")) {
                int y = Integer.parseInt(matcher.group(2));
                for (int x = Integer.parseInt(matcher.group(3)); x <= Integer.parseInt(matcher.group(4)); x++) {
                    String pos = x + "." + y;
                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                    clays.add(pos);
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

        List<Water> water = new ArrayList<>();
        water.add(new Water(500 + "." + 0, false));

        while (!water.stream().allMatch(Water::isBlocked)) {

        }

    }

    @Override
    public void part2() throws Exception {

    }
}
