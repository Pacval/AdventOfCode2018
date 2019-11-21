package aoc.days.day15;

import lombok.Data;

@Data
class Wall {
    Position position;

    Wall(int x, int y) {
        this.position = new Position(x, y);
    }
}
