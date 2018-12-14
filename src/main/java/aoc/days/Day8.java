package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 {

    @Getter
    @Setter
    private static class Node {
        List<Node> children;
        List<Integer> metadatas;

        Node() {
            this.children = new ArrayList<>();
            this.metadatas = new ArrayList<>();
        }

        int getTotalMetadatas() {
            return metadatas.stream().mapToInt(foo -> foo).sum() + children.stream().mapToInt(Node::getTotalMetadatas).sum();
        }

        int getValueOfNode() {
            if (this.children.isEmpty()) {
                return this.metadatas.stream().mapToInt(foo -> foo).sum();
            } else {
                int sum = 0;
                for (Integer metadata : this.metadatas) {
                    sum += this.children.size() > metadata - 1 ? this.children.get(metadata - 1).getValueOfNode() : 0; // On doit retirer 1 car le compteur commence à 1 et non 0 comme celui des listes
                }
                return sum;
            }
        }
    }

    private static int counter;
    private static String[] nodeDatas;

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(8, 1);
        nodeDatas = entries[0].split(" ");

        counter = 0;

        Node root = searchMetadatas();

        System.out.println("Total of metadatas : " + root.getTotalMetadatas());
    }

    // On va utiliser la récursivité pour créer les nodes au fur et mesure qu'on parcourt la string d'entrée
    private static Node searchMetadatas() {

        int nbChildren = Integer.valueOf(nodeDatas[counter++]);
        int nbMetadatas = Integer.valueOf(nodeDatas[counter++]);

        Node node = new Node();
        for (int i = 0; i < nbChildren; i++) {
            node.getChildren().add(searchMetadatas());
        }
        for (int j = 0; j < nbMetadatas; j++) {
            node.getMetadatas().add(Integer.valueOf(nodeDatas[counter++]));
        }

        return node;
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(8, 1);
        nodeDatas = entries[0].split(" ");

        counter = 0;

        Node root = searchMetadatas();

        System.out.println("Value of root node : " + root.getValueOfNode());
    }
}
