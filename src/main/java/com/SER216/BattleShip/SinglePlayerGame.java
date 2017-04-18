package com.SER216.BattleShip;

import java.io.IOException;

public class SinglePlayerGame{

	SinglePlayerGame() throws IOException{
		MainGUI mainGUI = new MainGUI();

		Human player1 = new Human();
		CPU cpu = new CPU();

		mainGUI.init(player1, cpu);

		new Thread().start();
	}
}
