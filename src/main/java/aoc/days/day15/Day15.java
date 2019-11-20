package aoc.days.day15;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day15 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(15);

        List<Unit> units = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        for (int i = 0; i < entries.length; i++) {
            for (int j = 0; j < entries[i].length(); j++) {
                switch (entries[i].charAt(j)) {
                    case '#':
                        walls.add(new Wall(i, j));
                        break;
                    case 'G':
                        units.add(new Unit(i, j, UnitType.GOBLIN));
                        break;
                    case 'E':
                        units.add(new Unit(i, j, UnitType.ELF));
                }
            }
        }

        Comparator<Unit> unitComparator = (o1, o2) -> {
            if (o1.getTurns() < o2.getTurns()) {
                return -1;
            } else if (o1.getTurns() > o2.getTurns()) {
                return 1;
            } else {
                if (o1.getPosition().getX() < o2.getPosition().getX()) {
                    return -1;
                } else if (o1.getPosition().getX() > o2.getPosition().getX()) {
                    return 1;
                } else {
                    return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
                }
            }
        };

        mainloop:
        while (true) {

            // on ordonne les unités
            units.sort(unitComparator);

            Unit unitToMove = units.get(0);

            // condition de sortie
            if (units.stream().map(Unit::getType).distinct().count() <= 1) {
                break mainloop;
            }
        }


    }

    @Override
    public void part2() throws Exception {

    }
}
