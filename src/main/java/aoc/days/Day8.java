package aoc.days;

import aoc.ExoEntryUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day8 {
    
    public static void exo1() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(8, 1);
        String[] nodeDatas = entries[0].split(" ");
    }

    // On va utiliser la récursivité pour aller chercher les dernières nodes et remonter au fur et à mesure les metadatas
    private int searchMetadatas(String[] nodeDatas) {
        return 0;
    }

    public static void exo2() throws IOException {

        String[] entries = ExoEntryUtils.getEntries(8, 1);

    }
}
