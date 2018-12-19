package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(9, 1);

        Integer nbPlayer = Integer.valueOf(entries[0].split(" ")[0]);
        Integer lastMarble = Integer.valueOf(entries[0].split(" ")[6]);

        System.out.println("Number of players : " + nbPlayer);
        System.out.println("Last marble value : " + lastMarble);

        int playerTurn = 1;

        int[] marbleGame = new int[]{0, 1};
        int currentMarblePosition = 1;

        // On stockes les scores dans un tableau et on met tout le monde à 0
        int[] scores = new int[nbPlayer];
        Arrays.fill(scores, 0);

        for (int marble = 2; marble <= lastMarble; marble++) {

            if(marble % 23 == 0) {
                // récupération de la 7eme bille avant et ajout des points
                currentMarblePosition = currentMarblePosition < 7 ? currentMarblePosition + marbleGame.length - 7 : currentMarblePosition - 7;

                // ajout points 7eme bille et bille à ajouter
                scores[playerTurn - 1] = scores[playerTurn - 1] + marble + marbleGame[currentMarblePosition];

                // suppression de la bille
                marbleGame = removeMarble(marbleGame, currentMarblePosition);

            } else {
                // recherche position nouvelle bille
                int nextPosition = (currentMarblePosition + 2) % marbleGame.length;

                // ajout bille
                marbleGame = addMarble(marbleGame, nextPosition, marble);
                currentMarblePosition = nextPosition;
            }

            playerTurn = playerTurn == nbPlayer ? 1 : playerTurn + 1;
        }

        int highestScore = Arrays.stream(scores).max().getAsInt();
        System.out.println("Highest score : " + highestScore);
    }

    private static int[] addMarble(int[] marbleGame, int positionNewMarble, int valueMarble) {
        int[] result = new int[marbleGame.length + 1];
        System.arraycopy(marbleGame, 0, result, 0, positionNewMarble);
        System.arraycopy(marbleGame, positionNewMarble, result, positionNewMarble + 1, result.length - positionNewMarble - 1);
        result[positionNewMarble] = valueMarble;
        return result;
    }

    private static int[] removeMarble(int[] marbleGame, int positionMarble) {
        int[] result = new int[marbleGame.length - 1];
        System.arraycopy(marbleGame, 0, result, 0, positionMarble);

        // On fait ça pour éviter de faire planter quand la bille à supprimer est la dernière bille du tableau
        if(positionMarble != marbleGame.length - 1) {
            System.arraycopy(marbleGame, positionMarble + 1, result, positionMarble, result.length - positionMarble - 1);
        }
        return result;
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(9, 2);

        Integer nbPlayer = Integer.valueOf(entries[0].split(" ")[0]);
        Integer lastMarble = Integer.valueOf(entries[0].split(" ")[6]);

        System.out.println("Number of players : " + nbPlayer);
        System.out.println("Last marble value : " + lastMarble);

        // Ne marche pas : pas assez optimisé
        /*
        int playerTurn = 1;

        int[] marbleGame = new int[]{0, 1};
        int currentMarblePosition = 1;

        // On stockes les scores dans un tableau et on met tout le monde à 0
        long[] scores = new long[nbPlayer];
        Arrays.fill(scores, 0);

        for (int marble = 2; marble <= lastMarble; marble++) {

            if(marble % 23 == 0) {
                // récupération de la 7eme bille avant et ajout des points
                currentMarblePosition = currentMarblePosition < 7 ? currentMarblePosition + marbleGame.length - 7 : currentMarblePosition - 7;

                // ajout points 7eme bille et bille à ajouter
                scores[playerTurn - 1] = scores[playerTurn - 1] + marble + marbleGame[currentMarblePosition];

                // suppression de la bille
                marbleGame = removeMarble(marbleGame, currentMarblePosition);

            } else {
                // recherche position nouvelle bille
                int nextPosition = (currentMarblePosition + 2) % marbleGame.length;

                // ajout bille
                marbleGame = addMarble(marbleGame, nextPosition, marble);
                currentMarblePosition = nextPosition;
            }

            playerTurn = playerTurn == nbPlayer ? 1 : playerTurn + 1;

            if (marble % 10000 == 0) {
                System.out.println(marble);
            }
        }

        long highestScore = Arrays.stream(scores).max().getAsLong();
        System.out.println("Highest score : " + highestScore);
        */
    }
}
