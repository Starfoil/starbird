package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import mainGUI.SystemData;
import birds.EnemyEntity;
import birds.EnemyUnit;
import birds.Spawner;

public class GameInstance implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -133591635835713111L;
	public ArrayList<EnemyEntity> birds = new ArrayList<EnemyEntity>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Birdbot> bots = new ArrayList<Birdbot>();
	public ArrayList<CoinDrop> drops = new ArrayList<CoinDrop>();
	public ArrayList<Spawner> birdSpawn = new ArrayList<Spawner>();
	
	public int gameStatus = 0;
	public int gameID;
	
	public int dropRate = 5;
	
	public int coinsCollected = 0;
	public int score = 0;
	
	public int gameRunTimer = 0;
	public int gameEndTimer = 0;
	public int distance;
	
	public GameInstance(){
		Random RNG = new Random();
		gameID = RNG.nextInt(900000000) + 100000000;
	}
	
	public void addBot(int skinID){
		bots.add(new Birdbot(skinID));
	}
	
	public void addSpawn(int eID, int amount, int start, int end){
		birdSpawn.add(new Spawner(this, eID, amount, start, end));
	}
	
	public void addSpawn(Spawner s){
		s.game = this;
		birdSpawn.add(s);
	}
	
	public void addPlayer(Player p){
		players.add(p);
	}
	
		
	public String toString(){
		String s = "";
		for (Player p : players){
			s += p.toString() + ", ";
		}
		for (EnemyEntity e : birds){
			s += e.toString() + ", ";
		}
		return s;
	}
	
}
