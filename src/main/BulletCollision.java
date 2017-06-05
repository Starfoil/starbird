package main;

import java.util.ArrayList;
import java.util.Random;

import birds.Boss;
import birds.EnemyEntity;

class BulletCollision implements CollisionInterface {
	public void checkCollision( GameInstance game ) {
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
				if (e.dropAmount > 0 || e.dropMinAmount > 0) dropCoin(game, e);
				game.score += e.scoreValue;
			}
		}
	}
	private void dropCoin(GameInstance game, EnemyEntity b){
		Random RNG = new Random();
		if (RNG.nextInt(100 / b.droprate) == 0){
			int amount = RNG.nextInt(b.dropAmount + 1) + b.dropMinAmount;
			game.drops.add(new CoinDrop(amount, b.xpos + b.sizex / 2 - 25
					, b.ypos + b.sizey / 2 - 25));
		}
	}
}
