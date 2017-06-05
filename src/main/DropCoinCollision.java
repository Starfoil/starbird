package main;

import java.util.ArrayList;

class DropCoinCollision implements CollisionInterface {
	public void checkCollision( GameInstance game ) {
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
}
