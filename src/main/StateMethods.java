package main;

import java.util.ArrayList;

import birds.EnemyEntity;

public class StateMethods {

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
}
