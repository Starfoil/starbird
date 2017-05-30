package mainGUI;

import java.util.ArrayList;

import birds.Spawner;

public class LevelSpawner{
	
	public int ID;
	public String name;
	public ArrayList<Spawner> spawns;
	public int backgroundID;
	
	public LevelSpawner(int ID, String name, int bgID){
		this.ID = ID;
		this.name = name;
		this.backgroundID = bgID;
		spawns = new ArrayList<Spawner>();
	}
	
	public LevelSpawner(LevelSpawner levelSpawner){
		this.ID = levelSpawner.ID;
		this.name = levelSpawner.name;
		this.backgroundID = levelSpawner.backgroundID;
		spawns = new ArrayList<Spawner>();
		for (Spawner s : levelSpawner.spawns){
			this.spawns.add(new Spawner(null, s.bird.eID, s.spawnAmount, s.spawnStart, s.spawnEnd));
		}
	}
	
	
	public void addSpawner(int eid, int amount, int start, int end){
		spawns.add(new Spawner(null, eid, amount, start, end));
	}
	
	public String toString(){
		return name;
	}
	

	
}
