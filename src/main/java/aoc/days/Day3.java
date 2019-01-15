package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 implements DayInterface {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Fabric {
        private int id;
        private int distFromSide;
        private int distFromTop;
        private int width;
        private int height;
    }

    @Getter
    @Setter
    private static class Case {
        private int col;
        private int line;
        private int used;

        Case(int col, int line) {
            this.col = col;
            this.line = line;
            this.used = 0;
        }

        void addUse() {
            this.used++;
        }
    }

    @Getter
    @Setter
    private static class UpgradedCase {
        private int col;
        private int line;
        private List<Integer> usedBy;

        UpgradedCase(int col, int line) {
            this.col = col;
            this.line = line;
            usedBy = new ArrayList<>();
        }

        void addUse(int id) {
            this.usedBy.add(id);
        }
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(3);

        // store fabrics informations
        List<Fabric> fabrics = Arrays.stream(entries).map(item -> new Fabric(
                Integer.parseInt(item.substring(1, item.indexOf('@') - 1)),
                Integer.parseInt(item.substring(item.indexOf('@') + 2, item.indexOf(','))),
                Integer.parseInt(item.substring(item.indexOf(',') + 1, item.indexOf(':'))),
                Integer.parseInt(item.substring(item.indexOf(':') + 2, item.indexOf('x'))),
                Integer.parseInt(item.substring(item.indexOf('x') + 1))
        )).collect(Collectors.toList());

        int maxWidth = fabrics.stream().mapToInt(item -> item.getDistFromSide() + item.getWidth()).max().getAsInt();
        int maxHeight = fabrics.stream().mapToInt(item -> item.getDistFromTop() + item.getHeight()).max().getAsInt();

        // create every case of plateform
        List<Case> cases = new ArrayList<>();
        for (int i = 0; i <= maxWidth; i++) {
            for (int j = 0; j <= maxHeight; j++) {
                cases.add(new Case(i, j));
            }
        }

        // add use to case if fabric is on it
        for (Case currentCase : cases) {
            for (Fabric fabric : fabrics) {
                if (currentCase.getCol() >= fabric.getDistFromSide() &&
                        currentCase.getCol() <= fabric.getDistFromSide() + fabric.getWidth() - 1 &&
                        currentCase.getLine() >= fabric.getDistFromTop() &&
                        currentCase.getLine() <= fabric.getDistFromTop() + fabric.getHeight() - 1) {
                    currentCase.addUse();
                }
            }
        }

        long casesUsedTooMuch = cases.stream().filter(item -> item.getUsed() > 1).count();
        System.out.println(casesUsedTooMuch);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(3);

        List<Fabric> fabrics = Arrays.stream(entries).map(item -> new Fabric(
                Integer.parseInt(item.substring(1, item.indexOf('@') - 1)),
                Integer.parseInt(item.substring(item.indexOf('@') + 2, item.indexOf(','))),
                Integer.parseInt(item.substring(item.indexOf(',') + 1, item.indexOf(':'))),
                Integer.parseInt(item.substring(item.indexOf(':') + 2, item.indexOf('x'))),
                Integer.parseInt(item.substring(item.indexOf('x') + 1))
        )).collect(Collectors.toList());

        int maxWidth = fabrics.stream().mapToInt(item -> item.getDistFromSide() + item.getWidth()).max().getAsInt();
        int maxHeight = fabrics.stream().mapToInt(item -> item.getDistFromTop() + item.getHeight()).max().getAsInt();

        List<UpgradedCase> cases = new ArrayList<>();
        for (int i = 0; i <= maxWidth; i++) {
            for (int j = 0; j <= maxHeight; j++) {
                cases.add(new UpgradedCase(i, j));
            }
        }

        // store all fabrics that use a case
        for (UpgradedCase currentCase : cases) {
            for (Fabric fabric : fabrics) {
                if (currentCase.getCol() >= fabric.getDistFromSide() &&
                        currentCase.getCol() <= fabric.getDistFromSide() + fabric.getWidth() - 1 &&
                        currentCase.getLine() >= fabric.getDistFromTop() &&
                        currentCase.getLine() <= fabric.getDistFromTop() + fabric.getHeight() - 1) {
                    currentCase.addUse(fabric.getId());
                }
            }
        }

        for (Fabric fabric : fabrics) {
            // check if all the cases that contains the current fabric, contain only this fabric
            if (cases.stream().filter(item -> item.getUsedBy().contains(fabric.getId())).allMatch(item -> item.getUsedBy().size() <= 1)) {
                System.out.println(fabric.getId());
            }
        }
    }
}
