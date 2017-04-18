package com.SER216.BattleShip;

import java.util.Random;
import static com.SER216.BattleShip.Util.boardWidth;

/**
 * Created by zawata on 4/12/2017.
 */
public class CPU {
    private final Random rand = new Random();

    private String name;

    // CPU values
    private final boolean[] cpuCompass = new boolean[4];
    private int orientation = 0;
    private boolean hunting = false;
    private String lastShip = "";
    private int nextShip = 0;
    private int cpuStartHit_x;
    private int cpuStartHit_y;
    private int cpuLastHit_x;
    private int cpuLastHit_y;

    private int[][] board = new int[boardWidth][boardWidth];

    private Ship[] shipFleet = new Ship[5];

    public CPU() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = MainGUI.TileColors.Blank.getValue();
            }
        }
    }
/*
    public boolean fire() {
        // Finds a random number in the range
        int x = random.nextInt(10);
        int y = random.nextInt(10);

        // If the last shot was a miss then the current shot is fired randomly
        if (gameBoard[x][y] == 0 && !hunting && nextShip == 0) {
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
                            if (cpuStartHit_y - 1 > -1
                                    && gameBoard[cpuStartHit_x][cpuStartHit_y - 1] == 0) {
                                //checks to see if the shot is on the board and not already shot
                                if (cpuTakeShot(cpuStartHit_x,
                                        cpuStartHit_y - 1)) {
                                    // If the shot is on the same ship the direction is found
                                    if (sameShip(cpuStartHit_x,
                                            cpuStartHit_y - 1)) {
                                        orientation = 1;
                                        cpuLastHit_x = cpuStartHit_x;
                                        cpuLastHit_y = cpuStartHit_y - 1;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 1) {
                            if (cpuStartHit_x + 1 < 10
                                    && gameBoard[cpuStartHit_x + 1][cpuStartHit_y] == 0) {
                                if (cpuTakeShot(cpuStartHit_x + 1,
                                        cpuStartHit_y)) {
                                    if (sameShip(cpuStartHit_x + 1,
                                            cpuStartHit_y)) {
                                        orientation = 2;
                                        cpuLastHit_x = cpuStartHit_x + 1;
                                        cpuLastHit_y = cpuStartHit_y;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 2) {
                            if (cpuStartHit_y + 1 < 10
                                    && gameBoard[cpuStartHit_x][cpuStartHit_y + 1] == 0) {
                                if (cpuTakeShot(cpuStartHit_x,
                                        cpuStartHit_y + 1)) {
                                    if (sameShip(cpuStartHit_x,
                                            cpuStartHit_y + 1)) {
                                        orientation = 3;
                                        cpuLastHit_x = cpuStartHit_x;
                                        cpuLastHit_y = cpuStartHit_y + 1;
                                    }
                                }
                                return true;
                            }
                        } else if (i == 3) {
                            if (cpuStartHit_x - 1 > -1
                                    && gameBoard[cpuStartHit_x - 1][cpuStartHit_y] == 0) {
                                if (cpuTakeShot(cpuStartHit_x - 1,
                                        cpuStartHit_y)) {
                                    if (sameShip(cpuStartHit_x - 1,
                                            cpuStartHit_y)) {
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
                    if (cpuLastHit_y - 1 > -1
                            && gameBoard[cpuLastHit_x][cpuLastHit_y - 1] == 0) {
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
                    if (cpuLastHit_x + 1 < 10
                            && gameBoard[cpuLastHit_x + 1][cpuLastHit_y] == 0) {
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
                    if (cpuLastHit_y + 1 < 10
                            && gameBoard[cpuLastHit_x][cpuLastHit_y + 1] == 0) {
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
                            && gameBoard[cpuLastHit_x - 1][cpuLastHit_y] == 0) {
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

    public boolean checkWin() {
        return false;
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
