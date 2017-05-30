package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import mainGUI.GUIPanel;
import mainGUI.LevelSpawner;
import mainGUI.MainMenu;
import mainGUI.PlayerData;
import mainGUI.SystemData;
import online.ClientGame;
import birds.*;

public class SingleplayerBoard extends JPanel implements ActionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2016556832011416106L;

	Timer time;

	int backgroundPos1;
	int backgroundPos2;
	Image background;
	
	
	final int scrollSpeed = 2;

	Player p;

	public GameInstance game;
	GameManager gm;

	// Constructor
	public SingleplayerBoard(LevelSpawner level){	
		//Setup game
		game = new GameInstance();
		gm = new GameManager(game);
		//Setup player
		p = new Player(PlayerData.currentSkin.name, -150, 300);
		game.addPlayer(p);
		//Setup bots
		//game.addBot(3);
		//Setup enemies
		for (Spawner s : level.spawns)	game.addSpawn(s);
		//Setup background
		this.background = SystemData.backgroundImages[level.backgroundID];
		this.backgroundPos1 = 0;
		this.backgroundPos2 = background.getWidth(null);
		//Start game
		addKeyListener(new AL());
		time = new Timer(15, this);
		time.start();
	}

	// During each timer tick
	public void actionPerformed(ActionEvent e) {
		if (game.gameStatus >= 0){
			gm.performStatus();
			p.update();
			updateBackground();
			repaint();
		}else{
			time.stop();
			PlayerData.coins += game.coinsCollected;
			if (game.score > PlayerData.highscore) PlayerData.highscore = game.score;
			PlayerData.saveData();
			MainMenu.jtp.setSelectedIndex(1);
			MainMenu.jtp.setSelectedIndex(0);
		}
	}

	public void paint(Graphics g) {
		//Background
		g.clearRect(0, 0, MainMenu.XFRAME, MainMenu.YFRAME);
		g.drawImage(background, background.getWidth(null) - backgroundPos1, 0, null);
		g.drawImage(background, background.getWidth(null) - backgroundPos2, 0, null);

		// Draw health/mana bars
		g.setColor(Color.red);
		g.fillRect(750, 20, (int) p.maxMana, 10);
		g.fillRect(50, 20, (int) p.maxHealth, 10);
		g.setColor(Color.blue);
		g.fillRect(750, 20, (int) p.mana, 10);
		g.setColor(Color.green);
		g.fillRect(50, 20, (int) p.health, 10);
		
		// Draw text labels
		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.PLAIN, 24)); 
		g.drawImage(SystemData.trophyIcon, 10, 40, null);
		g.drawString(Integer.toString(game.score), 65, 75);
		g.drawImage(SystemData.coinImage, 10, 100, null);
		g.drawString(Integer.toString(game.coinsCollected), 65, 135);
		g.drawString("Distance : " + game.distance, 10, 620);

		// Draw player
		for (Player p : game.players){
			p.draw(g, SystemData.playerImages, SystemData.bulletImages);
		}

		// Draw enemies
		for (EnemyEntity e : game.birds){
			e.draw(g);
		}

		// Draw bots
		for (Birdbot b : game.bots){
			b.draw(g, SystemData.playerImages, SystemData.bulletImages);
		}

		// Draw coins
		for (CoinDrop d : game.drops){
			d.draw(g, SystemData.coinImage);
		}
		
		// All players dead
		if (game.gameStatus == 3){
			g.setColor(Color.BLACK);
			g.drawImage(SystemData.defeatIcon, 500, 150, null);
			g.drawImage(SystemData.trophyIcon, 550, 375, null);
			g.drawImage(SystemData.coinImage, 550, 445, null);
			g.setFont(new Font("Calibri", Font.BOLD, 28)); 
			g.drawString("Defeat", 560, 345);
			g.setFont(new Font("Arial", Font.PLAIN, 24)); 
			g.drawString(Integer.toString(game.score), 610, 410);
			g.drawString(Integer.toString(game.coinsCollected), 610, 480);
		}
		if (game.gameStatus == 4){
			g.setColor(Color.BLACK);
			g.drawImage(SystemData.victoryIcon, 500, 150, null);
			g.drawImage(SystemData.trophyIcon, 550, 375, null);
			g.drawImage(SystemData.coinImage, 550, 445, null);
			g.setFont(new Font("Calibri", Font.BOLD, 28)); 
			g.drawString("Victory", 555, 345);
			g.setFont(new Font("Arial", Font.PLAIN, 24)); 
			g.drawString(Integer.toString(game.score), 610, 410);
			g.drawString(Integer.toString(game.coinsCollected), 610, 480);
		}
	}

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}
	}
	
	public void updateBackground(){
		if (backgroundPos1 > background.getWidth(null)){
			backgroundPos1 = 0;
			backgroundPos2 = background.getWidth(null);
		}
		backgroundPos1 += scrollSpeed;
		backgroundPos2 += scrollSpeed;
	}
	
	public String toString(){
		return Integer.toString(this.game.gameID);
	}
}