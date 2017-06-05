package main;

public class StateMovingPlayerintoPosition implements State {
	@Override
	public void perform(GameManager gameManager, GameInstance gameInstance) {
		boolean playflag = true;
		for (Player p : gameInstance.players) {
			p.moveTo(p.movetoX, p.movetoY, 3);
		}
		for (Player p : gameInstance.players) {
			if (!p.playable)
				playflag = false;
		}
		if (playflag && gameInstance.gameStatus == 1) {
			gameInstance.gameStatus = 2;
			gameManager.setState(new StateOngoingGame());
		}
		gameInstance.distance++;
	}
}
