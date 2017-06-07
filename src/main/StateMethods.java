package main;

import java.util.ArrayList;

import birds.EnemyEntity;
import birds.EnemyUnit;
import birds.Spawner;

public abstract class StateMethods {

	protected EnemyEntity botGetTarget(Birdbot bot, GameInstance gameInstance) {
		if (gameInstance.birds.isEmpty())
			return null;
		else if (bot.targetCooldown < 100) {
			return bot.target;
		} else {
			EnemyEntity target = null;
			for (EnemyEntity BE : gameInstance.birds) {
				if (!checkIfTargeted(BE, gameInstance) && (BE.xpos > bot.xpos) && BE.xpos < 1200
						&& BE.targetPriority > 0 && !BE.isDead()) {
					if (target == null) {
						target = BE;
					} else if (BE.targetPriority * botGetDistance(bot, BE) < target.targetPriority
							* botGetDistance(bot, target)) {
						target = BE;
					}
					bot.targetCooldown = 0;
				}
			}
			return target;
		}
	}

	private boolean checkIfTargeted(EnemyEntity enemy, GameInstance gameInstance) {
		ArrayList<EnemyEntity> targets = new ArrayList<EnemyEntity>();
		for (Birdbot BB : gameInstance.bots) {
			if (BB.target != null) {
				targets.add(BB.target);
			}
		}
		return targets.contains(enemy);
	}

	private double botGetDistance(Birdbot bot, EnemyEntity en) {
		int xposD = Math.abs(bot.xpos - en.xpos);
		int yposD = Math.abs(bot.ypos - en.ypos);
		double dist = Math.sqrt(xposD ^ 2 + yposD ^ 2);
		return dist;
	}

	protected Player enemyGetTarget(EnemyEntity bird, GameInstance gameInstance) {
		if (gameInstance.players.isEmpty())
			return null;
		else {
			Player target = null;
			for (Player p : gameInstance.players) {
				if ((bird.xpos > p.xpos) && (bird.xpos - p.xpos < bird.targetRange)) {
					if (target == null) {
						target = p;
					} else if (enemyGetDistance(p, bird) < enemyGetDistance(p, bird)) {
						target = p;
					}
				}
			}
			return target;
		}
	}

	private double enemyGetDistance(Player p, EnemyEntity en) {
		int xposD = Math.abs(p.xpos - en.xpos);
		int yposD = Math.abs(p.ypos - en.ypos);
		double dist = Math.sqrt(xposD ^ 2 + yposD ^ 2);
		return dist;
	}

	protected void removeDeadBots(GameInstance gameInstance) {
		ArrayList<Birdbot> botRemoveList = new ArrayList<Birdbot>();
		for (Birdbot b : gameInstance.bots) {
			if (b.health == 0) {
				botRemoveList.add(b);
			}
		}
		gameInstance.bots.removeAll(botRemoveList);
	}

	protected void removeDeadMobs(GameInstance gameInstance) {
			ArrayList<EnemyEntity> enemyRemoveList = new ArrayList<EnemyEntity>();
			for (EnemyEntity e : gameInstance.birds) {
				if (e.xpos < -200 || e.ypos > 700 || e.isDead()) {
					enemyRemoveList.add(e);
				}
			}
	gameInstance.birds.removeAll(enemyRemoveList);
		}

	protected void updateObjects(GameInstance gameInstance) {
		for (CoinDrop d : gameInstance.drops) {
			d.update();
		}
	}

	protected void updateTargets(GameInstance gameInstance) {
		for (Birdbot b : gameInstance.bots) {
			b.target = (EnemyUnit) botGetTarget(b, gameInstance);
		}
		for (EnemyEntity e : gameInstance.birds) {
			e.playerTarget = enemyGetTarget(e, gameInstance);
		}
	}

	protected void updateBots(GameInstance gameInstance) {
		for (Birdbot b : gameInstance.bots) {
			b.update();
		}
	}

	protected void updateEnemies(GameInstance gameInstance) {
		for (EnemyEntity b : gameInstance.birds) {
			b.update();
		}
	}

	protected void spawnEnemies(GameInstance gameInstance) {
		for (Spawner s : gameInstance.birdSpawn) {
			if (s.alive)
				s.spawn();
		}
	}
}
