package aoc.days;

import aoc.DayInterface;
import aoc.ExoEntryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//   x 1 2 3
// y
// 1
// 2
// 3

public class Day13 implements DayInterface {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @AllArgsConstructor
    private class Position {
        int x;
        int y;
    }

    @AllArgsConstructor
    @Data
    private class Cart {
        int id;
        int x;
        int y;
        Direction direction;
        int nbCross;

        void move() {
            switch (direction) {
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
                case LEFT:
                    x--;
                    break;
                case RIGHT:
                    x++;
                    break;
            }
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    private class CartExo2 extends Cart {
        int moves;

        CartExo2(int id, int x, int y, Direction direction, int nbCross) {
            super(id, x, y, direction, nbCross);
            moves = 0;
        }

        @Override
        void move() {
            super.move();
            moves++;
        }
    }

    @Override
    public void part1() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(13);

        List<Position> turn_slash = new ArrayList<>(); // "/"
        List<Position> turn_backslash = new ArrayList<>(); // "\"
        List<Position> cross = new ArrayList<>(); // "+"

        List<Cart> carts = new ArrayList<>();

        int cartId = 0;
        for (int y = 0; y < entries.length; y++) {
            for (int x = 0; x < entries[y].length(); x++) {
                switch (entries[y].charAt(x)) {
                    case '/':
                        turn_slash.add(new Position(x, y));
                        break;
                    case '\\':
                        turn_backslash.add(new Position(x, y));
                        break;
                    case '+':
                        cross.add(new Position(x, y));
                        break;

                    case '^':
                        carts.add(new Cart(cartId++, x, y, Direction.UP, 0));
                        break;
                    case 'v':
                        carts.add(new Cart(cartId++, x, y, Direction.DOWN, 0));
                        break;
                    case '>':
                        carts.add(new Cart(cartId++, x, y, Direction.RIGHT, 0));
                        break;
                    case '<':
                        carts.add(new Cart(cartId++, x, y, Direction.LEFT, 0));
                        break;
                }
            }
        }

        Position firstCrash = null;

        Comparator<Cart> cartComparator = Comparator
                .comparing(Cart::getY)
                .thenComparing(Cart::getX);

        while (firstCrash == null) {
            List<Cart> orderedCarts = carts.stream().sorted(cartComparator).collect(Collectors.toList());

            for (Cart cart : orderedCarts) {
                // avance
                cart.move();

                // condition de sortie
                if (carts.stream().anyMatch(otherCart -> cart.x == otherCart.x
                        && cart.y == otherCart.y
                        && cart.id != otherCart.id)) {
                    // crash
                    if (firstCrash == null) {
                        firstCrash = new Position(cart.x, cart.y);
                    }
                }

                // virage
                if (turn_slash.stream().anyMatch((turn) -> turn.x == cart.x && turn.y == cart.y)) { // "/"
                    switch (cart.direction) {
                        case UP:
                            cart.setDirection(Direction.RIGHT);
                            break;
                        case DOWN:
                            cart.setDirection(Direction.LEFT);
                            break;
                        case LEFT:
                            cart.setDirection(Direction.DOWN);
                            break;
                        case RIGHT:
                            cart.setDirection(Direction.UP);
                            break;
                    }
                } else if (turn_backslash.stream().anyMatch((turn) -> turn.x == cart.x && turn.y == cart.y)) { // "\"
                    switch (cart.direction) {
                        case UP:
                            cart.setDirection(Direction.LEFT);
                            break;
                        case DOWN:
                            cart.setDirection(Direction.RIGHT);
                            break;
                        case LEFT:
                            cart.setDirection(Direction.UP);
                            break;
                        case RIGHT:
                            cart.setDirection(Direction.DOWN);
                            break;
                    }
                } else if (cross.stream().anyMatch((turn) -> turn.x == cart.x && turn.y == cart.y)) { // "+"
                    switch (cart.nbCross % 3) {
                        case 0:
                            switch (cart.direction) {
                                case UP:
                                    cart.setDirection(Direction.LEFT);
                                    break;
                                case DOWN:
                                    cart.setDirection(Direction.RIGHT);
                                    break;
                                case LEFT:
                                    cart.setDirection(Direction.DOWN);
                                    break;
                                case RIGHT:
                                    cart.setDirection(Direction.UP);
                                    break;
                            }
                            break;
                        case 2:
                            switch (cart.direction) {
                                case UP:
                                    cart.setDirection(Direction.RIGHT);
                                    break;
                                case DOWN:
                                    cart.setDirection(Direction.LEFT);
                                    break;
                                case LEFT:
                                    cart.setDirection(Direction.UP);
                                    break;
                                case RIGHT:
                                    cart.setDirection(Direction.DOWN);
                                    break;
                            }
                            break;
                    }
                    cart.setNbCross(cart.nbCross + 1);
                }
            }
        }

        System.out.println("Position of first crash is x = " + firstCrash.x + " y = " + firstCrash.y);
    }

    @Override
    public void part2() throws Exception {
        String[] entries = ExoEntryUtils.getEntries(13);

        List<Position> turn_slash = new ArrayList<>(); // "/"
        List<Position> turn_backslash = new ArrayList<>(); // "\"
        List<Position> cross = new ArrayList<>(); // "+"

        List<CartExo2> carts = new ArrayList<>();

        int cartId = 0;
        for (int y = 0; y < entries.length; y++) {
            for (int x = 0; x < entries[y].length(); x++) {
                switch (entries[y].charAt(x)) {
                    case '/':
                        turn_slash.add(new Position(x, y));
                        break;
                    case '\\':
                        turn_backslash.add(new Position(x, y));
                        break;
                    case '+':
                        cross.add(new Position(x, y));
                        break;

                    case '^':
                        carts.add(new CartExo2(cartId++, x, y, Direction.UP, 0));
                        break;
                    case 'v':
                        carts.add(new CartExo2(cartId++, x, y, Direction.DOWN, 0));
                        break;
                    case '>':
                        carts.add(new CartExo2(cartId++, x, y, Direction.RIGHT, 0));
                        break;
                    case '<':
                        carts.add(new CartExo2(cartId++, x, y, Direction.LEFT, 0));
                        break;
                }
            }
        }

        Comparator<CartExo2> cartComparator = Comparator
                .comparing(CartExo2::getMoves)
                .thenComparing(CartExo2::getY)
                .thenComparing(CartExo2::getX);

        int ticks = 0;

        while (carts.size() > 1) {

            // cette fois on bouge les carts 1 par 1
            CartExo2 cartToMove = carts.stream().min(cartComparator).get();

            // avance
            cartToMove.move();

            if(cartToMove.getMoves() > ticks) {
                ticks = cartToMove.getMoves();
            }

            // virage
            if (turn_slash.stream().anyMatch((turn) -> turn.x == cartToMove.x && turn.y == cartToMove.y)) { // "/"
                switch (cartToMove.direction) {
                    case UP:
                        cartToMove.setDirection(Direction.RIGHT);
                        break;
                    case DOWN:
                        cartToMove.setDirection(Direction.LEFT);
                        break;
                    case LEFT:
                        cartToMove.setDirection(Direction.DOWN);
                        break;
                    case RIGHT:
                        cartToMove.setDirection(Direction.UP);
                        break;
                }
            } else if (turn_backslash.stream().anyMatch((turn) -> turn.x == cartToMove.x && turn.y == cartToMove.y)) { // "\"
                switch (cartToMove.direction) {
                    case UP:
                        cartToMove.setDirection(Direction.LEFT);
                        break;
                    case DOWN:
                        cartToMove.setDirection(Direction.RIGHT);
                        break;
                    case LEFT:
                        cartToMove.setDirection(Direction.UP);
                        break;
                    case RIGHT:
                        cartToMove.setDirection(Direction.DOWN);
                        break;
                }
            } else if (cross.stream().anyMatch((turn) -> turn.x == cartToMove.x && turn.y == cartToMove.y)) { // "+"
                switch (cartToMove.nbCross % 3) {
                    case 0:
                        switch (cartToMove.direction) {
                            case UP:
                                cartToMove.setDirection(Direction.LEFT);
                                break;
                            case DOWN:
                                cartToMove.setDirection(Direction.RIGHT);
                                break;
                            case LEFT:
                                cartToMove.setDirection(Direction.DOWN);
                                break;
                            case RIGHT:
                                cartToMove.setDirection(Direction.UP);
                                break;
                        }
                        break;
                    case 2:
                        switch (cartToMove.direction) {
                            case UP:
                                cartToMove.setDirection(Direction.RIGHT);
                                break;
                            case DOWN:
                                cartToMove.setDirection(Direction.LEFT);
                                break;
                            case LEFT:
                                cartToMove.setDirection(Direction.UP);
                                break;
                            case RIGHT:
                                cartToMove.setDirection(Direction.DOWN);
                                break;
                        }
                        break;
                }
                cartToMove.setNbCross(cartToMove.nbCross + 1);
            }

            // si crash on supprime les 2 carts
            List<CartExo2> crashedCarts = carts.stream().filter(otherCart -> cartToMove.x == otherCart.x
                    && cartToMove.y == otherCart.y
                    && cartToMove.id != otherCart.id)
                    .collect(Collectors.toList());

            if (crashedCarts.size() > 0) {
                carts.removeAll(crashedCarts);
                carts.remove(cartToMove);
            }
        }

        while (carts.get(0).getMoves() < ticks) {
            carts.get(0).move();
        }

        System.out.println("Position of last standing cart is x = " + carts.get(0).getX() + " y = " + carts.get(0).getY());
    }
}

