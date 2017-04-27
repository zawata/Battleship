package com.SER216.BattleShip;

import java.io.IOException;

import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/18/2017.
 */
public class Human extends Player {

    public Human(String name) { super(name); }

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

    public boolean takeTurn(Player opponent, int x, int y) throws IOException {
        if (validShot(x, y)) {
            int retVal = opponent.receiveShot(x, y);
            switch (retVal) {
                case 0: //miss
                    System.out.println("player miss");
                    this.setShotBoardValue(x, y, MainGUI.TileColors.Grey.getValue());
                    return true;
                case 1: //hit
                    System.out.println("player hit");
                    setShotBoardValue(x, y, MainGUI.TileColors.Red.getValue());
                    return true;
                case 2: //sunk Patrol Boat
                case 3: //sunk Submarine or Destroyer
                case 4: //sunk Battleship
                case 5: //sunk Carrier
                    System.out.println("player Sunk size:" + retVal);
                    setShotBoardValue(x, y, MainGUI.TileColors.Red.getValue()); //if its not displaying then we don't need to mark it black
                    return true;
                default:
                    throw new IOException("Unhandled ReceiveShot Number");
            }
        } else {
            System.out.println("invalid shot: " + x + " " + y);
            return false;
        }
    }
}
