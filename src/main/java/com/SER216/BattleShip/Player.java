package com.SER216.BattleShip;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/13/2017.
 */
public class Player {

    private int[][] board = new int[boardWidth][boardWidth];

    private Ship[] shipFleet = new Ship[5];

    public Player() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }

    public boolean addShip(Ship ship, int x, int y) {
        if(ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
            if (y <= ((boardWidth + 1) - ship.getSize()) && !ship.isPlaced()) {
                ship.place(x , y );
                for (int i = 0; i < ship.getSize(); i++) {
                    setBoardValue(x, (i + y), MainGUI.TileColors.Green.getValue());
                }
                return true;
            }
        }
        else {
            if(ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                if (x <= ((boardWidth + 1) - ship.getSize()) && !ship.isPlaced()) {
                    ship.place(x , y );
                    for (int i = 0; i < ship.getSize(); i++) {
                        setBoardValue((i + x), y, MainGUI.TileColors.Green.getValue());
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean allShipsPlaced() {
        for(Ship ship : shipFleet) {
            if(!ship.isPlaced()) {
                return false;
            }
        }
        return true;
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
