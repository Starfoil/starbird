package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mainGUI.MainMenu;
import mainGUI.PlayerData;
import birds.Boss;
import birds.EnemyEntity;
import birds.EnemyUnit;
import birds.Spawner;

public class GameManager {
	private State state;
	private GameInstance game;
	private CollisionCheck collisionCheck;
	//private StateContext stateContext;
	public GameManager(GameInstance gameInstance) {
		game = gameInstance;
		collisionCheck = new CollisionCheck();
		state = new StateZero();
	}
	public void setState(State state) {
		this.state = state;
	}

	public void checkCollision() {
		collisionCheck.checkCollision(game);
	}
	public void performStatus(){
		state.perform(this, game);
		
	}
	
}
