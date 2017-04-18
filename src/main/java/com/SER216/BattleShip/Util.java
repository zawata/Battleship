package com.SER216.BattleShip;

/**
 * Created by zawata on 4/12/2017.
 */
public class Util {
    //width of the board
    public static final int boardWidth = 10;

    // resource location
    public static final String resources = "src/main/resources/";

    // Returns the value of the game coordinates given pixels of the click
    public static int getGrid(int x) {
        // integer division in java is, by implementation, to the floor.
        int retval = x / 45;
        if(retval > 0 && retval <= 10) {
            return retval;
        }
        else {
            return 0;
        }
    }

    // Returns the value of the pixel coordinates given pixels of the click
    public static int getGridPixel(int x) {
        int retval = x * 45;
        if(x > 0 && x <= 10) {
            return retval;
        }
        else {
            return 0;
        }
    }
}
