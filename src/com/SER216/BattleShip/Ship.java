package com.SER216.BattleShip;

import java.util.*;

/**
 * Created by zawata on 4/12/2017.
 */
// Holds the values for the ships
public class Ship {
    private ShipType typeofShip;
    private Direction directionOfShip;
    private int x, y, hits;

    private boolean placed;

    public enum ShipType {
        AircraftCarrier(5, "Aircraft Carrier"),
        Battleship(4, "Battleship"),
        Destroyer(3, "Destroyer"),
        Submarine(3, "Submarine"),
        PatrolBoat(2, "Patrol Boat");

        private final int size;
        private final String name;
        ShipType(final int size, String name) { this.size = size; this.name = name;}
        public int getSize() { return size; }
        public String getName() { return name; }
    }

    public static ShipType getShipValuebyName(String name) {
        for (ShipType ship : ShipType.values()) {
            if(ship.getName().equals(name))
                return ship;
        }
        return null;
    }

    public static String[] getShipNames() {
        List<String> retval = new ArrayList<>();
        for (ShipType ship : ShipType.values()) {
            retval.add(ship.getName());
        }
        return retval.toArray(new String[0]);
    }

    public enum Direction {
        Horizontal(0, "Horizontal"),
        Vertical(1, "Vertical");

        private final int value;
        private final String name;
        Direction(final int size, String name) { this.value = size; this.name = name;}
        public int getValue() { return value; }
        public String getName() { return name; }
    }

    public static Direction getDirectionValuebyName(String name) {
        for (Direction dir : Direction.values()) {
            if(dir.getName().equals(name))
                return dir;
        }
        return null;
    }

    public static String[] getDirectionNames() {
        List<String> retval = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            retval.add(dir.getName());
        }
        return retval.toArray(new String[0]);
    }

    public Ship() {
        this.x = 0;
        this.y = 0;
        this.hits = 0;
    }

    public Ship(String ship, String direction) {
        this.typeofShip = ShipType.valueOf(ship.replaceAll(" ", ""));
        this.directionOfShip = Direction.valueOf(direction.replaceAll(" ", ""));
        this.x = 0;
        this.y = 0;
        this.hits = 0;
    }

    public Ship(ShipType name, Direction direction) {
        this.typeofShip = name;
        this.directionOfShip = direction;
        this.x = 0;
        this.y = 0;
        this.hits = 0;
    }

    public void shipReset() {
        x = 0;
        y = 0;
        directionOfShip = Direction.Horizontal;
        placed = false;
    }

    //If hits on this ship equal its size, its sunk
    public boolean sunk() {
        if (this.hits == this.typeofShip.getSize()) {
            return true;
        } else {
            return false;
        }
    }

    public ShipType getShipType() { return typeofShip; }
    public Direction getDirectionOfShip() { return directionOfShip; }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getSize() { return typeofShip.getSize(); }

    public int getHits() { return hits; }
    public void hit() { if (this.hits < this.getSize()) this.hits++; }

    public boolean isPlaced() { return placed; }
    public boolean isSunk() { return (this.hits == this.getSize()); }

    public void place(int X, int Y) {
        this.x = X;
        this.y = Y;
        this.placed = true;
    }

    public void remove() {
        this.x = 0;
        this.y = 0;
        this.placed = false;
    }

    public boolean Occupies(int x, int y) {
        if (this.directionOfShip.equals(Direction.Vertical)) {
            if(this.x == x) {
                if (this.y <= y && y < (this.y + this.getSize())) {
                    return true;
                }
            }
        } else {
            if (this.directionOfShip.equals(Direction.Horizontal)) {
                if(this.y == y) {
                    if (this.x <= x && x < (this.x + this.getSize())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        return typeofShip == ship.typeofShip;
    }

    @Override
    public int hashCode() {
        return typeofShip.hashCode();
    }

    public String toString() {
        return typeofShip.getName() + " " +
                directionOfShip.toString() + " " +
                x + " " +
                y + " " +
                typeofShip.getSize() + " " +
                placed + " " +
                hits;
    }
}