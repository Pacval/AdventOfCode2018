package aoc.days;

import aoc.ExoEntryUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(1, 1);

        int res = 0;

        for (String change : entries) {
            if (change.substring(0, 1).equals("+")) {
                res += Integer.parseInt(change.substring(1));
            } else {
                res -= Integer.parseInt(change.substring(1));
            }
        }
        System.out.println(res);
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(1, 1);

        int res = 0;
        List<Integer> seenValues = new ArrayList<>();
        int count = 0;

        while (!seenValues.contains(res)) {
            seenValues.add(res);
            if (entries[count].substring(0, 1).equals("+")) {
                res += Integer.parseInt(entries[count].substring(1));
            } else {
                res -= Integer.parseInt(entries[count].substring(1));
            }
            count++;
            if (count >= entries.length) {
                count = 0;
            }
        }
        System.out.println(res);
    }
}
