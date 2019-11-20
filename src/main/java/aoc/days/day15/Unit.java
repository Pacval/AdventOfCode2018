package aoc.days.day15;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//   y 1 2 3
// x
// 1
// 2
// 3

@Data
class Unit {
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

    private void hit(int damagesTaken) {
        this.health -= damagesTaken;
    }


    void doTurn(List<Unit> units, List<Wall> walls) {

        List<Position> adjacentPositions = this.position.getAdjacentPositions();

        // on vérifie qu'aucune unité ennemie n'est à coté
        if (units.stream().noneMatch(item -> item.getType() != this.type && adjacentPositions.contains(item.position))) {
            // move
            // si pas adjacent à ennemi
            //   chercher positions target
            // si > 0
            //   prendre target plus courte + reading order
            //   générer chemin
            //   prendre case adjacente avec plus petit coût
            //   aller dessus

            List<Position> targetPositions = getAllTargetPositions(units, walls);
            System.out.println("toto");
        }

        List<Unit> enemiesAdjacent = units.stream().filter(item -> item.getType() != this.type && adjacentPositions.contains(item.position)).collect(Collectors.toList());

        if (enemiesAdjacent.size() >= 1) {
            Comparator<Unit> targetComparator = (o1, o2) -> {
                if (o1.getPosition().getX() < o2.getPosition().getX()) {
                    return -1;
                } else if (o1.getPosition().getX() > o2.getPosition().getX()) {
                    return 1;
                } else {
                    return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
                }
            };

            Optional<Unit> enemyToTarget = enemiesAdjacent.stream().min(targetComparator);
            enemyToTarget.ifPresent(unit -> unit.hit(this.damages));
        }

        this.turns++;
    }

    /** ---------- fonctions privées pour le déplacement ---------- */
    
    List<Position> getAllTargetPositions(List<Unit> units, List<Wall> walls) {

        List<Position> allPositionsAdjacentToEnemies = units.stream().filter(unit -> unit.type != this.type).map(item -> item.getPosition().getAdjacentPositions()).collect(Collectors.toList())
                .stream().flatMap(List::stream).collect(Collectors.toList());

        return allPositionsAdjacentToEnemies.stream().filter(pos -> units.stream().noneMatch(unit -> unit.position.equals(pos)
                && walls.stream().noneMatch(wall -> wall.position.equals(pos)))).collect(Collectors.toList());
    }
}
