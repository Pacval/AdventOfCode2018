package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day12 implements DayInterface {

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Spread {
        private String before;
        private String plantNext;
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(12);
        long nbGenerations = 20L;

        String state = entries[0].split(" ")[2];

        List<Spread> spreads = new ArrayList<>();
        for (int i = 2; i < entries.length; i++) {
            Spread spread = new Spread();
            spread.setBefore(entries[i].split(" ")[0]);
            spread.setPlantNext(entries[i].split(" ")[2]);
            spreads.add(spread);
        }

        System.out.println("Total points of pots on " + nbGenerations + " generations : " + generate(nbGenerations, state, spreads));
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(12);
        long nbGenerations = 50000000000L;

        String state = entries[0].split(" ")[2];

        List<Spread> spreads = new ArrayList<>();
        for (int i = 2; i < entries.length; i++) {
            Spread spread = new Spread();
            spread.setBefore(entries[i].split(" ")[0]);
            spread.setPlantNext(entries[i].split(" ")[2]);
            spreads.add(spread);
        }

        System.out.println("Total points of pots on " + nbGenerations + " generations : " + generate(nbGenerations, state, spreads));
    }

    private long generate(long nbGenerations, String state, List<Spread> spreads) {
        long farestLeft = 0, previousFarestLeft = 0;
        long age = 0;

        while (age < nbGenerations){

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

            if (newState.toString().equals(state)) {
                // On a trouvé une occurrence. On calcul l'offset entre les 2 ages, on le multiplie par le nombre d'années restantes à calculer et bingo
                long offSet = farestLeft - previousFarestLeft;
                farestLeft = farestLeft + offSet * (nbGenerations - age - 1);

                // on va directement à la fin
                age = nbGenerations;
            } else {
                state = newState.toString();
                previousFarestLeft = farestLeft;
                age++;
            }
        }

        System.out.println("\n\n\nFinal state after " + nbGenerations + " years : " + state);

        // Calcul du total des numéros de pot
        long potValue = farestLeft;
        long totalPots = 0;
        for (char c : state.toCharArray()) {
            if (c == '#') {
                totalPots += potValue;
            }
            potValue++;
        }

        return totalPots;
    }
}
