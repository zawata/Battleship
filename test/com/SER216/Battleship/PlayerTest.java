package com.SER216.Battleship;

import com.SER216.BattleShip.CPU;
import org.databene.contiperf.junit.ContiPerfRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.*;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.SER216.BattleShip.Human;
import com.SER216.BattleShip.Ship;

/**
 *
 * @author Nubian
 */
public class PlayerTest {
    @Rule
    public MockitoRule mockrule = MockitoJUnit.rule();
    @Rule
    public ContiPerfRule CPRule = new ContiPerfRule();
    @Mock
    private Ship ship = mock(Ship.class);
    
    private Human instance;
     
    public PlayerTest() throws IOException {
        this.instance = new Human("human");
        MockitoAnnotations.initMocks(this);
        

    }
    
    @BeforeClass
    public static void setUpClass() {        

    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    
    /**
     * Test of isRange method, of class Player.
     * @throws java.lang.Exception
     */
    /*
    @Test
    public void testPlayerPlacement() throws Exception {
        System.out.println("isRange");
        Ship ship2 = mock(Ship.class);
        ship.size = 2;
        ship2.size = 5;
        
        when(ship.getDirectionOfShip()).thenReturn("Vertical");
        when(ship.getY()).thenReturn(5);
        
        when(ship2.getDirectionOfShip()).thenReturn("Horizontal");
        when(ship2.getX()).thenReturn(4);
        
        assertEquals(true, instance.isRange(ship, 0, 5));
        verify(ship, times(1)).getDirectionOfShip();
        
        assertEquals(true, instance.isRange(ship2, 4, 0));
        verify(ship2, times(2)).getDirectionOfShip();
    }

    @Test
    public void testCPUPlacement() throws Exception {
        System.out.println("CPUPlacement");
        CPU cpu = mock(CPU.class);

        when(ship.getDirectionOfShip()).thenReturn("Vertical");
        when(ship.getY()).thenReturn(5);

        when(ship2.getDirectionOfShip()).thenReturn("Horizontal");
        when(ship2.getX()).thenReturn(4);

        assertEquals(true, instance.isRange(ship, 0, 5));
        verify(ship, times(1)).getDirectionOfShip();

        assertEquals(true, instance.isRange(ship2, 4, 0));
        verify(ship2, times(2)).getDirectionOfShip();
    }
    
    @Test
    public void testremoveOldShip() throws IOException{
        System.out.println("removeOldShip");        
        int CurrX = 0;
        
        instance.setShipName("Destroyer");
        ship.size = 3;
        when(ship.getDirectionOfShip()).thenReturn("Vertical");
        when(ship.getX()).thenReturn(0);
        int[][] board = instance.getBoard();
        
        //sets board spaces to 1
        while(CurrX < ship.size){
            board[ship.getX()][CurrX] = 1;
            CurrX++;
        }
        
        //sets board spaces to 0
        instance.removeOldShip();
        
        assertEquals(0, board[ship.getX()][9]);

    }
*/
}