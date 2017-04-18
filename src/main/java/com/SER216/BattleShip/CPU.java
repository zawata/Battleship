package com.SER216.BattleShip;

import java.util.Random;
import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/12/2017.
 */
public class CPU extends Player {
    private final Random rand = new Random();

    private String name;

    // CPU AI values
    private final boolean[] cpuCompass = new boolean[4];
    private int orientation = 0;
    private boolean hunting = false;
    private String lastShip = "";
    private int nextShip = 0;
    private int cpuStartHit_x;
    private int cpuStartHit_y;
    private int cpuLastHit_x;
    private int cpuLastHit_y;

    public CPU() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void placeShips() {
        int direction;
        int x, y;

        Ship ship;

        for (Ship.ShipType shiptype : Ship.ShipType.values()) {

            //attempt to add a ship until it succeeds
            while (true) {
                direction = rand.nextInt(2);

                if (direction == Ship.Direction.Vertical.getValue()) {
                    x = rand.nextInt(boardWidth) + 1;
                    y = rand.nextInt(boardWidth - shiptype.getSize()) + 1;

                    ship = new Ship(shiptype, Ship.Direction.Vertical);

                    //if the ship intersect with another then start over
                    for (int i = 0; i < ship.getSize(); i++) {
                        if (getBoardValue(x, (i + y)) != MainGUI.TileColors.Blank.getValue())
                            continue;
                    }

                    //place ship if things are good
                    ship.place(x, y);
                    addShip(ship);
                    for (int i = 0; i < ship.getSize(); i++) {
                        setBoardValue(x, (i + y), MainGUI.TileColors.Green.getValue());
                    }
                    break;
                } else {
                    if (direction == Ship.Direction.Horizontal.getValue()) {
                        x = rand.nextInt(boardWidth - shiptype.getSize()) + 1;
                        y = rand.nextInt(boardWidth) + 1;

                        ship = new Ship(shiptype, Ship.Direction.Horizontal);

                        //if the ship intersect with another then start over
                        for (int i = 0; i < ship.getSize(); i++) {
                            if (getBoardValue((i + x), y) != MainGUI.TileColors.Blank.getValue())
                                continue;
                        }

                        //place ship if things are good
                        ship.place(x, y);
                        addShip(ship);
                        for (int i = 0; i < ship.getSize(); i++) {
                            setBoardValue((i + x), y, MainGUI.TileColors.Green.getValue());
                        }
                        break;
                    }
                }
            }
        }
    }
/*
    //TODO Need to look here at AI's
    //http://stackoverflow.com/questions/1631414/what-is-the-best-battleship-ai
    public boolean fire() {
        // Finds a random number in the range
        int x = rand.nextInt(boardWidth) + 1;
        int y = rand.nextInt(boardWidth) + 1;

        // If the last shot was a miss then the current shot is fired randomly
        if (getBoardValue(x, y) == 0 && !hunting && nextShip == 0) {
            // If the shot is a hit then it sets the next shot to be searching
            if (cpuTakeShot(x, y)) {
                cpuStartHit_x = x;
                cpuStartHit_y = y;
                sameShip(x, y);
                hunting = true;
            }
            return true;
        }
        else if (hunting) {
            // Finding the direction of the ship starting with up and going clockwise
            if (orientation <= 0) {
                for (int i = 0; i < cpuCompass.length; i++) {
                    if (!cpuCompass[i]) {
                        cpuCompass[i] = true;
                        if (i == 0) {
                            if (cpuStartHit_y - 1 > -1 && getBoardValue(cpuStartHit_x, cpuStartHit_y - 1) == 0) {
                                //checks to see if the shot is on the board and not already shot
                                if (cpuTakeShot(cpuStartHit_x, cpuStartHit_y - 1)) {
                                    // If the shot is on the same ship the direction is found
                                    if (sameShip(cpuStartHit_x, cpuStartHit_y - 1)) {
                                        orientation = 1;
                                        cpuLastHit_x = cpuStartHit_x;
                                        cpuLastHit_y = cpuStartHit_y - 1;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 1) {
                            if (cpuStartHit_x + 1 < 10 && getBoardValue(cpuStartHit_x + 1, cpuStartHit_y) == 0) {
                                if (cpuTakeShot(cpuStartHit_x + 1, cpuStartHit_y)) {
                                    if (sameShip(cpuStartHit_x + 1, cpuStartHit_y)) {
                                        orientation = 2;
                                        cpuLastHit_x = cpuStartHit_x + 1;
                                        cpuLastHit_y = cpuStartHit_y;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 2) {
                            if (cpuStartHit_y + 1 < 10 && getBoardValue(cpuStartHit_x, cpuStartHit_y + 1) == 0) {
                                if (cpuTakeShot(cpuStartHit_x, cpuStartHit_y + 1)) {
                                    if (sameShip(cpuStartHit_x, cpuStartHit_y + 1)) {
                                        orientation = 3;
                                        cpuLastHit_x = cpuStartHit_x;
                                        cpuLastHit_y = cpuStartHit_y + 1;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 3) {
                            if (cpuStartHit_x - 1 > -1 && getBoardValue(cpuStartHit_x - 1, cpuStartHit_y) == 0) {
                                if (cpuTakeShot(cpuStartHit_x - 1, cpuStartHit_y)) {
                                    if (sameShip(cpuStartHit_x - 1, cpuStartHit_y)) {
                                        orientation = 4;
                                        cpuLastHit_x = cpuStartHit_x - 1;
                                        cpuLastHit_y = cpuStartHit_y;
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            } else if (orientation > 0) {
                // When the direction of the ship is known The boat shot until it sinks
                if (orientation == 1) {
                    if (cpuLastHit_y - 1 > -1 && getBoardValue(cpuStartHit_x, cpuStartHit_y - 1) == 0) {
                        if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y - 1)) {
                            if (sameShip(cpuLastHit_x, cpuLastHit_y - 1)) {
                                cpuLastHit_y = cpuLastHit_y - 1;
                                return true;
                            }
                        } else { // If the shot was a miss or off the board the direction is changed
                            orientation = 3;
                            cpuLastHit_y = cpuStartHit_y;
                            return true;
                        }
                    } else {
                        orientation = 3;
                        cpuLastHit_y = cpuStartHit_y;
                        return false;
                    }
                } else if (orientation == 2) {
                    if (cpuLastHit_x + 1 < 10 && getBoardValue(cpuStartHit_x + 1, cpuStartHit_y) == 0) {
                        if (cpuTakeShot(cpuLastHit_x + 1, cpuLastHit_y)) {
                            if (sameShip(cpuLastHit_x + 1, cpuLastHit_y)) {
                                cpuLastHit_x = cpuLastHit_x + 1;
                                return true;
                            }
                        } else {
                            orientation = 4;
                            cpuLastHit_x = cpuStartHit_x;
                            return true;
                        }
                    } else {
                        orientation = 4;
                        cpuLastHit_x = cpuStartHit_x;
                        return false;
                    }
                } else if (orientation == 3) {
                    if (cpuLastHit_y + 1 < 10 && getBoardValue(cpuStartHit_x, cpuStartHit_y + 1) == 0) {
                        if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y + 1)) {
                            if (sameShip(cpuLastHit_x, cpuLastHit_y + 1)) {
                                cpuLastHit_y = cpuLastHit_y + 1;
                                return true;
                            }
                        } else {
                            orientation = 1;
                            cpuLastHit_y = cpuStartHit_y;
                            return true;
                        }
                    } else {
                        orientation = 1;
                        cpuLastHit_y = cpuStartHit_y;
                        return false;
                    }
                } else if (orientation == 4) {
                    if (cpuLastHit_x - 1 > -1
                            && getBoardValue(cpuStartHit_x - 1, cpuStartHit_y) == 0) {
                        if (cpuTakeShot(cpuLastHit_x - 1, cpuLastHit_y)) {
                            if (sameShip(cpuLastHit_x - 1, cpuLastHit_y)) {
                                cpuLastHit_x = cpuLastHit_x - 1;
                                return true;
                            }
                        } else {
                            orientation = 2;
                            cpuLastHit_x = cpuStartHit_x;
                            return true;
                        }
                    } else {
                        orientation = 2;
                        cpuLastHit_x = cpuStartHit_x;
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }
    */
}
