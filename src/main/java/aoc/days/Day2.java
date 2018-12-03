package aoc.days;

import aoc.ExoEntryUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(2, 1);

        Integer code2occurence = 0;
        Integer code3occurence = 0;

        for(String code : entries) {
            Map<String, Long> frequentChars = Arrays.stream(
                    code.toLowerCase().split("")).collect(
                    Collectors.groupingBy(c -> c, Collectors.counting()));

            if(frequentChars.containsValue(2l)) {
                code2occurence++;
            }
            if(frequentChars.containsValue(3l)) {
                code3occurence++;
            }
        }

        System.out.println(code2occurence * code3occurence);
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
