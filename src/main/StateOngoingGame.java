package main;

import birds.Spawner;

public class StateOngoingGame extends StateMethods implements State  {
	private CollisionCheck collisionCheck = new CollisionCheck();

	@Override
	public void perform(GameManager gameManager, GameInstance gameInstance) {
		// TODO Auto-generated method stub
		boolean aliveflag = false;
		spawnEnemies(gameInstance);
		updateEnemies(gameInstance);
		updateBots(gameInstance);
		updateTargets(gameInstance);
		updateObjects(gameInstance);
		removeDeadMobs(gameInstance);
		removeDeadBots(gameInstance);
		
		collisionCheck.checkCollision(gameInstance);
		for (Player p : gameInstance.players) {
			if (p.health > 0)
				aliveflag = true;
		}
		boolean enemyflag = false;
		for (Spawner s : gameInstance.birdSpawn) {
			if (s.alive)
				enemyflag = true;
		}
		
		setStatus(gameManager, gameInstance, aliveflag, enemyflag);
		gameInstance.distance++;
	}

	private void setStatus(GameManager gameManager, GameInstance gameInstance, boolean aliveflag, boolean enemyflag) {
		if (!aliveflag && gameInstance.gameStatus == 2) {
			gameInstance.gameStatus = 3;
			gameManager.setState(new StateAllPlayersDead());
		} else if (!enemyflag && gameInstance.birds.isEmpty() && gameInstance.gameStatus == 2) {
			gameInstance.gameStatus = 4;
			gameManager.setState(new StateAllEnemiesDead());
		}
	}


}
