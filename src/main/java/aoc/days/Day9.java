package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;

public class Day9 {

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(9, 1);

        int nbPlayer = Integer.parseInt(entries[0].split(" ")[0]);
        int lastMarble = Integer.valueOf(entries[0].split(" ")[6]);

        System.out.println("Number of players : " + nbPlayer);
        System.out.println("Last marble value : " + lastMarble);
        System.out.println("Highest score : " + play(nbPlayer, lastMarble));
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(9, 2);

        int nbPlayer = Integer.valueOf(entries[0].split(" ")[0]);
        int lastMarble = Integer.valueOf(entries[0].split(" ")[6]);

        System.out.println("Number of players : " + nbPlayer);
        System.out.println("Last marble value : " + lastMarble);
        System.out.println("Highest score : " + play(nbPlayer, lastMarble));
    }

    private static long play(int nbPlayer, int lastMarble) {

        LinkedList<Integer> marbleGame = new LinkedList<>();
        marbleGame.add(0);

        // On stockes les scores dans un tableau et on met tout le monde à 0
        long[] scores = new long[nbPlayer];
        Arrays.fill(scores, 0);

        int playerTurn = 1;
        ListIterator<Integer> listIterator = marbleGame.listIterator();

        for (int marble = 1; marble <= lastMarble; marble++) {

            if(marble % 23 == 0) {

                int valueRemoved = 0;
                for (int i = 0; i < 8; i++) {
                    if (listIterator.hasPrevious()) {
                        valueRemoved = listIterator.previous();
                    } else {
                        listIterator = marbleGame.listIterator(marbleGame.size());
                        valueRemoved = listIterator.previous();
                    }
                }
                listIterator.remove();
                listIterator.next();

                // ajout points 7eme bille et bille à ajouter
                scores[playerTurn - 1] = scores[playerTurn - 1] + marble + valueRemoved;

            } else {

                if (listIterator.hasNext()) {
                    listIterator.next();
                } else {
                    listIterator = marbleGame.listIterator();
                    listIterator.next();
                }
                listIterator.add(marble);
            }

            playerTurn = playerTurn == nbPlayer ? 1 : playerTurn + 1;
        }

        return Arrays.stream(scores).max().getAsLong();
    }
}
