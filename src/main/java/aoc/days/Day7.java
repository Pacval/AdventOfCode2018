package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day7 implements DayInterface {

    @Getter
    private static class Instruction {
        private String name;
        private List<String> instBefore;

        Instruction(String name) {
            this.name = name;
            this.instBefore = new ArrayList<>();
        }

        void insertBefore(String name) {
            instBefore.add(name);
        }
    }

    @Getter
    @Setter
    private static class Elve {
        private int secTaskRemaining;
        private Character jobName;

        Elve() {
            this.secTaskRemaining = 0;
            this.jobName = null;
        }

        void giveWork(char jobName) {
            this.jobName = jobName;
            this.secTaskRemaining = (int) jobName - 5; // 60 + (int) jobName - 64 - 1 (A = 65 -> A - 64 = 1)  - 1 car le travail commence directement
        }

        void work() {
            System.out.print(jobName + "   ");
            this.secTaskRemaining--;
        }

        boolean isWorking() {
            return this.jobName != null;
        }
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(7);

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
            instructions.stream()
                    .filter(inst -> inst.getInstBefore().contains(instructionToProcess.getName()))
                    .forEach(inst -> inst.getInstBefore().remove(instructionToProcess.getName()));

            // On vire cette instruction de la liste et on affiche son nom
            instructions.remove(instructionToProcess);
            System.out.print(instructionToProcess.getName());
        }
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(7);

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

        List<Elve> elves = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            elves.add(new Elve());
        }

        // le timer
        int secCounter = 0;

        // Tant qu'il y a des instructions ou qu'un elf est en train de travailler
        while (!instructions.isEmpty() || elves.stream().anyMatch(Elve::isWorking)) {

            // Pour chaque elfe on vérifie si il travaille ou si il ne fait rien
            // il travaille -> on avance sont travail d'une seconde
            // il ne fait rien -> on vérifie qu'un travail est libre et on lui donne
            for (Elve elve : elves) {
                if (elve.isWorking()) {
                    elve.work();
                } else {
                    Optional<Instruction> instructionToProcess = instructions.stream()
                            .filter(inst -> inst.getInstBefore().isEmpty())
                            .min(Comparator.comparing(Instruction::getName));

                    if(instructionToProcess.isPresent()) {
                        elve.giveWork(instructionToProcess.get().getName().charAt(0));
                        instructions.remove(instructionToProcess.get());
                    }
                }
            }

            // Pour tous les elfes qui ont fini leur travail, on libère les instructions suivantes
            for(Elve elve : elves.stream().filter(Elve::isWorking).filter(foo -> foo.getSecTaskRemaining() == 0).collect(Collectors.toList())) {
                instructions.stream()
                        .filter(inst -> inst.getInstBefore().contains(elve.getJobName().toString()))
                        .forEach(inst -> inst.getInstBefore().remove(elve.getJobName().toString()));
                elve.setJobName(null);
            }

            System.out.println(secCounter);
            secCounter++;
        }

        System.out.println("Full time to do all tasks : " + secCounter / 60 + "min and " + secCounter % 60 + " sec");
        System.out.println("Total secondes : " + secCounter);
    }
}
