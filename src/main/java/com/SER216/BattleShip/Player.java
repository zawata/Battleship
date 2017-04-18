package com.SER216.BattleShip;

import java.util.ArrayList;
import java.util.List;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/13/2017.
 */
public class Player {

    private int[][] board = new int[boardWidth][boardWidth];

    //crude implementation of fixed Length Set
    private List<Ship> shipFleet = new ArrayList<>();
    private boolean addShip(Ship ship) {
        if(shipFleet.size() != 5 && !shipFleet.contains(ship)) {
            shipFleet.add(ship);
        }
        return false;
    }

    public Player() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean addShip(Ship ship, int x, int y) {
        //remove old Ships to place new ones
        removeShip(ship.getShipType());

        if(!ship.isPlaced()) {
            if (ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                if (y <= ((boardWidth + 1) - ship.getSize())) {
                    for (int i = 0; i < ship.getSize(); i++) {
                        if(getBoardValue(x, (i + y)) != MainGUI.TileColors.Blank.getValue())
                            return false;
                    }
                    ship.place(x, y);
                    addShip(ship);
                    for (int i = 0; i < ship.getSize(); i++) {
                        setBoardValue(x, (i + y), MainGUI.TileColors.Green.getValue());
                    }
                    return true;
                }
            } else {
                if (ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                    if (x <= ((boardWidth + 1) - ship.getSize())) {
                        for (int i = 0; i < ship.getSize(); i++) {
                            if(getBoardValue((i + x), y) != MainGUI.TileColors.Blank.getValue())
                                return false;
                        }
                        ship.place(x, y);
                        addShip(ship);
                        for (int i = 0; i < ship.getSize(); i++) {
                            setBoardValue((i + x), y, MainGUI.TileColors.Green.getValue());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("Duplicates")
    public boolean removeShip(Ship.ShipType shiptype) {
        Ship oldShip = null;
        for(Ship ship : shipFleet) {
            if(ship.getShipType().equals(shiptype)) {
                oldShip = ship;
            }
        }
        if(oldShip != null) {
            if (oldShip.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                shipFleet.remove(oldShip);
                for (int i = 0; i < oldShip.getSize(); i++) {
                    setBoardValue(oldShip.getX(), (i + oldShip.getY()), MainGUI.TileColors.Blank.getValue());
                }
                oldShip.remove();
                return true;
            } else {
                if (oldShip.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                    shipFleet.remove(oldShip);
                    for (int i = 0; i < oldShip.getSize(); i++) {
                        setBoardValue((i + oldShip.getX()), oldShip.getY(), MainGUI.TileColors.Blank.getValue());
                    }
                    oldShip.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allShipsPlaced() {
        for(Ship ship : shipFleet) {
            if (!ship.isPlaced()) {
                return false;
            }
        }
        return shipFleet.size() == 5;
    }

    public boolean validShot(int x, int y) {
        if(getBoardValue(x, y) == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkShot() {
        return false;
    }

    public void turn() {

    }

    /**
     * Values passed are 1-indexed
     */
    public int getBoardValue(int X, int Y) { return board[X-1][Y-1]; }

    /**
     * Values passed are 1-indexed
     */
    public void setBoardValue(int X, int Y, int value) { board[X-1][Y-1] = value; }
}
