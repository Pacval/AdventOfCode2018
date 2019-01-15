package aoc.days;

import aoc.DayInterface;

public class Day11 implements DayInterface {

    @Override
    public void part1() throws Exception {
        int serialNumber = 9424;

        int[][] grid = new int[300][300];

        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                grid[x - 1][y - 1] = (((((x + 10) * y + serialNumber) * (x + 10)) % 1000) / 100) - 5;
            }
        }

        // On peut afficher le resultat
        /*for (int y = 0; y < 300; y++) {
            for (int x = 0; x < 300; x++) {
                System.out.print(StringUtils.leftPad(String.valueOf(grid[x][y]), 4));
            }
            System.out.println();
        }*/

        // On cherche le carré le plus fort
        int strongestPower = -100;
        int posX = -1;
        int posY = -1;
        for (int y = 0; y < 300 - 2; y++) {
            for (int x = 0; x < 300 - 2; x++) {
                int squareValue = grid[x][y] + grid[x][y + 1] + grid[x][y + 2] +
                        grid[x + 1][y] + grid[x + 1][y + 1] + grid[x + 1][y + 2] +
                        grid[x + 2][y] + grid[x + 2][y + 1] + grid[x + 2][y + 2];

                if (squareValue > strongestPower) {
                    strongestPower = squareValue;
                    posX = x + 1;
                    posY = y + 1;
                }
            }
        }

        System.out.println("Strongest power : " + strongestPower);
        System.out.println("Position : " + posX + "," + posY);
    }

    @Override
    public void part2() throws Exception {
        int serialNumber = 9424;

        int[][] grid = new int[300][300];

        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                grid[x - 1][y - 1] = (((((x + 10) * y + serialNumber) * (x + 10)) % 1000) / 100) - 5;
            }
        }

        int strongestPower = -100;
        int posX = -1;
        int posY = -1;
        int squareSize = -1;

        for (int s = 1; s < 300; s++) { // taille carré testé
            for (int y = 0; y <= 300 - s; y++) {
                for (int x = 0; x <= 300 - s; x++) {

                    int squareValue = 0;

                    // Comme on ne connait pas la taille du carré initialement on doit procéder autrement
                    for (int i = x; i < x + s; i++) {
                        for (int j = y; j < y + s; j++) {
                            squareValue += grid[i][j];
                        }
                    }

                    if (squareValue > strongestPower) {
                        strongestPower = squareValue;
                        posX = x + 1;
                        posY = y + 1;
                        squareSize = s;
                    }
                }
            }

            System.out.println("Square size tested : " + s + " . Strongest square found from beginning : " + strongestPower);
        }

        System.out.println("Strongest power : " + strongestPower);
        System.out.println("Position : " + posX + "," + posY + "," + squareSize);
    }
}
