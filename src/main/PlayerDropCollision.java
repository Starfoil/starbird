package main;

import java.util.ArrayList;

class PlayerDropCollision implements CollisionInterface {
	public void checkCollision( GameInstance game ) {
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
}
