package main;

import java.util.ArrayList;

import birds.EnemyEntity;
import birds.EnemyUnit;
import birds.Spawner;

public class StateOngoingGame extends StateMethods implements State  {
	private CollisionCheck collisionCheck = new CollisionCheck();

	@Override
	public void perform(GameManager gameManager, GameInstance gameInstance) {
		// TODO Auto-generated method stub
		boolean aliveflag = false;
		// spawnEnemies
		for (Spawner s : gameInstance.birdSpawn) {
			if (s.alive)
				s.spawn();
		}
		// updateEnemies
		for (EnemyEntity b : gameInstance.birds) {
			b.update();
		}
		// updateBots
		for (Birdbot b : gameInstance.bots) {
			b.update();
		}
		// updateTargets
		for (Birdbot b : gameInstance.bots) {
			b.target = (EnemyUnit) botGetTarget(b, gameInstance);
		}
		for (EnemyEntity e : gameInstance.birds) {
			e.playerTarget = enemyGetTarget(e, gameInstance);
		}
		// updateObjects
		for (CoinDrop d : gameInstance.drops) {
			d.update();
		}
		// cleanDeadObjects
		// Remove dead mobs
		ArrayList<EnemyEntity> enemyRemoveList = new ArrayList<EnemyEntity>();
		for (EnemyEntity e : gameInstance.birds) {
			if (e.xpos < -200 || e.ypos > 700 || e.isDead()) {
				enemyRemoveList.add(e);
			}
		}
		gameInstance.birds.removeAll(enemyRemoveList);

		// Remove dead bots
		ArrayList<Birdbot> botRemoveList = new ArrayList<Birdbot>();
		for (Birdbot b : gameInstance.bots) {
			if (b.health == 0) {
				botRemoveList.add(b);
			}
		}
		gameInstance.bots.removeAll(botRemoveList);
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
		if (!aliveflag && gameInstance.gameStatus == 2) {
			gameInstance.gameStatus = 3;
			gameManager.setState(new StateAllPlayersDead());
		} else if (!enemyflag && gameInstance.birds.isEmpty() && gameInstance.gameStatus == 2) {
			gameInstance.gameStatus = 4;
			gameManager.setState(new StateAllEnemiesDead());
		}
		gameInstance.distance++;
	}


}
