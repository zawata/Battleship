package com.SER216.Battleship;

import com.SER216.BattleShip.Player;
import com.SER216.BattleShip.Ship;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zawata on 4/27/2017.
 */
public class PlayerTest extends Player{
    @Test
    public void Test_addShip() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        this.addShip(ship);

        assertTrue(this.shipFleet.contains(ship));
        for(int i = 0; i < 5; i++) {
            this.addShip(new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal));
        }
        assertFalse(this.shipFleet.size() == 6);

        for(Ship.ShipType newship : Ship.ShipType.values()) {
            this.addShip(new Ship(newship, Ship.Direction.Horizontal));
        }
        assertTrue(this.shipFleet.size() == 5);
    }

    @Test
    public void Test_allShipsPlaced() throws Exception {
        for(Ship.ShipType newship : Ship.ShipType.values()) {
            Ship ship = new Ship(newship, Ship.Direction.Horizontal);
            ship.place(0,0);
            this.addShip(ship);
        }
        assertTrue(this.allShipsPlaced());
    }

    @Test
    public void Test_allShipsHit() throws Exception {
        for(Ship ship : shipFleet) {
            for(int i = 0; i < ship.getSize(); i++) {
                ship.hit();
            }
        }
        assertTrue(this.allShipsHit());
    }

    @Test
    public void Test_receiveShot() throws Exception {

    }

    @Test
    public void Test_validShot() throws Exception {
        assertFalse(this.validShot(0,0));
        assertFalse(this.validShot(11,10));
        assertTrue(this.validShot(5,7));
        assertTrue(this.validShot(3,10));

    }

    @Test
    public void Test_getName() throws Exception {
        assertEquals(this.getName(), "Player");
    }

    @Test
    public void Test_getShipBoardValue() throws Exception {

    }

    @Test
    public void Test_getShotBoardValue() throws Exception {

    }

    @Test
    public void Test_setShipBoardValue() throws Exception {

    }

    @Test
    public void Test_setShotBoardValue() throws Exception {

    }

}