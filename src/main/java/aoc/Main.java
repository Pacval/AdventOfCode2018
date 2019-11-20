package aoc;

import aoc.days.day15.Day15;

public class Main {

    public static void main(String[] args) {

        try {
            DayInterface day = new Day15();
            day.part1();
            day.part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
