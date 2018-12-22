package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Light {
        private int posX;
        private int posY;
        private int moveX;
        private int moveY;

        void move() {
            this.posX += moveX;
            this.posY += moveY;
        }
    }

    // Fonction permettant de récupérer la valeur d'une string en gérant les "-"
    private static int getValue(String str) {
        String strTrimed = str.replaceAll(" ", "");
        if (strTrimed.charAt(0) == '-') {
            return Integer.valueOf(strTrimed.substring(1)) * -1;
        } else {
            return Integer.valueOf(strTrimed);
        }
    }

    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(10, 1);

        List<Light> lights = new ArrayList<>();

        for (String line : entries) {
            Light newLight = new Light();
            newLight.setPosX(getValue(line.substring(line.indexOf("position=<") + 10, line.indexOf(","))));
            newLight.setPosY(getValue(line.substring(line.indexOf(",") + 1, line.indexOf("> velocity=<"))));
            newLight.setMoveX(getValue(line.substring(line.indexOf("> velocity=<") + 12, line.lastIndexOf(","))));
            newLight.setMoveY(getValue(line.substring(line.lastIndexOf(",") + 1, line.lastIndexOf(">"))));
            lights.add(newLight);
        }

        // En analysant les valeurs d'entrées on peut voir que les points ne se rencontrent pas avant un petit bout de temps.
        // On fait bouger 10000 fois les lumières avant de commencer à regarder
        for (int i = 0; i < 10000; i++) {
            lights.forEach(Light::move);
        }

        // On récupère le fichier dans lequel on va écrire
        try (BufferedWriter writer = Files.newBufferedWriter(new File("day10_out.txt").toPath())) {

            for (int i = 0; i < 100; i++) {
                writer.write(i + "\n");
                System.out.println(i);
                int minX = lights.stream().mapToInt(Light::getPosX).min().getAsInt();
                int minY = lights.stream().mapToInt(Light::getPosY).min().getAsInt();
                int maxX = lights.stream().mapToInt(Light::getPosX).max().getAsInt();
                int maxY = lights.stream().mapToInt(Light::getPosY).max().getAsInt();

                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        int currentX = x;
                        int currentY = y;

                        if (lights.stream().anyMatch(light -> light.getPosX() == currentX && light.getPosY() == currentY)) {
                            writer.write("#");
                        } else {
                            writer.write(".");
                        }
                    }
                    writer.write("\n");
                }

                // On bouge les lumières
                lights.forEach(Light::move);
                writer.write("\n\n\n");
            }
        }
    }

    public static void exo2() {

        // Pour l'exo 2, on a pas vraiment besoin de code : on a déjà la réponse grâce à l'exo 1
        System.out.println(10000 + 76);
    }
}
