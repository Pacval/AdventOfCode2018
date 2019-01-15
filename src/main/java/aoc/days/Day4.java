package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 implements DayInterface {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Line {
        private Date date;
        private String sentence;

        @Override
        public String toString() {
            return date.toString() + " " + sentence + "\n";
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Shift {
        private int idGuard;
        private List<Integer> minAsleep;

        Shift(int idGuard) {
            this.idGuard = idGuard;
            this.minAsleep = new ArrayList<>();
        }

        void addAsleep(int min) {
            minAsleep.add(min);
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class MaxSleptShiftByGuard {
        private int idGuard;
        private int minute;
        private Long nbNights;
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(4);

        List<Line> lines = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (String entry : entries) {
            lines.add(new Line(
                    sdf.parse(entry.substring(1, 17)),
                    entry.substring(entry.indexOf("] ") + 2)
            ));
        }

        List<Shift> shifts = new ArrayList<>();
        Shift shift = new Shift();
        int minStartSleeping = 0;
        Calendar cal = Calendar.getInstance();
        for (Line line : lines.stream().sorted(Comparator.comparing(Line::getDate)).collect(Collectors.toList())) {
            switch (line.getSentence().split(" ")[0]) {
                case "Guard":
                    // nouveau garde
                    shift = new Shift(Integer.parseInt(line.getSentence().split(" ")[1].substring(1)));
                    shifts.add(shift);
                    minStartSleeping = 0; // 0 = nouveau garde
                    break;
                case "wakes":
                    // On se réveille
                    cal.setTime(line.getDate());
                    // On ajoute toutes les minutes endormies
                    for (int i = minStartSleeping; i < cal.get(Calendar.MINUTE); i++) {
                        shift.addAsleep(i);
                    }
                    minStartSleeping = -1; // -1 = garde réveillé
                    break;
                case "falls":
                    // On s'endort
                    cal.setTime(line.getDate());
                    minStartSleeping = cal.get(Calendar.MINUTE);
                    break;
            }
        }

        // on groupe par ID, puis on additione les temps de dodo
        // On récupère donc dans une Map l'id du garde et son temps total de dodo
        Map.Entry maxSleepingGuard = shifts.stream()
                .collect(Collectors.groupingBy(foo -> foo.idGuard,
                        Collectors.summingInt(foo -> foo.minAsleep.size())))
                .entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();

        Integer idGuardMostSleeping = Integer.parseInt(maxSleepingGuard.getKey().toString());

        // On cherche la minute la plus endormie
        // On filtre sur l'id du garde
        List<Shift> sleepingGuardShifts = shifts.stream().filter(item -> item.getIdGuard() == idGuardMostSleeping).collect(Collectors.toList());

        // on fait une map avec la minute et le nombre de dodo sur cette minute
        Map<Integer, Long> minSlept = sleepingGuardShifts.stream()
                .flatMap(item -> item.getMinAsleep().stream())
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        Integer minMoreSlept = Collections.max(minSlept.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("id guard most sleeping : " + idGuardMostSleeping);
        System.out.println("min most slept : " + minMoreSlept);
        System.out.println(idGuardMostSleeping * minMoreSlept);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(4);

        List<Line> lines = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (String entry : entries) {
            lines.add(new Line(
                    sdf.parse(entry.substring(1, 17)),
                    entry.substring(entry.indexOf("] ") + 2)
            ));
        }

        List<Shift> shifts = new ArrayList<>();
        Shift shift = new Shift();
        int minStartSleeping = 0;
        Calendar cal = Calendar.getInstance();
        for (Line line : lines.stream().sorted(Comparator.comparing(Line::getDate)).collect(Collectors.toList())) {
            if (line.getSentence().split(" ")[0].equals("Guard")) {
                // nouveau garde
                shift = new Shift(Integer.parseInt(line.getSentence().split(" ")[1].substring(1)));
                shifts.add(shift);
                minStartSleeping = 0; // 0 = nouveau garde

            } else if (line.getSentence().split(" ")[0].equals("wakes")) {
                // On se réveille
                cal.setTime(line.getDate());
                // On ajoute toutes les minutes endormies
                for (int i = minStartSleeping; i < cal.get(Calendar.MINUTE); i++) {
                    shift.addAsleep(i);
                }
                minStartSleeping = -1; // -1 = garde réveillé

            } else if (line.getSentence().split(" ")[0].equals("falls")) {
                // On s'endort
                cal.setTime(line.getDate());
                minStartSleeping = cal.get(Calendar.MINUTE);
            }
        }

        // filtrer par id garde
        Map<Integer, List<Shift>> groupedShift = shifts.stream().collect(Collectors.groupingBy(Shift::getIdGuard));

        List<MaxSleptShiftByGuard> maxSleptShiftByGuards = new ArrayList<>();

        for (Map.Entry<Integer, List<Shift>> shiftPersos : groupedShift.entrySet()) {
            Map<Integer, Long> toto = shiftPersos.getValue().stream()
                    .flatMap(item -> item.getMinAsleep().stream())
                    .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

            if (!toto.isEmpty()) {
                Map.Entry<Integer, Long> minMoreSlept = Collections.max(toto.entrySet(), Map.Entry.comparingByValue());
                MaxSleptShiftByGuard maxSleptShiftByGuard = new MaxSleptShiftByGuard(
                        shiftPersos.getKey(),
                        minMoreSlept.getKey(),
                        minMoreSlept.getValue()
                );
                maxSleptShiftByGuards.add(maxSleptShiftByGuard);
            }
        }

        MaxSleptShiftByGuard result = Collections.max(maxSleptShiftByGuards, Comparator.comparing(MaxSleptShiftByGuard::getNbNights));

        System.out.println("id " + result.getIdGuard());
        System.out.println("minute " + result.getMinute());
        System.out.println("nb nights " + result.getNbNights());

        System.out.println("result = " + result.getIdGuard() * result.getMinute());
    }
}
