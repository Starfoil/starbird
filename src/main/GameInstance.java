package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
	public ArrayList<CoinDrop> drops = new ArrayList<CoinDrop>();
	
	public int gameStatus = 0;
	public int gameID;
	
	public int dropRate = 5;
	
	public int coinsCollected = 0;
	public int score = 0;
	
	public int gameRunTimer = 0;
	public int gameEndTimer = 0;
	
	public GameInstance(){
		Random RNG = new Random();
		gameID = RNG.nextInt(900000000) + 100000000;
	}
	
	public void addBot(int skinID){
		bots.add(new Birdbot(skinID));
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
