package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.util.ArrayList;
import java.util.List;

public class Day1 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(1);

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

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(1);

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
