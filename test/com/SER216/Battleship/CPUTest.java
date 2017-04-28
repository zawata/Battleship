package com.SER216.Battleship;

import com.SER216.BattleShip.CPU;
import com.SER216.BattleShip.MainGUI;
import com.SER216.BattleShip.Ship;
import org.junit.Test;

import static com.SER216.BattleShip.Util.boardWidth;
import static org.junit.Assert.*;

/**
 * Created by zawata on 4/27/2017.
 */
public class CPUTest extends CPU{

    //private CPU cpuTest = new CPU("cpuTest");

    public CPUTest() {

    }

    @Test
    public void testPlaceShips() {
        this.placeShips();

        for(int i = 0; i < boardWidth; i++) {
            for(int j = 0; j < boardWidth; j++) {
                System.out.print(shipBoard[i][j]);
            }
            System.out.println();
        }

        for(Ship topShip : shipFleet) {
            assertTrue(topShip.isPlaced());
            for (Ship otherShip : shipFleet){
                if(!topShip.equals(otherShip)) {
                    if (topShip.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                        if (topShip.getX() < (boardWidth - topShip.getSize())) {
                            for (int i = 0; i < topShip.getSize(); i++) {
                                System.out.println("H Comparing " + (i + topShip.getX()) + " and " + topShip.getY());
                                assertFalse(otherShip.Occupies((i + topShip.getX()), topShip.getY()));
                            }
                        }
                    } else {
                        if (topShip.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                            if (topShip.getY() < (boardWidth - topShip.getSize())) {
                                for (int i = 0; i < topShip.getSize(); i++) {
                                    System.out.println("V Comparing " + topShip.getX() + " and " + (i + topShip.getY()));
                                    assertFalse(otherShip.Occupies(topShip.getX(), (i + topShip.getY())));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void fire() throws Exception {

    }

}