package com.SER216.BattleShip;

import java.util.ArrayList;
import java.util.List;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/13/2017.
 */
public class Player {

    protected int[][] board = new int[boardWidth][boardWidth];

    //crude implementation of fixed Length Set
    protected List<Ship> shipFleet = new ArrayList<>();
    protected boolean addShip(Ship ship) {
        if(shipFleet.size() != 5 && !shipFleet.contains(ship)) {
            shipFleet.add(ship);
        }
        return false;
    }

    protected Player() {
        for(int i = 0; i < boardWidth; i++) {
            for(int j = 0; j < boardWidth; j++) {
                board[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }

    protected boolean allShipsPlaced() {
        for(Ship ship : shipFleet) {
            if (!ship.isPlaced()) {
                return false;
            }
        }
        return shipFleet.size() == 5;
    }

    protected boolean allShipsHit() {
        for(Ship ship : shipFleet) {
            if (ship.getSize() != ship.getHits()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Values passed are 1-indexed
     */
    protected boolean recieveShot(int x, int y) {
        if(getBoardValue(x, y) == MainGUI.TileColors.Blank.getValue()) {
            //empty cell
            setBoardValue(x, y, MainGUI.TileColors.Grey.getValue());
            return true;
        } else if(getBoardValue(x, y) == MainGUI.TileColors.Green.getValue()) {
            //Ship
            setBoardValue(x, y, MainGUI.TileColors.Red.getValue());
            for(Ship ship : shipFleet) {
                if (ship.Occupies(x, y)) {
                    ship.hit();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Values passed are 1-indexed
     */
    protected int getBoardValue(int x, int y) { return board[x-1][y-1]; }

    /**
     * Values passed are 1-indexed
     */
    protected void setBoardValue(int x, int y, int value) { board[x-1][y-1] = value; }
}
