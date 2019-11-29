package aoc;

import aoc.days.Day18;

public class Main {

    public static void main(String[] args) {

        try {
            DayInterface day = new Day18();
            day.part1();
            day.part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
