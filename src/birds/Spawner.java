package birds;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import main.GameInstance;
import mainGUI.SystemData;

public class Spawner {
	
	private Random RNG = new Random();
	public boolean alive = true;
	
	public GameInstance game;
	public EnemyEntity bird;
	public int spawnAmount;
	public int spawnStart;	//distance for spawn
	public int spawnEnd;	//distance end
	
	public PriorityQueue<Integer> spawnTime;
	
	public Spawner(GameInstance game, int eID, int amount, int start, int end){
		this.game = game;
		this.bird = SystemData.getEnemy(eID);
		this.spawnAmount = amount;
		this.spawnStart = start;
		this.spawnEnd = end;
		this.spawnTime = new PriorityQueue<Integer>(amount);
		createSpawnLocations();
	}
	
	private void createSpawnLocations(){
		for (int i = 0; i < spawnAmount; i++){
			int time = RNG.nextInt(spawnEnd - spawnStart + 1) + spawnStart;
			spawnTime.add(time);
		}	
	}
	
	public void spawn(){
		if (spawnTime.isEmpty())	alive = false;
		else{
			int check = spawnTime.peek();	
			if (check < game.distance){
				spawnTime.remove();
				EnemyEntity spawn = null;
				if(bird instanceof EnemyUnit){
					spawn = new EnemyUnit(bird);
				}else if(bird instanceof Boss){
					spawn = new Boss((Boss) bird);
				}
				if (spawn.zigFactor == -1){
					spawn.setSpawn(1200, RNG.nextInt(400) - 410 - spawn.sizey);
				}else{
					spawn.setSpawn(1200, RNG.nextInt(650 - spawn.sizey));
				}	
				game.birds.add(spawn);
			}
		}
	}
	
	
	
}
