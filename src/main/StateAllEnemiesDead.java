package main;

public class StateAllEnemiesDead extends StateMethods implements State {
	private CollisionCheck collisionCheck = new CollisionCheck();

	@Override
	public void perform(GameManager gameManager, GameInstance gameInstance) {
		// TODO Auto-generated method stub

		spawnEnemies(gameInstance);
		updateEnemies(gameInstance);
		updateBots(gameInstance);
		updateTargets(gameInstance);
		updateObjects(gameInstance);
		
		// cleanDeadObjects
		removeDeadMobs(gameInstance);
		removeDeadBots(gameInstance);

		collisionCheck.checkDropCoinCollision(gameInstance);
		setStatus(gameInstance);
		gameInstance.distance++;
	}

	private void setStatus(GameInstance gameInstance) {
		gameInstance.gameEndTimer += 1;

		if ((gameInstance.gameStatus == 3 || gameInstance.gameStatus == 4) && gameInstance.gameEndTimer > 500) {
			gameInstance.gameStatus = -1;
		}
	}

}
