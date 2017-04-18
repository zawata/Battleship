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
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
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

    /**
     * Values passed are 1-indexed
     */
    protected int getBoardValue(int X, int Y) { return board[X-1][Y-1]; }

    /**
     * Values passed are 1-indexed
     */
    protected void setBoardValue(int X, int Y, int value) { board[X-1][Y-1] = value; }
}
