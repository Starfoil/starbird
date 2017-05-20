package main;

import java.util.ArrayList;
import java.util.Random;

import mainGUI.MainMenu;
import mainGUI.PlayerData;
import birds.BirdEntity;

public class GameManager {

	private GameInstance game;
	

	public GameManager(GameInstance gameInstance){
		game = gameInstance;
	}

	public void update(){
		updateEnemies();
		updateBots();
		updateTargets();
		updateObjects();
		cleanDeadObjects();
	}

	public void checkCollision(){
		checkBulletCollision();
		checkMobCollision();
		checkDropCollision();
	}

	private void updateStatus(){
		boolean playflag = true;
		boolean aliveflag = false;
		boolean gameover = true;
		for (Player p : game.players){
			if (!p.playable) playflag = false;
			if (p.health > 0) aliveflag = true;
			if (p.xpos < 1300) gameover = false;
		}
		if 		(game.gameStatus == 0)							game.gameStatus = 1;
		else if (playflag && game.gameStatus == 1)				game.gameStatus = 2;
		else if (!aliveflag && game.gameStatus == 2) 			game.gameStatus = 3;
		else if (game.birds.isEmpty() && game.gameStatus == 2)	game.gameStatus = 4;	
		else if ((game.gameStatus == 3 || game.gameStatus == 4)
				&& game.gameEndTimer > 10) 					game.gameStatus = 5;
		else if (gameover){
			MainMenu.jtp.setSelectedIndex(1);
			MainMenu.jtp.setSelectedIndex(0);
			game.gameStatus = -1;
		}
	}

	public void performStatus(){
		if (game.gameStatus == 0){
			Random RNG = new Random();
			for (Player p : game.players){
				p.movetoX = RNG.nextInt(100) + 150;
				p.movetoY = RNG.nextInt(450) + 75;
			}
		}
		// Moving players into position
		else if(game.gameStatus == 1){
			for (Player p : game.players)	p.moveTo(p.movetoX, p.movetoY, 2);
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
		else if(game.gameStatus == 5){
			update();
			checkDropCollision();
			for (Player p : game.players) p.moveTo(1500, p.ypos, 4);
		}
		updateStatus();
	}

	private void updateBots(){
		for (Birdbot b : game.bots){
			b.update();
		}
	}

	private void updateEnemies(){
		for (BirdEntity b : game.birds){
			b.update();
		}
	}

	private void updateObjects(){
		for (CoinDrop d: game.drops){
			d.update();
		}
	}

	private void checkBulletCollision(){
		for (BirdEntity e : game.birds){
			// Check player
			for (int i = 0; i < game.players.size(); i++){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : game.players.get(i).bullets){
					if(b.hitbox.intersects(e.hitbox)){
						e.bulletHit(b.damage);
						if(!b.piercing)	{
							b.live = false;
							bulletRemoveList.add(b);
						}
					}
				}
				game.players.get(i).bullets.removeAll(bulletRemoveList);
			}
			// Check NPCs
			for (Birdbot bb : game.bots){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : bb.bullets){
					if(b.hitbox.intersects(e.hitbox)){
						e.bulletHit(b.damage);
						if(!b.piercing)	bulletRemoveList.add(b);
					}
				}
				bb.bullets.removeAll(bulletRemoveList);
			}	
			if(e.isDead() && e.dropcoins) {
				dropCoin(e.xpos, e.ypos);
				game.score += e.scoreValue;
			}
		}
	}

	private void checkMobCollision(){
		ArrayList<BirdEntity> enemyRemoveList = new ArrayList<BirdEntity>();
		for (BirdEntity e : game.birds){
			for (int i = 0; i < game.players.size(); i++){
				Player p = game.players.get(i);
				if(p.hitbox.intersects(e.hitbox) && p.health > 0){
					game.players.get(i).updateHealth(e.dmg);
					enemyRemoveList.add(e);
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


	private void cleanDeadObjects(){
		// Remove dead mobs
		ArrayList<BirdEntity> enemyRemoveList = new ArrayList<BirdEntity>();
		for (BirdEntity e : game.birds){
			if (e.xpos < -200 || e.ypos > 700 || e.isDead()){
				enemyRemoveList.add(e);
			}
		}
		game.birds.removeAll(enemyRemoveList);	

		// Remove dead players
//		ArrayList<Player> playerRemoveList = new ArrayList<Player>();
//		for (Player p : game.players){
//			if (p.health == 0){
//				playerRemoveList.add(p);
//			}
//		}
//		game.players.removeAll(playerRemoveList);

		// Remove dead bots
		ArrayList<Birdbot> botRemoveList = new ArrayList<Birdbot>();
		for (Birdbot b : game.bots){
			if (b.health == 0){
				botRemoveList.add(b);
			}
		}
		game.bots.removeAll(botRemoveList);
	}


	private double botGetDistance(Birdbot bot, BirdEntity en){
		int xposD = Math.abs(bot.xpos - en.xpos);
		int yposD = Math.abs(bot.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private double enemyGetDistance(Player p, BirdEntity en){
		int xposD = Math.abs(p.xpos - en.xpos);
		int yposD = Math.abs(p.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private boolean checkIfTargeted(BirdEntity enemy){
		ArrayList<BirdEntity> targets = new ArrayList<BirdEntity>();
		for (Birdbot BB : game.bots){
			if(BB.target != null && !BB.target.boss){
				targets.add(BB.target);
			}	
		}
		return targets.contains(enemy);
	}

	private BirdEntity botGetTarget(Birdbot bot){
		if (game.birds.isEmpty()) return null;
		else if(bot.targetCooldown < 100){
			return bot.target;
		}
		else{
			BirdEntity target = null;
			for (BirdEntity BE : game.birds){
				if(!checkIfTargeted(BE) && (BE.xpos > bot.xpos) &&
						(BE.xpos - bot.xpos < 800) && BE.targetable && !BE.isDead()){
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

	private Player enemyGetTarget(BirdEntity bird){
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
			b.target = botGetTarget(b);
		}
		for (BirdEntity e : game.birds){
			e.playerTarget = enemyGetTarget(e);
		}
	}

	private void dropCoin(int x, int y){
		Random RNG = new Random();
		if (RNG.nextInt(game.dropRate) == 0){
			int amount = RNG.nextInt(5) + 1;
			game.drops.add(new CoinDrop(amount, x + 25, y + 25));
		}
	}
}
