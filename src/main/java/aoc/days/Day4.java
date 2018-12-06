package aoc.days;

import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

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

        public Shift(int idGuard) {
            this.idGuard = idGuard;
            this.minAsleep = new ArrayList<>();
        }

        public void addAsleep(int min) {
            minAsleep.add(min);
        }
    }

    public static void exo1() throws IOException, ParseException {

        String[] entries = ExoEntryUtils.getEntries(4, 1);

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

        // on groupe par ID, puis on additione les temps de dodo
        // On récupère donc dans une Map l'id du garde et son temps total de dodo
        Map.Entry maxSleepingGuard = shifts.stream()
                .collect(Collectors.groupingBy(foo -> foo.idGuard,
                        Collectors.summingInt(foo -> foo.minAsleep.size())))
                .entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get();

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

    public static void exo2() throws IOException {

    }
}
