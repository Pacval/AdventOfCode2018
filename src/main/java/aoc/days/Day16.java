package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day16 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(16);

        int line = 0;

        Pattern patternBefore = Pattern.compile("Before: [(.*?), (.*?), (.*?), (.*?)]");

        while (true) {
            List<Integer> beforeRegister = new ArrayList<>(4);
            List<Integer> afterRegister = new ArrayList<>(4);

            int

            List<Integer> registers = new ArrayList<>();
            Method method = getClass().getMethod("toto", List.class, Integer.class, Integer.class, Integer.class);
            method.invoke(this, registers, a, b, c);

        }
    }

    @Override
    public void part2() throws Exception {

    }

    /* On va faire une fonction séparée pour chaque instruction */
    /* Chaque instruction prend en paramètre une liste correspondant aux 4 registres, et modifie cette liste */

    private void addr(List<Integer> registers, int a, int b, int c) {
        registers.set(c, registers.get(a) + registers.get(b));
    }
}
