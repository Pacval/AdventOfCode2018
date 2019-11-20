package aoc.days;

import aoc.DayInterface;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day14 implements DayInterface {

    @Override
    public void part1() throws Exception {
        int entry = 598701;

        List<Integer> scores = new ArrayList<>();
        scores.add(3);
        scores.add(7);

        int positionElf1 = 0;
        int positionElf2 = 1;

        while (scores.size() < entry + 10) {
            int newScore = scores.get(positionElf1) + scores.get(positionElf2);

            if (newScore >= 10) {
                scores.add(1);
            }
            scores.add(newScore % 10);

            positionElf1 = (positionElf1 + 1 + scores.get(positionElf1)) % scores.size();
            positionElf2 = (positionElf2 + 1 + scores.get(positionElf2)) % scores.size();
        }

        StringBuilder lastScores = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            lastScores.append(scores.get(entry + i));
        }

        System.out.println("Last 10 scores after " + entry + " are : " + lastScores.toString());
    }

    @Override
    public void part2() throws Exception {
        int entry = 598701;
        String entryStr = String.valueOf(entry);

        List<Integer> scores = new ArrayList<>();
        scores.add(3);
        scores.add(7);
        StringBuilder scoresStr = new StringBuilder("37");

        int positionElf1 = 0;
        int positionElf2 = 1;

        loop:
        while (true) {
            int newScore = scores.get(positionElf1) + scores.get(positionElf2);

            for (String str : String.valueOf(newScore).split("")) {
                scores.add(Integer.valueOf(str));
                scoresStr.append(str);

                if (scoresStr.length() > entryStr.length()) {
                    scoresStr.deleteCharAt(0);
                }

                if (scoresStr.toString().equals(entryStr)) {
                    break loop;
                }
            }

            positionElf1 = (positionElf1 + 1 + scores.get(positionElf1)) % scores.size();
            positionElf2 = (positionElf2 + 1 + scores.get(positionElf2)) % scores.size();
        }


        System.out.println("Number of recipes before entry sequence : " + (scores.size() - entryStr.length()));
    }
}
