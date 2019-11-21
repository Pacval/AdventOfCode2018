package aoc.days.day15;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

//   y 1 2 3
// x
// 1
// 2
// 3

@Data
class Unit implements Cloneable {
    Position position;
    UnitType type;
    int health;
    int damages;

    Unit(int x, int y, UnitType type) {
        this.position = new Position(x, y);
        this.type = type;
        this.health = 200;
        this.damages = 3;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private void hit(int damagesTaken) {
        this.health -= damagesTaken;
    }

    Comparator<Unit> hitTargetReadingOrderComparator = (o1, o2) -> {
        if (o1.getHealth() < o2.getHealth()) {
            return -1;
        } else if (o1.getHealth() > o2.getHealth()) {
            return 1;
        } else {
            if (o1.getPosition().getX() < o2.getPosition().getX()) {
                return -1;
            } else if (o1.getPosition().getX() > o2.getPosition().getX()) {
                return 1;
            } else {
                return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
            }
        }
    };

    Comparator<Position> positionReadingOrderComparator = (o1, o2) -> {
        if (o1.getX() < o2.getX()) {
            return -1;
        } else if (o1.getX() > o2.getX()) {
            return 1;
        } else {
            return Integer.compare(o1.getY(), o2.getY());
        }
    };


    void doTurn(List<Unit> units, List<Wall> walls) {

        List<Position> adjacentPositions = this.position.getAdjacentPositions();

        // on vérifie qu'aucune unité ennemie n'est à coté
        if (units.stream().noneMatch(item -> item.getType() != this.type && adjacentPositions.contains(item.position))) {
            Position nearestTarget = getNearestTarget(units, walls);

            if (nearestTarget != null) {
                moveTo(nearestTarget, units, walls);
            }

        }

        List<Position> newAdjacentPositions = this.position.getAdjacentPositions();
        List<Unit> enemiesAdjacent = units.stream().filter(item -> item.getType() != this.type && newAdjacentPositions.contains(item.position)).collect(Collectors.toList());

        if (enemiesAdjacent.size() >= 1) {
            Optional<Unit> enemyToTarget = enemiesAdjacent.stream().min(hitTargetReadingOrderComparator);
            enemyToTarget.ifPresent(unit -> unit.hit(this.damages));
        }
    }

    /* ---------- fonctions privées pour le déplacement ---------- */

    /**
     * Permet de trouver la plus proche target accessible (case adjacente à un ennemi)
     */
    private Position getNearestTarget(List<Unit> units, List<Wall> walls) {
        List<Position> targets = getAllTargetPositions(units, walls);
        if (targets.isEmpty()) {
            return null;
        }

        Map<Position, Integer> accessiblePositions = getDistanceFromAllAccessiblePositions(this.position, units, walls);
        if (accessiblePositions.isEmpty()) {
            return null;
        }

        Map<Position, Integer> targetAccessiblePositions = accessiblePositions.entrySet().stream()
                .filter(item -> targets.contains(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (targetAccessiblePositions.isEmpty()) {
            return null;
        }

        Integer minDistance = Collections.min(targetAccessiblePositions.values());

        List<Position> nearestTargetPositions = targetAccessiblePositions.entrySet().stream().filter(item -> item.getValue().equals(minDistance)).map(Map.Entry::getKey).collect(Collectors.toList());

        return nearestTargetPositions.stream().min(positionReadingOrderComparator).orElse(null);
    }

    /**
     * Permet de trouver toutes les cases "target" : toutes les cases libres adjacentes à un ennemi
     */
    private List<Position> getAllTargetPositions(List<Unit> units, List<Wall> walls) {

        List<Position> allPositionsAdjacentToEnemies = units.stream().filter(unit -> unit.type != this.type).map(item -> item.getPosition().getAdjacentPositions()).collect(Collectors.toList())
                .stream().flatMap(List::stream).collect(Collectors.toList());

        return allPositionsAdjacentToEnemies.stream().filter(pos -> units.stream().noneMatch(unit -> unit.position.equals(pos)
                && walls.stream().noneMatch(wall -> wall.position.equals(pos)))).collect(Collectors.toList());
    }

    /**
     * Permet de trouver toutes les cases accessibles depuis le point de départ (chemin non bloqué)
     */
    private Map<Position, Integer> getDistanceFromAllAccessiblePositions(Position startPosition, List<Unit> units, List<Wall> walls) {
        List<Position> notAccessiblePositions = units.stream().map(Unit::getPosition).collect(Collectors.toList());
        notAccessiblePositions.addAll(walls.stream().map(Wall::getPosition).collect(Collectors.toList()));

        Map<Position, Integer> positionsToAnalyze = new HashMap<>();
        Map<Position, Integer> positionsAnalyzed = new HashMap<>();
        positionsToAnalyze.put(startPosition, 0);

        while (!positionsToAnalyze.isEmpty()) {

            // prendre case liste à analyser
            Map.Entry<Position, Integer> processing = positionsToAnalyze.entrySet().iterator().next();

            // mettre case dans map analysées
            positionsAnalyzed.put(processing.getKey(), processing.getValue());
            positionsToAnalyze.remove(processing.getKey());

            // prendre cases adjacentes
            List<Position> adjacentPositions = processing.getKey().getAccessibleAdjacentPositions(notAccessiblePositions);

            for (Position pos : adjacentPositions) {
                if (positionsToAnalyze.containsKey(pos)) {
                    //  si dans liste à analyser
                    //   si dist < , modifier distance
                    if (positionsToAnalyze.get(pos) > processing.getValue() + 1) {
                        positionsToAnalyze.replace(pos, processing.getValue() + 1);
                    }
                } else if (positionsAnalyzed.containsKey(pos)) {
                    //  si dans liste analysée
                    //    si dist <, changer dist et mettre dans à analyser
                    if (positionsAnalyzed.get(pos) > processing.getValue() + 1) {
                        positionsAnalyzed.remove(pos);
                        positionsToAnalyze.put(pos, processing.getValue() + 1);
                    }
                } else {
                    //  si pas dans liste à analyser ni liste analysées
                    //   mettre dans map à ananlyser avec dist + 1
                    positionsToAnalyze.put(pos, processing.getValue() + 1);
                }
            }
        }
        return positionsAnalyzed;
    }

    /**
     * Permet de déplacer l'unité vers la target en empruntant le plus court chemin
     */
    private void moveTo(Position target, List<Unit> units, List<Wall> walls) {

        // récupération cases à proximité
        List<Position> adjPos = this.position.getAdjacentPositions();

        // récupération des poids des chemins jusqu'à la case target pour les cases adjacentes
        Map<Position, Integer> adjacentPaths = getDistanceFromAllAccessiblePositions(target, units, walls).entrySet().stream()
                .filter(item -> adjPos.contains(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // poids minimum
        Integer minWeight = Collections.min(adjacentPaths.values());

        // liste des chemins avec poids minimum
        List<Position> minWeightAdjacentPath = adjacentPaths.entrySet().stream().filter(item -> item.getValue().equals(minWeight)).map(Map.Entry::getKey).collect(Collectors.toList());

        // mouvement sur la case avec poids minimum et devant dans l'ordre de lecture
        minWeightAdjacentPath.stream().min(positionReadingOrderComparator).ifPresent(nextPositionUnit -> this.position = nextPositionUnit);
    }
}
