package battleship;

import java.io.IOException;

public class SinglePlayerGame{
	
	SinglePlayerGame() throws IOException{
		
		Player player1 = new Player("player1");
		Player cpu = new Player("cpu");
		
		player1.setOpponent(cpu);
		cpu.setOpponent(player1);
		
		cpu.cpuShips();
		cpu.setCompass();
		player1.mainGUI();
		
		new Thread().start();
		
		
		
	}

}
