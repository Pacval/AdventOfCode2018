package aoc.days.day15;

import lombok.Data;

//   y 1 2 3
// x
// 1
// 2
// 3

@Data
public class Unit {
    Position position;
    UnitType type;
    int health;
    int damages;
    int turns;

    Unit(int x, int y, UnitType type) {
        this.position = new Position(x, y);
        this.type = type;
        this.health = 200;
        this.damages = 3;
        this.turns = 0;
    }

    public void doSomething() {
        // move
        // si pas adjacent à ennemi
        //   chercher positions target
        // si > 0
        //   prendre target plus courte + reading order
        //   générer chemin
        //   prendre case adjacente avec plus petit coût
        //   aller dessus

        // si proximité ennemi
        // attack
    }
}
