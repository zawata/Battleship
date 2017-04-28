package com.SER216.Battleship;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.*;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.SER216.BattleShip.Ship;


/**
 * Created by zawata on 4/27/2017.
 */
public class ShipTest {
    @Rule
    public MockitoRule mockrule = MockitoJUnit.rule();
    @Rule
    public ContiPerfRule CPRule = new ContiPerfRule();
    @Mock
    private Ship ship = mock(Ship.class);

    //private Human instance;

    public ShipTest() throws IOException {
        //this.instance = new Human("human");
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getShipValuebyName() throws Exception {
        assertEquals(Ship.getShipValuebyName("Aircraft Carrier"), Ship.ShipType.AircraftCarrier);
        assertEquals(Ship.getShipValuebyName("Battleship"), Ship.ShipType.Battleship);
        assertEquals(Ship.getShipValuebyName("Destroyer"), Ship.ShipType.Destroyer);
        assertEquals(Ship.getShipValuebyName("Submarine"), Ship.ShipType.Submarine);
        assertEquals(Ship.getShipValuebyName("Patrol Boat"), Ship.ShipType.PatrolBoat);
    }

    @Test
    public void getShipNames() throws Exception {
        ArrayList<String> values = new ArrayList<>(Arrays.asList(Ship.getShipNames()));
        assertTrue(values.size() == 5);
        assertTrue(values.contains("Aircraft Carrier"));
        assertTrue(values.contains("Battleship"));
        assertTrue(values.contains("Destroyer"));
        assertTrue(values.contains("Submarine"));
        assertTrue(values.contains("Patrol Boat"));
    }

    @Test
    public void getDirectionValuebyName() throws Exception {
        assertEquals(Ship.getDirectionValuebyName("Horizontal"), Ship.Direction.Horizontal);
        assertEquals(Ship.getDirectionValuebyName("Vertical"), Ship.Direction.Vertical);
    }

    @Test
    public void getDirectionNames() throws Exception {
        ArrayList<String> values = new ArrayList<>(Arrays.asList(Ship.getDirectionNames()));
        assertTrue(values.size() == 2);
        assertTrue(values.contains("Horizontal"));
        assertTrue(values.contains("Vertical"));
    }

    @Test
    public void getShipType() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        assertTrue(ship.getShipType().equals(Ship.ShipType.AircraftCarrier));
    }

    @Test
    public void getDirectionOfShip() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        assertTrue(ship.getDirectionOfShip().equals(Ship.Direction.Horizontal));
    }

    @Test
    public void getX() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(6,9);

        assertTrue(ship.getX() == 6);
    }

    @Test
    public void getY() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(6,9);

        assertTrue(ship.getY() == 9);
    }

    @Test
    public void getSize() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        assertTrue(ship.getSize() == 5);
    }

    @Test
    public void getHits() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.hit();

        assertTrue(ship.getHits() == 1);
    }

    @Test
    public void hit() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.hit();

        assertTrue(ship.getHits() == 1);
    }

    @Test
    public void isPlaced() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(6, 7);

        assertTrue(ship.isPlaced());
    }

    @Test
    public void isSunk() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.hit();
        ship.hit();
        ship.hit();
        ship.hit();
        ship.hit();

        assertTrue(ship.isSunk());
    }

    @Test
    public void place() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(1,1);

        assertTrue( ship.isPlaced() );
        assertTrue( ship.getX() == 1 );
        assertTrue( ship.getY() == 1 );
    }

    @Test
    public void remove() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(1,1);
        ship.remove();

        assertFalse( ship.isPlaced() );
        assertFalse( ship.getX() == 1 );
        assertFalse( ship.getY() == 1 );
    }

    @Test
    public void occupies() throws Exception {
        Ship ship = new Ship(Ship.ShipType.AircraftCarrier, Ship.Direction.Horizontal);
        ship.place(1,1);

        assertFalse(ship.Occupies(1,2));
        assertTrue(ship.Occupies(2,1));
    }

}