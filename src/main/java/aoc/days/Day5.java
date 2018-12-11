package aoc.days;

import aoc.ExoEntryUtils;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class Day5 {


    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(5, 1);
        String polymer = entries[0];

        boolean oppostionFound = true;

        int i;
        while (oppostionFound) {
            oppostionFound = false;
            i = 1;
            while (!oppostionFound && i < polymer.length()) {
                // Si les caractères sont différents mais équivalents en majuscule
                if (polymer.charAt(i) != polymer.charAt(i - 1) && Character.toUpperCase(polymer.charAt(i)) == Character.toUpperCase(polymer.charAt(i - 1))) {
                    oppostionFound = true;
                    polymer = polymer.substring(0, i - 1).concat(polymer.substring(i + 1));
                } else {
                    i++;
                }
            }
        }

        System.out.println("final polymer : " + polymer);
        System.out.println("Size : " + polymer.length());
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(5, 1);
        String polymer = entries[0];

        Map<Character, Integer> advancedPolymers = new HashMap<>();
        boolean oppostionFound;
        int i;

        // On itère sur toutes les lettres de l'alphabet
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {

            // On enlève de la string toutes les occurences du caractère testé
            String polymerWithoutLetter = polymer.replace(String.valueOf(alphabet), "").replace(String.valueOf(Character.toUpperCase(alphabet)), "");

            oppostionFound = true;
            // On process l'optimisation de la chaine
            while (oppostionFound) {
                oppostionFound = false;
                i = 1;
                while (!oppostionFound && i < polymerWithoutLetter.length()) {
                    // Si les caractères sont différents mais équivalents en majuscule
                    if (polymerWithoutLetter.charAt(i) != polymerWithoutLetter.charAt(i - 1) &&
                            Character.toUpperCase(polymerWithoutLetter.charAt(i)) == Character.toUpperCase(polymerWithoutLetter.charAt(i - 1))) {
                        oppostionFound = true;
                        polymerWithoutLetter = polymerWithoutLetter.substring(0, i - 1).concat(polymerWithoutLetter.substring(i + 1));
                    } else {
                        i++;
                    }
                }
            }

            // On enregistre la taille de la chaine
            advancedPolymers.put(alphabet, polymerWithoutLetter.length());
            System.out.println(alphabet + " done");
        }

        Character characterCausingProblems = advancedPolymers.entrySet().stream().min((e1, e2) -> (e1.getValue().compareTo(e2.getValue()))).get().getKey();

        System.out.println("Character causing the most problems is : " + characterCausingProblems);
        System.out.println("Size of polymer without this character :" + advancedPolymers.get(characterCausingProblems));
    }
}
