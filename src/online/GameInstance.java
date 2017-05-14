package online;

import java.io.Serializable;
import java.util.ArrayList;

import main.Birdbot;
import main.Player;
import birds.BirdEntity;
import birds.BirdManager;

public class GameInstance implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -133591635835713111L;
	public ArrayList<BirdEntity> birds = new ArrayList<BirdEntity>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Birdbot> bots = new ArrayList<Birdbot>();
		
	public GameInstance(){
		
	}
	
	public void addBot(){
		Birdbot bob = new Birdbot(2, 2, 3, 135, 95);
		bob.setSpawn(50, 150);
		bots.add(bob);
		Birdbot bob2 = new Birdbot(3, 2, 3, 135, 95);
		bob2.setSpawn(250, 450);
		bots.add(bob2);
	}
	
	public void addEnemies(BirdManager m){
		for (BirdEntity b : m.spawnList){
			birds.add(b);
		}
	}

	public void addPlayer(Player p){
		players.add(p);
	}
	
	public void updatePlayer(Player p){
		if(!existingPlayer(p)){
			addPlayer(p);
		}
		for (int i = 0; i < players.size(); i++){
			if(players.get(i).ID.equals(p.ID)){
				players.set(i, p);
			}
		}
	}
	
	private boolean existingPlayer(Player p){
		for (int i = 0; i < players.size(); i++){
			if(players.get(i).ID.equals(p.ID)){
				return true;
			}
		}
		return false;
	}
		
	public String toString(){
		String s = "";
		for (Player p : players){
			s += p.toString() + ", ";
		}
		for (BirdEntity e : birds){
			s += e.toString() + ", ";
		}
		return s;
	}
	
}
