package com.SER216.Battleship;

import com.SER216.BattleShip.Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by zawata on 4/27/2017.
 */
public class UtilTest {
    @Test
    public void getGrid() throws Exception {
        assertEquals(Util.getGrid(450), 10);
        assertEquals(Util.getGrid(137), 3);
        assertEquals(Util.getGrid(1000), 0);
        assertEquals(Util.getGrid(-1), 0);
    }

    @Test
    public void reverse() throws Exception {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Integer[] arr = {5,4,3,2,1};

        assertTrue(Arrays.equals(Util.reverse(stack).toArray(), arr));
    }

}