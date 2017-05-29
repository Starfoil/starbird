package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mainGUI.MainMenu;
import mainGUI.PlayerData;
import birds.Boss;
import birds.EnemyEntity;
import birds.EnemyUnit;
import birds.Spawner;

public class GameManager {

	private GameInstance game;


	public GameManager(GameInstance gameInstance){
		game = gameInstance;
	}

	public void update(){
		spawnEnemies();
		updateEnemies();
		updateBots();
		updateTargets();
		updateObjects();
		cleanDeadObjects();
	}
	
	private void spawnEnemies(){
		for (Spawner s : game.birdSpawn){
			if (s.alive)	s.spawn();
		}
	}

	public void checkCollision(){
		checkBulletCollision();
		checkMobCollision();
		checkDropCollision();
		checkPlayerDropCollision();
	}

	private void updateStatus(){
		boolean playflag = true;
		boolean aliveflag = false;
		for (Player p : game.players){
			if (!p.playable) playflag = false;
			if (p.health > 0) aliveflag = true;
		}
		boolean enemyflag = false;
		for (Spawner s : game.birdSpawn){
			if (s.alive) enemyflag = true;
		}
		if 		(game.gameStatus == 0)							game.gameStatus = 1;
		else if (playflag && game.gameStatus == 1)				game.gameStatus = 2;
		else if (!aliveflag && game.gameStatus == 2) 			game.gameStatus = 3;
		else if (!enemyflag && game.birds.isEmpty() && game.gameStatus == 2)			game.gameStatus = 4;	
		else if ((game.gameStatus == 3 || game.gameStatus == 4) && game.gameEndTimer > 500){
			game.gameStatus = -1;
		}
	}

	public void performStatus(){
		if (game.gameStatus == 0){
			Random RNG = new Random();
			for (Player p : game.players){
				p.movetoX = RNG.nextInt(100) + 50;
				p.movetoY = RNG.nextInt(450) + 75;
			}
		}
		// Moving players into position
		else if(game.gameStatus == 1){
			for (Player p : game.players)	p.moveTo(p.movetoX, p.movetoY, 3);
		}
		// Ongoing game
		else if(game.gameStatus == 2){
			update();
			checkCollision();
		}
		// All players are dead
		else if(game.gameStatus == 3){
			update();
			checkCollision();
			game.gameEndTimer += 1;
		}
		// All enemies are dead
		else if(game.gameStatus == 4){
			update();
			checkDropCollision();
			game.gameEndTimer += 1;
		}
		updateStatus();
		game.distance++;
	}

	private void updateBots(){
		for (Birdbot b : game.bots){
			b.update();
		}
	}

	private void updateEnemies(){
		for (EnemyEntity b : game.birds){
			b.update();
		}
	}

	private void updateObjects(){
		for (CoinDrop d: game.drops){
			d.update();
		}
	}

	private void checkBulletCollision(){
		for (EnemyEntity e : game.birds){
			// Check player
			for (Player p : game.players){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : p.bullets){
					if(b.hitbox.intersects(e.hitbox) && p.skin.fspeed != 0){
						e.loseHealth(b.damage);
						if(!b.piercing)	{
							bulletRemoveList.add(b);
						}
					}
				}
				p.bullets.removeAll(bulletRemoveList);
				if (e instanceof Boss){
					ArrayList<EBullet> ebulletRemoveList = new ArrayList<EBullet>();
					for (EBullet b : e.bullets){
						if(b.hitbox.intersects(p.hitbox)){
							p.updateHealth(b.damage);
							if(!b.piercing)	ebulletRemoveList.add(b);
						}
					}
					e.bullets.removeAll(ebulletRemoveList);
				}	
			}
			// Check NPCs
			for (Birdbot bb : game.bots){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : bb.bullets){
					if(b.hitbox.intersects(e.hitbox)){
						e.loseHealth(b.damage);
						if(!b.piercing)	bulletRemoveList.add(b);
					}
				}
				bb.bullets.removeAll(bulletRemoveList);
			}	
			if(e.isDead()) {
				if (e.dropAmount > 0 || e.dropMinAmount > 0) dropCoin(e);
				game.score += e.scoreValue;
			}
		}
	}

	private void checkMobCollision(){
		ArrayList<EnemyEntity> enemyRemoveList = new ArrayList<EnemyEntity>();
		for (EnemyEntity e : game.birds){
			for (int i = 0; i < game.players.size(); i++){
				Player p = game.players.get(i);
				if(p.hitbox.intersects(e.hitbox) && p.health > 0){
					game.players.get(i).updateHealth(e.dmg);
					if(!(e instanceof Boss)) enemyRemoveList.add(e);
				}
			}
			for (Birdbot b : game.bots){
				if(b.hitbox.intersects(e.hitbox) && !b.invincible){
					b.updateHealth(e.dmg);
					enemyRemoveList.add(e);
				}
			}		
		}
		game.birds.removeAll(enemyRemoveList);
	}

	private void checkDropCollision(){
		ArrayList<CoinDrop> dropRemoveList = new ArrayList<CoinDrop>();
		for (CoinDrop d : game.drops){
			for (int i = 0; i < game.players.size(); i++){
				if(game.players.get(i).hitbox.intersects(d.hitbox)){
					game.coinsCollected += d.amountDrop;
					dropRemoveList.add(d);
				}
			}	
		}
		game.drops.removeAll(dropRemoveList);
	}

	private void checkPlayerDropCollision(){
		for (Player p1 : game.players){
			if (p1.skin.fspeed == 0){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : p1.bullets){
					for (Player p2 : game.players){
						if (p2.hitbox.intersects(b.hitbox)){
							p2.healHealth(p1.skin.power);
							bulletRemoveList.add(b);
						}
					}
				}
				p1.bullets.removeAll(bulletRemoveList);
			}
		}
	}


	private void cleanDeadObjects(){
		// Remove dead mobs
		ArrayList<EnemyEntity> enemyRemoveList = new ArrayList<EnemyEntity>();
		for (EnemyEntity e : game.birds){
			if (e.xpos < -200 || e.ypos > 700 || e.isDead()){
				enemyRemoveList.add(e);
			}
		}
		game.birds.removeAll(enemyRemoveList);	

		// Remove dead bots
		ArrayList<Birdbot> botRemoveList = new ArrayList<Birdbot>();
		for (Birdbot b : game.bots){
			if (b.health == 0){
				botRemoveList.add(b);
			}
		}
		game.bots.removeAll(botRemoveList);
	}


	private double botGetDistance(Birdbot bot, EnemyEntity en){
		int xposD = Math.abs(bot.xpos - en.xpos);
		int yposD = Math.abs(bot.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private double enemyGetDistance(Player p, EnemyEntity en){
		int xposD = Math.abs(p.xpos - en.xpos);
		int yposD = Math.abs(p.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private boolean checkIfTargeted(EnemyEntity enemy){
		ArrayList<EnemyEntity> targets = new ArrayList<EnemyEntity>();
		for (Birdbot BB : game.bots){
			if(BB.target != null){
				targets.add(BB.target);
			}	
		}
		return targets.contains(enemy);
	}

	private EnemyEntity botGetTarget(Birdbot bot){
		if (game.birds.isEmpty()) return null;
		else if(bot.targetCooldown < 100){
			return bot.target;
		}
		else{
			EnemyEntity target = null;
			for (EnemyEntity BE : game.birds){
				if(!checkIfTargeted(BE) && (BE.xpos > bot.xpos) &&
						BE.xpos < 1200 && BE.targetPriority > 0 && !BE.isDead()){
					if(target == null) {
						target = BE;
					}
					else if(BE.targetPriority * botGetDistance(bot, BE) <
							target.targetPriority * botGetDistance(bot, target)) {
						target = BE;
					}	
					bot.targetCooldown = 0;
				}
			}
			return target;
		}
	}

	private Player enemyGetTarget(EnemyEntity bird){
		if (game.players.isEmpty()) return null;
		else{
			Player target = null;
			for (Player p : game.players){
				if((bird.xpos > p.xpos) && (bird.xpos - p.xpos < bird.targetRange)){
					if(target == null) {
						target = p;
					}
					else if(enemyGetDistance(p, bird) < enemyGetDistance(p, bird)) {
						target = p;
					}	
				}
			}
			return target;
		}
	}



	private void updateTargets(){
		for (Birdbot b : game.bots){
			b.target = (EnemyUnit) botGetTarget(b);
		}
		for (EnemyEntity e : game.birds){
			e.playerTarget = enemyGetTarget(e);
		}
	}

	private void dropCoin(EnemyEntity b){
		Random RNG = new Random();
		if (RNG.nextInt(100 / b.droprate) == 0){
			int amount = RNG.nextInt(b.dropAmount + 1) + b.dropMinAmount;
			game.drops.add(new CoinDrop(amount, b.xpos + b.sizex / 2 - 25
					, b.ypos + b.sizey / 2 - 25));
		}
	}
}
