package main;

import java.util.ArrayList;

import birds.Boss;
import birds.EnemyEntity;

class MobCollision implements CollisionInterface {
	public void checkCollision( GameInstance game ) {
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
}
