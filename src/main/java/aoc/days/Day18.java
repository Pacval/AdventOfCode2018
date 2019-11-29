package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day18 implements DayInterface {

    private enum Type {
        OPEN, TREES, LUMBERYARD;
    }

    @AllArgsConstructor
    @Data
    private static class Acre {
        int x;
        int y;
        Type type;

        private Acre getNextAcre(List<Acre> area) {
            List<Acre> adjacentAcres = getAdjacentAcre(area);
            Acre nextAcre = new Acre(x, y, type);

            switch (type) {
                case OPEN:
                    if (adjacentAcres.stream().filter(item -> item.getType() == Type.TREES).count() >= 3L) {
                        nextAcre.type = Type.TREES;
                    }
                    break;
                case TREES:
                    if (adjacentAcres.stream().filter(item -> item.getType() == Type.LUMBERYARD).count() >= 3L) {
                        nextAcre.type = Type.LUMBERYARD;
                    }
                    break;
                case LUMBERYARD:
                    if (!(adjacentAcres.stream().filter(item -> item.getType() == Type.LUMBERYARD).count() >= 1L && adjacentAcres.stream().filter(item -> item.getType() == Type.TREES).count() >= 1L)) {
                        nextAcre.type = Type.OPEN;
                    }
                    break;
            }
            return nextAcre;
        }

        private List<Acre> getAdjacentAcre(List<Acre> area) {
            return area.stream().filter(item ->
                    item.getX() >= x - 1 && item.getX() <= x + 1 &&
                            item.getY() >= y - 1 && item.getY() <= y + 1 && item != this)
                    .collect(Collectors.toList());
        }

    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(18);

        List<Acre> area = new ArrayList<>();
        for (int i = 0; i < entries.length; i++) {
            for (int j = 0; j < entries[i].length(); j++) {
                switch (entries[i].charAt(j)) {
                    case '.':
                        area.add(new Acre(i, j, Type.OPEN));
                        break;
                    case '|':
                        area.add(new Acre(i, j, Type.TREES));
                        break;
                    case '#':
                        area.add(new Acre(i, j, Type.LUMBERYARD));
                        break;
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            List<Acre> nextArea = new ArrayList<>();
            for (Acre acre : area) {
                nextArea.add(acre.getNextAcre(area));
            }
            area = nextArea;
        }

        long result = area.stream().filter(item -> item.getType() == Type.TREES).count() * area.stream().filter(item -> item.getType() == Type.LUMBERYARD).count();

        System.out.println("Value of the lumber collection is " + result);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(18);

        List<Acre> area = new ArrayList<>();
        for (int i = 0; i < entries.length; i++) {
            for (int j = 0; j < entries[i].length(); j++) {
                switch (entries[i].charAt(j)) {
                    case '.':
                        area.add(new Acre(i, j, Type.OPEN));
                        break;
                    case '|':
                        area.add(new Acre(i, j, Type.TREES));
                        break;
                    case '#':
                        area.add(new Acre(i, j, Type.LUMBERYARD));
                        break;
                }
            }
        }

        Map<List<Acre>, Integer> history = new HashMap<>();
        history.put(area, 0);

        long result = 0;

        for (int i = 1; i <= 1000000000; i++) {
            List<Acre> nextArea = new ArrayList<>();
            for (Acre acre : area) {
                nextArea.add(acre.getNextAcre(area));
            }

            area = nextArea;

            if (history.containsKey(area)) {
                int repetition = i - history.get(area);
                int j = i;
                while (j < 1000000000) {
                    j += repetition;
                }
                int offset = repetition - (j - 1000000000);

                int iResult = history.get(area) + offset;

                result = history.entrySet().stream().filter(item -> item.getValue() == iResult).findFirst().get().getKey()
                        .stream().filter(item -> item.getType() == Type.TREES).count() * area.stream().filter(item -> item.getType() == Type.LUMBERYARD).count();

                break;

            } else {
                history.put(area, i);
            }

            // print(area);
            // Thread.sleep(500);
        }

        System.out.println("Value of the lumber collection after 1000000000 is " + result);
    }

    private void print(List<Acre> map) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                int finalI = i;
                int finalJ = j;
                switch (map.stream().filter(item -> item.getX() == finalI && item.getY() == finalJ).findFirst().get().getType()) {
                    case TREES:
                        System.out.print("|");
                        break;
                    case OPEN:
                        System.out.print(".");
                        break;
                    case LUMBERYARD:
                        System.out.print("#");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
