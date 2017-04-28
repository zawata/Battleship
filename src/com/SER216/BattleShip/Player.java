package com.SER216.BattleShip;

import java.util.ArrayList;
import java.util.List;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/13/2017.
 */
public class Player {

    protected int[][] shipBoard = new int[boardWidth][boardWidth];
    protected int[][] shotBoard = new int[boardWidth][boardWidth];

    protected String name;

    //crude implementation of fixed Length Set
    protected List<Ship> shipFleet = new ArrayList<>();
    protected boolean addShip(Ship ship) {
        if(shipFleet.size() != 5 && !shipFleet.contains(ship)) {
            shipFleet.add(ship);
        }
        return false;
    }

    protected Player() {
        this.name = "Player";
        for(int i = 0; i < boardWidth; i++) {
            for(int j = 0; j < boardWidth; j++) {
                shipBoard[i][j] = MainGUI.TileColors.Blank.getValue();
                shotBoard[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }

    protected Player(String name) {
        this.name = name;
        for(int i = 0; i < boardWidth; i++) {
            for(int j = 0; j < boardWidth; j++) {
                shipBoard[i][j] = MainGUI.TileColors.Blank.getValue();
                shotBoard[i][j] = MainGUI.TileColors.Blank.getValue();
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
     *
     * @return
     * -1 for error
     * 0 for miss
     * 1 for hit
     * 2-5 for sunk
     */
    protected int receiveShot(int x, int y) {
        if(getShipBoardValue(x, y) == MainGUI.TileColors.Blank.getValue()) {
            //empty cell
            setShipBoardValue(x, y, MainGUI.TileColors.Grey.getValue());
            return 0;
        } else if(getShipBoardValue(x, y) == MainGUI.TileColors.Green.getValue()) {
            //Ship
            setShipBoardValue(x, y, MainGUI.TileColors.Red.getValue());
            for(Ship ship : shipFleet) {
                if (ship.Occupies(x, y)) {
                    ship.hit();
                    if(ship.isSunk()) {
                        if (ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                            for (int i = 0; i < ship.getSize(); i++) {
                                setShipBoardValue(ship.getX(), (i + ship.getY()), MainGUI.TileColors.Black.getValue());
                            }
                        } else {
                            if (ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                                for (int i = 0; i < ship.getSize(); i++) {
                                    setShipBoardValue((i + ship.getX()), ship.getY(), MainGUI.TileColors.Black.getValue());
                                }
                            }
                        }
                        return ship.getSize();
                    }
                }
            }
            return 1;
        } else {
            return -1;
        }
    }

    protected boolean validShot(int x, int y) {
        try {
            return getShotBoardValue(x, y) == MainGUI.TileColors.Blank.getValue();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public String getName() { return name; }

    /**
     * Values passed are 1-indexed
     */
    protected int getShipBoardValue(int x, int y) { return shipBoard[x-1][y-1]; }
    protected int getShotBoardValue(int x, int y) { return shotBoard[x-1][y-1]; }

    /**
     * Values passed are 1-indexed
     */
    protected void setShipBoardValue(int x, int y, int value) { shipBoard[x-1][y-1] = value; }
    protected void setShotBoardValue(int x, int y, int value) { shotBoard[x-1][y-1] = value; }
}
