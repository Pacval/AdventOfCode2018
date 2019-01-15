package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(2);

        Integer code2occurence = 0;
        Integer code3occurence = 0;

        for (String code : entries) {
            Map<String, Long> frequentChars = Arrays.stream(
                    code.toLowerCase().split("")).collect(
                    Collectors.groupingBy(c -> c, Collectors.counting()));

            if (frequentChars.containsValue(2L)) {
                code2occurence++;
            }
            if (frequentChars.containsValue(3L)) {
                code3occurence++;
            }
        }

        System.out.println(code2occurence * code3occurence);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(2);

        boolean found = false;
        for (String code : entries) {
            for (String codeCompared : entries) {
                char[] codeChars = code.toCharArray();
                char[] codeComparedChars = codeCompared.toCharArray();
                int differences = 0;
                for (int i = 0; i < Math.min(codeChars.length, codeComparedChars.length); i++) {
                    if (codeChars[i] != codeComparedChars[i]) {
                        differences++;
                    }
                }
                if (differences == 1 && !found) {
                    found = true;
                    for (int i = 0; i < Math.min(codeChars.length, codeComparedChars.length); i++) {
                        if (codeChars[i] == codeComparedChars[i]) {
                            System.out.print(codeChars[i]);
                        }
                    }
                }
            }
        }
    }
}
