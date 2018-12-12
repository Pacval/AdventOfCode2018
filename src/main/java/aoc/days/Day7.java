package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    @Getter
    private static class Instruction {
        private String name;
        private List<String> instBefore;

        public Instruction(String name) {
            this.name = name;
            this.instBefore = new ArrayList<>();
        }

        public void insertBefore(String name) {
            instBefore.add(name);
        }
    }

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(7, 1);

        List<Instruction> instructions = new ArrayList<>();
        for (String entry : entries) {
            String stepBefore = entry.split(" ")[1];
            String step = entry.split(" ")[7];

            if (instructions.stream().noneMatch(inst -> inst.getName().equals(step))) {
                instructions.add(new Instruction(step));
            }
            if (instructions.stream().noneMatch(inst -> inst.getName().equals(stepBefore))) {
                instructions.add(new Instruction(stepBefore));
            }
            for (Instruction instructionToAdd : instructions.stream().filter(inst -> inst.getName().equals(step)).collect(Collectors.toList())) {
                instructionToAdd.insertBefore(stepBefore);
            }
        }

        while (!instructions.isEmpty()) {
            // On récupère l'instruction exécutable (pas d'instruction avant) la plus petite par ordre alphabétique
            Instruction instructionToProcess = instructions.stream().filter(inst -> inst.getInstBefore().isEmpty()).min(Comparator.comparing(Instruction::getName)).get();

            // On vire cette instruction de la liste d'attente des autres
            instructions.stream().filter(inst -> inst.getInstBefore()
                    .contains(instructionToProcess.getName()))
                    .forEach(inst -> inst.getInstBefore().remove(instructionToProcess.getName()));

            // On vire cette instruction de la liste et on affiche son nom
            instructions.remove(instructionToProcess);
            System.out.print(instructionToProcess.getName());
        }
    }

    public static void exo2() throws IOException {

    }
}
