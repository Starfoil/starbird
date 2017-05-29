package online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import main.Birdbot;
import main.Bullet;
import main.CoinDrop;
import main.GameInstance;
import main.Player;
import mainGUI.MainMenu;
import mainGUI.PlayerData;
import birds.EnemyUnit;

public class ServerGameManager {

	private GameInstance game;


	public ServerGameManager(GameInstance gameInstance){
		game = gameInstance;
	}

	public void update(){
		updateEnemies();
		updateBots();
		updateTargets();
		updateObjects();
	}

	public void checkCollision(){
		checkBulletCollision();
		checkMobCollision();
		checkDropCollision();
		checkPlayerDropCollision();
		cleanDeadObjects();
	}

	private void updateStatus(){
		boolean aliveflag = false;
		for (Player p : game.players){
			if (p.health > 0) aliveflag = true;
		}
		if (!aliveflag && game.gameStatus == 2) 			game.gameStatus = 3;
		else if (game.birds.isEmpty() && game.gameStatus == 2)	game.gameStatus = 4;	
		else if ((game.gameStatus == 3 || game.gameStatus == 4) && game.gameEndTimer > 500){
			game.gameStatus = -1;
		}
	}

	public void performUpdateStatus(){
		if (game.gameStatus == 1){
			// Waiting to start
		}
		// Live game
		if(game.gameStatus == 2){
			update();
			//checkCollision();
		}
		// All players are dead
		else if(game.gameStatus == 3){
			update();
			//checkCollision();
			game.gameEndTimer += 1;
		}
		// All enemies are dead
		else if(game.gameStatus == 4){
			update();
			//checkDropCollision();
			game.gameEndTimer += 1;
		}
		updateStatus();
	}
	
	public void performCollisionStatus(){
		if (game.gameStatus == 1){
			// Waiting to start
		}
		// Live game
		if(game.gameStatus == 2){
			checkCollision();
		}
		// All players are dead
		else if(game.gameStatus == 3){
			checkCollision();
		}
		// All enemies are dead
		else if(game.gameStatus == 4){
			checkDropCollision();
		}
	}
	
	
	
	public void updatePlayer(Player p){
		if(!existingPlayer(p)){
			game.addPlayer(p);
		}
		for (int i = 0; i < game.players.size(); i++){
			if(game.players.get(i).ID.equals(p.ID)){
				game.players.set(i, p);
			}
		}
	}
	
	private boolean existingPlayer(Player p){
		for (int i = 0; i < game.players.size(); i++){
			if(game.players.get(i).ID.equals(p.ID)){
				return true;
			}
		}
		return false;
	}

	private void updateBots(){
		for (Birdbot b : game.bots){
			b.update();
		}
	}

	private void updateEnemies(){
		for (EnemyUnit b : game.birds){
			b.update();
		}
	}

	private void updateObjects(){
		for (CoinDrop d: game.drops){
			d.update();
		}
	}

	private void checkBulletCollision(){
		for (EnemyUnit e : game.birds){
			// Check player
			for (int i = 0; i < game.players.size(); i++){
				for (Bullet b : game.players.get(i).bullets){
					if(b.hitbox.intersects(e.hitbox) && game.players.get(i).skin.fspeed != 0){
						e.bulletHit(b.damage);
						if(!b.piercing)	b.live = false;
					}
				}
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
			if(e.isDead()) {
				if (e.dropcoins) dropCoin(e.xpos, e.ypos);
				game.score += e.scoreValue;
			}
		}
	}

	private void checkMobCollision(){
		ArrayList<EnemyUnit> enemyRemoveList = new ArrayList<EnemyUnit>();
		for (EnemyUnit e : game.birds){
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
		ArrayList<EnemyUnit> enemyRemoveList = new ArrayList<EnemyUnit>();
		for (EnemyUnit e : game.birds){
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


	private double botGetDistance(Birdbot bot, EnemyUnit en){
		int xposD = Math.abs(bot.xpos - en.xpos);
		int yposD = Math.abs(bot.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private double enemyGetDistance(Player p, EnemyUnit en){
		int xposD = Math.abs(p.xpos - en.xpos);
		int yposD = Math.abs(p.ypos - en.ypos);
		double dist = Math.sqrt(xposD^2 + yposD^2);
		return dist;
	}

	private boolean checkIfTargeted(EnemyUnit enemy){
		ArrayList<EnemyUnit> targets = new ArrayList<EnemyUnit>();
		for (Birdbot BB : game.bots){
			if(BB.target != null && !BB.target.boss){
				targets.add(BB.target);
			}	
		}
		return targets.contains(enemy);
	}

	private EnemyUnit botGetTarget(Birdbot bot){
		if (game.birds.isEmpty()) return null;
		else if(bot.targetCooldown < 100){
			return bot.target;
		}
		else{
			EnemyUnit target = null;
			for (EnemyUnit BE : game.birds){
				if(!checkIfTargeted(BE) && (BE.xpos > bot.xpos) &&
						BE.xpos < 1200 && BE.targetable && !BE.isDead()){
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

	private Player enemyGetTarget(EnemyUnit bird){
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
		for (EnemyUnit e : game.birds){
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
