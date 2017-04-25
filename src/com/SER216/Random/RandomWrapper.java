package com.SER216.Random;

import java.util.Random;

/**
 * Created by zawata on 4/18/2017.
 */
public class RandomWrapper implements IRandomWrapper {

    private Random random;

    public RandomWrapper() {
        random = new Random();
    }

    public int nextInt(int n) {
        return random.nextInt(n);
    }
}
