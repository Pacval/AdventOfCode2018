package aoc;

import aoc.days.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        try {
            Day9.exo2();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
