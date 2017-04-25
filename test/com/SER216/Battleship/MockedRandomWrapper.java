package com.SER216.Battleship;

import com.SER216.Random.IRandomWrapper;

/**
 * Created by zawata on 4/18/2017.
 */
public class MockedRandomWrapper implements IRandomWrapper {

    private int theInt;

    public MockedRandomWrapper(int theInt) {
        this.theInt = theInt;
    }

    public int nextInt(int n) {
        return theInt;
    }

}