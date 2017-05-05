package main;

import java.util.ArrayList;

import birds.BirdManager;
import birds.BirdEntity;

public class CollisionManager {

	Player p;
	ArrayList<BirdManager> enemyList = new ArrayList<BirdManager>();

	public CollisionManager(Player p){
		this.p = p;
	}

	public void addEnemy(BirdManager b){
		enemyList.add(b);
	}

	public void bulletCollision(){
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

	public void playerCollision(){
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

}
