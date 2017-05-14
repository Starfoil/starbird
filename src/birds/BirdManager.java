package birds;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class BirdManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7974164924154596242L;
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
	
	public abstract void spawn();
	
	
}
