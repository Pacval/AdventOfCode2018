package aoc.days.day15;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
class Position {
    int x;
    int y;

    public List<Position> getAdjacentPositions() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x + 1, y));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x, y + 1));
        return positions;
    }
}
