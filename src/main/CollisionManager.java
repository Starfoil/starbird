package main;

import java.util.ArrayList;

import birds.*;

public class CollisionManager {

	Player p;
	ArrayList<BirdManager> enemyList = new ArrayList<BirdManager>();
	ArrayList <Blackbird> bosses = new ArrayList<Blackbird>();

	public CollisionManager(Player p){
		this.p = p;
	}

	public void addEnemy(BirdManager b){
		enemyList.add(b);
		if(b instanceof Blackbird){
			bosses.add((Blackbird) b);
		}
	}

	public void collisionCheck(){
		bulletCollision();
		enemyBulletCollision();
		playerCollision();
		cleanUp();
	}

	private void enemyBulletCollision(){
		for (Blackbird BB : bosses){
			for (BirdEntity BE : BB.spawnList){
				ArrayList<EnemyBullet> bulletRemoveList = new ArrayList<EnemyBullet>();
				for (EnemyBullet b : BE.bullets){
					if(b.hitbox.intersects(p.hitbox)){
						p.updateHealth(b.damage);
						bulletRemoveList.add(b);
					}
				}
				BE.bullets.removeAll(bulletRemoveList);
			}		
		}
	}

	private void bulletCollision(){
		for (BirdManager e : enemyList){
			ArrayList<BirdEntity> enemyRemoveList = new ArrayList<BirdEntity>();
			for (BirdEntity ee : e.spawnList){
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : p.bullets){
					if(b.hitbox.intersects(ee.hitbox)){
						ee.bulletHit(b.damage);
						if(b.explosive)	bulletRemoveList.add(b);
					}
				}
				p.bullets.removeAll(bulletRemoveList);
				if (ee.hp == 0){
					enemyRemoveList.add(ee);
				}
			}
			e.spawnList.removeAll(enemyRemoveList);
		}
	}

	private void playerCollision(){
		for (BirdManager e : enemyList){
			ArrayList<BirdEntity> enemyRemoveList = new ArrayList<BirdEntity>();
			for (BirdEntity ee : e.spawnList){
				if(p.hitbox.intersects(ee.hitbox)){
					p.updateHealth(ee.dmg);
					enemyRemoveList.add(ee);
				}
			}
			e.spawnList.removeAll(enemyRemoveList);
		}
	}
	
	private void cleanUp(){
		for (BirdManager BM : enemyList){
			ArrayList<BirdEntity> enemyRemoveList = new ArrayList<BirdEntity>();
			for (BirdEntity BE : BM.spawnList){
				if (BE.xpos < -200){
					enemyRemoveList.add(BE);
				}
			}
			BM.spawnList.removeAll(enemyRemoveList);
		}
	}
}
