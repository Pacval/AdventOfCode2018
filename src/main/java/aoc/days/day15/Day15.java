package aoc.days.day15;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(15);

        List<Unit> units = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        // lecture entrée
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

        // comparateur unité pour savoir qui bouge ensuite
        Comparator<Unit> unitReadingOrderComparator = (o1, o2) -> {
            if (o1.getPosition().getX() < o2.getPosition().getX()) {
                return -1;
            } else if (o1.getPosition().getX() > o2.getPosition().getX()) {
                return 1;
            } else {
                return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
            }
        };

        int rounds = 0;

        loop:
        while (true) {
            // on ordonne les unités
            units.sort(unitReadingOrderComparator);

            for (Unit unit : units) {

                if (units.stream().filter(item -> item.getHealth() > 0).map(Unit::getType).distinct().count() <= 1) {
                    break loop;
                }

                if (unit.getHealth() > 0) {
                    unit.doTurn(units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList()), walls);
                }
            }

            // tri des unités vivantes
            units = units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList());

            rounds++;
        }

        // tri des unités vivantes
        units = units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList());

        int totalLifeRemaining = units.stream().mapToInt(Unit::getHealth).sum();

        System.out.println("rounds : " + rounds);
        System.out.println("total life : " + totalLifeRemaining);
        System.out.println("Outcome of the fight is : " + (rounds * totalLifeRemaining));
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(15);

        List<Unit> originalUnits = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        // lecture entrée
        for (int i = 0; i < entries.length; i++) {
            for (int j = 0; j < entries[i].length(); j++) {
                switch (entries[i].charAt(j)) {
                    case '#':
                        walls.add(new Wall(i, j));
                        break;
                    case 'G':
                        originalUnits.add(new Unit(i, j, UnitType.GOBLIN));
                        break;
                    case 'E':
                        originalUnits.add(new Unit(i, j, UnitType.ELF));
                }
            }
        }

        // comparateur unité pour savoir qui bouge ensuite
        Comparator<Unit> unitReadingOrderComparator = (o1, o2) -> {
            if (o1.getPosition().getX() < o2.getPosition().getX()) {
                return -1;
            } else if (o1.getPosition().getX() > o2.getPosition().getX()) {
                return 1;
            } else {
                return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
            }
        };

        int elfPower = 3;
        long nbElf = originalUnits.stream().filter(item -> item.getType() == UnitType.ELF).count();

        bigLoop:
        while (true) {

            List<Unit> units = new ArrayList<>(originalUnits.size());
            for (Unit unit : originalUnits) {
                units.add((Unit) unit.clone());
            }

            elfPower++;
            int finalElfPower = elfPower;
            units.stream().filter(item -> item.getType() == UnitType.ELF).forEach(item -> item.setDamages(finalElfPower));

            int rounds = 0;

            loop:
            while (true) {
                // on ordonne les unités
                units.sort(unitReadingOrderComparator);

                for (Unit unit : units) {

                    if (units.stream().filter(item -> item.getHealth() > 0).map(Unit::getType).distinct().count() <= 1) {
                        break loop;
                    }

                    if (unit.getHealth() > 0) {
                        unit.doTurn(units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList()), walls);
                    }
                }

                // tri des unités vivantes
                units = units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList());

                // si un elf est mort on arrete cette simulation
                if (nbElf > units.stream().filter(item -> item.getType() == UnitType.ELF).count()) {
                    break loop;
                }

                rounds++;
            }

            // tri des unités vivantes
            units = units.stream().filter(item -> item.getHealth() > 0).collect(Collectors.toList());

            // on sort de la boucle et aucun elf n'est mort
            if (nbElf == units.stream().filter(item -> item.getType() == UnitType.ELF).count()) {
                int totalLifeRemaining = units.stream().mapToInt(Unit::getHealth).sum();

                System.out.println("rounds : " + rounds);
                System.out.println("total life : " + totalLifeRemaining);
                System.out.println("minimum power required : " + elfPower);
                System.out.println("Outcome of the fight is : " + (rounds * totalLifeRemaining));

                break bigLoop;
            }
        }
    }
}
