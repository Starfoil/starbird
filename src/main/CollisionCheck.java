package main;

public class CollisionCheck implements CollisionInterface {

	private BulletCollision 	bulletCollision;
	private MobCollision 		mobCollision;
	private DropCoinCollision	dropCoinCollision;
	private PlayerDropCollision playerDropCollision;
	
	@Override
	public void checkCollision(GameInstance game) {
		checkBulletCollision(game);
		checkMobCollision(game);
		checkDropCoinCollision(game);
		checkPlayerDropCollision(game);
	}

	public void checkBulletCollision(GameInstance game) {
		if( bulletCollision == null ) 
			bulletCollision = new BulletCollision();
		
		bulletCollision.checkCollision(game);
	}
	
	public void checkMobCollision(GameInstance game) {
		if( mobCollision == null ) 		
			mobCollision = new MobCollision();
		
		mobCollision.checkCollision(game);
	}
	
	public void checkDropCoinCollision(GameInstance game) {
		if( dropCoinCollision == null ) 	
			dropCoinCollision = new DropCoinCollision();
		
		dropCoinCollision.checkCollision(game);
	}
	
	public void checkPlayerDropCollision(GameInstance game) {
		if( playerDropCollision == null ) 	
			playerDropCollision	= new PlayerDropCollision();
		
		playerDropCollision.checkCollision(game);
	}
}
