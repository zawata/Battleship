package com.SER216.BattleShip;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/18/2017.
 */
public class Human extends Player {

    public Human() {
        super();
    }

    @SuppressWarnings("Duplicates")
    public boolean addShip(Ship ship, int x, int y) {
        //remove old Ships to place new ones
        removeShip(ship.getShipType());

        if(!ship.isPlaced()) {
            if (ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                if (y <= ((boardWidth + 1) - ship.getSize())) {
                    for (int i = 0; i < ship.getSize(); i++) {
                        if(getShipBoardValue(x, (i + y)) != MainGUI.TileColors.Blank.getValue())
                            return false;
                    }
                    ship.place(x, y);
                    addShip(ship);
                    for (int i = 0; i < ship.getSize(); i++) {
                        setShipBoardValue(x, (i + y), MainGUI.TileColors.Green.getValue());
                    }
                    return true;
                }
            } else {
                if (ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                    if (x <= ((boardWidth + 1) - ship.getSize())) {
                        for (int i = 0; i < ship.getSize(); i++) {
                            if(getShipBoardValue((i + x), y) != MainGUI.TileColors.Blank.getValue())
                                return false;
                        }
                        ship.place(x, y);
                        addShip(ship);
                        for (int i = 0; i < ship.getSize(); i++) {
                            setShipBoardValue((i + x), y, MainGUI.TileColors.Green.getValue());
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
                    setShipBoardValue(oldShip.getX(), (i + oldShip.getY()), MainGUI.TileColors.Blank.getValue());
                }
                oldShip.remove();
                return true;
            } else {
                if (oldShip.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                    shipFleet.remove(oldShip);
                    for (int i = 0; i < oldShip.getSize(); i++) {
                        setShipBoardValue((i + oldShip.getX()), oldShip.getY(), MainGUI.TileColors.Blank.getValue());
                    }
                    oldShip.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validShot(int x, int y) {
        if(getShipBoardValue(x, y) == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkShot() {
        return false;
    }

    public void turn() {}
}
