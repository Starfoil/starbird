package main;

import java.util.Random;

public class StateZero implements State {
	public void perform(GameManager gameManager, GameInstance gameInstance) {
		Random RNG = new Random();
		for (Player p : gameInstance.players) {
			p.movetoX = RNG.nextInt(100) + 50;
			p.movetoY = RNG.nextInt(450) + 75;
		}
		gameInstance.gameStatus = 1;
		gameManager.setState(new StateOne());
		gameInstance.distance++;
	}
	
}