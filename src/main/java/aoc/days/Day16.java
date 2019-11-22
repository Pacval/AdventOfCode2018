package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 implements DayInterface {

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(16);

        int line = 0;

        Pattern patternBefore = Pattern.compile("Before: \\[(\\d*), (\\d*), (\\d*), (\\d*)]");
        Pattern patternInstruction = Pattern.compile("(.*) (.*) (.*) (.*)");
        Pattern patternAfter = Pattern.compile("After: {2}\\[(.*), (.*), (.*), (.*)]");

        List<Integer> beforeRegister = new ArrayList<>(4);
        List<Integer> afterRegister = new ArrayList<>(4);
        List<Integer> testRegister = new ArrayList<>(4);

        int samplesMatchingThreeOpcodes = 0;

        Map<String, InstructionInterface<List<Integer>, Integer, Integer, Integer>> instructions = getAllInstructions();

        while (true) {

            beforeRegister.clear();
            afterRegister.clear();

            Matcher matcherBefore = patternBefore.matcher(entries[line++]);
            if (!matcherBefore.find()) {
                break;
            }
            beforeRegister.add(Integer.valueOf(matcherBefore.group(1)));
            beforeRegister.add(Integer.valueOf(matcherBefore.group(2)));
            beforeRegister.add(Integer.valueOf(matcherBefore.group(3)));
            beforeRegister.add(Integer.valueOf(matcherBefore.group(4)));

            Matcher matcherInstruction = patternInstruction.matcher(entries[line++]);
            if (!matcherInstruction.find()) {
                break;
            }
            int a = Integer.parseInt(matcherInstruction.group(2));
            int b = Integer.parseInt(matcherInstruction.group(3));
            int c = Integer.parseInt(matcherInstruction.group(4));

            Matcher matcherAfter = patternAfter.matcher(entries[line++]);
            if (!matcherAfter.find()) {
                break;
            }
            afterRegister.add(Integer.valueOf(matcherAfter.group(1)));
            afterRegister.add(Integer.valueOf(matcherAfter.group(2)));
            afterRegister.add(Integer.valueOf(matcherAfter.group(3)));
            afterRegister.add(Integer.valueOf(matcherAfter.group(4)));

            int opcodes = 0;
            for (InstructionInterface instruction : instructions.values()) {

                testRegister.clear();
                testRegister.addAll(beforeRegister);
                instruction.doInstruct(testRegister, a, b, c);

                if (testRegister.equals(afterRegister)) {
                    opcodes++;
                }
            }

            if (opcodes >= 3) {
                samplesMatchingThreeOpcodes++;
            }


            line++;
        }

        System.out.println("Number of samples with 3 or more opcodes possible : " + samplesMatchingThreeOpcodes);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(16);


        Pattern patternBefore = Pattern.compile("Before: \\[(\\d*), (\\d*), (\\d*), (\\d*)]");
        Pattern patternInstruction = Pattern.compile("(.*) (.*) (.*) (.*)");
        Pattern patternAfter = Pattern.compile("After: {2}\\[(.*), (.*), (.*), (.*)]");

        List<Integer> beforeRegister = new ArrayList<>(4);
        List<Integer> afterRegister = new ArrayList<>(4);
        List<Integer> testRegister = new ArrayList<>(4);

        Map<String, InstructionInterface<List<Integer>, Integer, Integer, Integer>> instructions = getAllInstructions();

        Map<Integer, String> numberNameInstructionMap = new HashMap<>();

        // Etape 1 on va trouver les correspondances numéro / instruction

        mainLoop:
        while (true) {

            int line = 0;

            readFileLoop:
            while (true) {
                beforeRegister.clear();
                afterRegister.clear();

                Matcher matcherBefore = patternBefore.matcher(entries[line++]);
                if (!matcherBefore.find()) {
                    break readFileLoop;
                }
                beforeRegister.add(Integer.valueOf(matcherBefore.group(1)));
                beforeRegister.add(Integer.valueOf(matcherBefore.group(2)));
                beforeRegister.add(Integer.valueOf(matcherBefore.group(3)));
                beforeRegister.add(Integer.valueOf(matcherBefore.group(4)));

                Matcher matcherInstruction = patternInstruction.matcher(entries[line++]);
                if (!matcherInstruction.find()) {
                    break readFileLoop;
                }
                int nbInstruct = Integer.parseInt(matcherInstruction.group(1));
                int a = Integer.parseInt(matcherInstruction.group(2));
                int b = Integer.parseInt(matcherInstruction.group(3));
                int c = Integer.parseInt(matcherInstruction.group(4));

                Matcher matcherAfter = patternAfter.matcher(entries[line++]);
                if (!matcherAfter.find()) {
                    break readFileLoop;
                }
                afterRegister.add(Integer.valueOf(matcherAfter.group(1)));
                afterRegister.add(Integer.valueOf(matcherAfter.group(2)));
                afterRegister.add(Integer.valueOf(matcherAfter.group(3)));
                afterRegister.add(Integer.valueOf(matcherAfter.group(4)));

                // on ne cherche que si le numéro de l'instruction n'a pas été trouvé
                if (!numberNameInstructionMap.containsKey(nbInstruct)) {

                    List<String> possibleInstructions = new ArrayList<>();

                    for (Map.Entry<String, InstructionInterface<List<Integer>, Integer, Integer, Integer>> instruction : instructions.entrySet()) {
                        // on ne teste que les instructions dont on a pas trouvé les correspondances
                        if (!numberNameInstructionMap.containsValue(instruction.getKey())) {
                            testRegister.clear();
                            testRegister.addAll(beforeRegister);

                            // test instruction
                            instruction.getValue().doInstruct(testRegister, a, b, c);

                            if (testRegister.equals(afterRegister)) {
                                // si l'instruction correspond on enregistre le nom
                                possibleInstructions.add(instruction.getKey());
                            }
                        }
                    }

                    // Si qu'un seul nom de fonction possible, on enregistre la correspondance
                    if (possibleInstructions.size() == 1) {
                        numberNameInstructionMap.put(nbInstruct, possibleInstructions.get(0));

                        if (numberNameInstructionMap.size() == instructions.size()) {
                            // on a trouvé toutes les correspondances
                            break mainLoop;
                        }
                    }
                }

                line++;
            }
        }

        // Etape 2 on execute toutes les instructions

        // registres à 0
        testRegister.clear();
        testRegister.add(0);
        testRegister.add(0);
        testRegister.add(0);
        testRegister.add(0);

        for (int i = 3354; i < entries.length; i++) {
            Matcher matcherInstruction = patternInstruction.matcher(entries[i]);
            if (!matcherInstruction.find()) {
                System.out.println("error");
                break;
            }
            int nbInstruct = Integer.parseInt(matcherInstruction.group(1));
            int a = Integer.parseInt(matcherInstruction.group(2));
            int b = Integer.parseInt(matcherInstruction.group(3));
            int c = Integer.parseInt(matcherInstruction.group(4));

            // le numéro d'instruction nous donne le nom
            // le nom nous donne l'instruction
            instructions.get(numberNameInstructionMap.get(nbInstruct)).doInstruct(testRegister, a, b, c);
        }

        System.out.println("After executing program, register 0 contains : " + testRegister.get(0));
    }

    @FunctionalInterface
    private interface InstructionInterface<Register, A, B, C> {
        void doInstruct(Register register, A a, B b, C c);
    }

    /**
     * @return la liste des instructions sous forme de fonctions lambdas
     */
    private Map<String, InstructionInterface<List<Integer>, Integer, Integer, Integer>> getAllInstructions() {

        Map<String, InstructionInterface<List<Integer>, Integer, Integer, Integer>> instructions = new HashMap<>();

        instructions.put("addr", (register, a, b, c) -> register.set(c, register.get(a) + register.get(b)));

        instructions.put("addi", (register, a, b, c) -> register.set(c, register.get(a) + b));

        instructions.put("mulr", (register, a, b, c) -> register.set(c, register.get(a) * register.get(b)));

        instructions.put("muli", (register, a, b, c) -> register.set(c, register.get(a) * b));

        instructions.put("banr", (register, a, b, c) -> register.set(c, register.get(a) & register.get(b)));

        instructions.put("bani", (register, a, b, c) -> register.set(c, register.get(a) & b));

        instructions.put("borr", (register, a, b, c) -> register.set(c, register.get(a) | register.get(b)));

        instructions.put("bori", (register, a, b, c) -> register.set(c, register.get(a) | b));

        instructions.put("setr", (register, a, b, c) -> register.set(c, register.get(a)));

        instructions.put("seti", (register, a, b, c) -> register.set(c, a));

        instructions.put("gtir", (register, a, b, c) -> register.set(c, a > register.get(b) ? 1 : 0));

        instructions.put("gtri", (register, a, b, c) -> register.set(c, register.get(a) > b ? 1 : 0));

        instructions.put("gtrr", (register, a, b, c) -> register.set(c, register.get(a) > register.get(b) ? 1 : 0));

        instructions.put("eqir", (register, a, b, c) -> register.set(c, a.equals(register.get(b)) ? 1 : 0));

        instructions.put("eqri", (register, a, b, c) -> register.set(c, register.get(a).equals(b) ? 1 : 0));

        instructions.put("eqrr", (register, a, b, c) -> register.set(c, register.get(a).equals(register.get(b)) ? 1 : 0));

        return instructions;
    }


}
