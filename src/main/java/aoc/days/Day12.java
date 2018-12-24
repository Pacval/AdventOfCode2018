package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day12 {

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Spread {
        private String before;
        private String plantNext;
    }

    public static void exo1() throws IOException {
        System.out.println("Day 12 exo 1");
        process(20);
    }

    public static void exo2() throws IOException {
        // brut force...
        System.out.println("Day 12 exo 2");
        // process(50000000000L);
        // marche pas :(
    }

    private static void process(long nbGenerations) throws IOException {
        String[] entries = ExoEntryUtils.getEntries(12, 1);

        String state = entries[0].split(" ")[2];
        int farestLeft = 0;

        List<Spread> spreads = new ArrayList<>();
        for (int i = 2; i < entries.length; i++) {
            Spread spread = new Spread();
            spread.setBefore(entries[i].split(" ")[0]);
            spread.setPlantNext(entries[i].split(" ")[2]);
            spreads.add(spread);
        }

        for (int age = 0; age < nbGenerations; age++) {

            // On étend le tableau pour qu'il y ai au minimum 4 plants vides à droite et à gauche (pour bien tester toutes les combinaisons)
            StringBuilder extendedStateBuilder = new StringBuilder();
            for (int i = 0; i < 4 - state.indexOf('#'); i++) {
                extendedStateBuilder.append(".");
                farestLeft--;
            }
            farestLeft += 2;
            extendedStateBuilder.append(state);
            for (int i = 0; i < 4 - state.length() + state.lastIndexOf('#') + 1; i++) {
                extendedStateBuilder.append(".");
            }

            StringBuilder newState = new StringBuilder();

            for (int i = 3; i < extendedStateBuilder.length() - 1; i++) {
                String plantZone = extendedStateBuilder.substring(i - 3, i + 2);

                Optional<Spread> optionalSpread = spreads.stream().filter(s -> s.getBefore().equals(plantZone)).findFirst();
                if (optionalSpread.isPresent()) {
                    newState.append(optionalSpread.get().getPlantNext());
                } else {
                    newState.append(".");
                }
            }
            state = newState.toString();

            if(age % 100000 == 0) {
                System.out.println((double) age / 50000000000.0 * 100 + "%");
            }
        }

        System.out.println("Final state after " + nbGenerations + " years : " + state);

        // Calcul du total des numéros de pot
        int potValue = farestLeft;
        int totalPots = 0;
        for (char c : state.toCharArray()) {
            if (c == '#') {
                totalPots += potValue;
            }
            potValue++;
        }

        System.out.println("Total points of pots : " + totalPots);
    }

}
