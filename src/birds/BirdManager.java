package birds;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class BirdManager {
	
	public ArrayList<BirdEntity> spawnList;
	public int spawnSize;
	public int spawnDeviation;
	public int spawnDistance;

	public BirdManager(int spawnSize, int spawnDeviation, int spawnDistance){
		this.spawnSize = spawnSize;
		this.spawnDeviation = spawnDeviation;
		this.spawnDistance = spawnDistance;
		spawnList = new ArrayList<BirdEntity>();
	}
	

	public void draw(Graphics g){
		for (BirdEntity e : spawnList){
			e.move();
			e.draw(g);
		}
	}


}
