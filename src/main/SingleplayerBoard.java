package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import mainGUI.GUIPanel;
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
	final int scrollSpeed = 2;

	Player p;

	public GameInstance game;
	GameManager gm;

	// Constructor
	public SingleplayerBoard(){	
		setupGame();
		setupObjects();
		time = new Timer(15, this);
		time.start();
	}

	public void setupObjects(){
		// Players
		p = new Player("Grayson", -100, 300);
		game.addPlayer(p);

		// Enemy Spawn
		game.addEnemies(new Bluejay(100, 30000, 1000));
		game.addEnemies(new Eagle(50, 30000, 5000));
		//game.addEnemies(new Bomber(25, 12000, 5000));
		//game.addEnemies(new Buzzer(75, 20000, 3000));
		game.addEnemies(new Hawk(40, 24000, 10000));
		//GM.addEnemies(new Spoder(8, 20000, 2000));
		//game.addEnemies(new Wraith(15, 3000, 1000));
		//GM.addEnemies(new Blackbird(1, 0, 2500));

		// Bots
		game.addBot(18);
		game.addBot(21);
		game.addBot(24);
	}


	public void setupGame(){
		this.backgroundPos1 = 0;
		this.backgroundPos2 = 1200;
		addKeyListener(new AL());
		//setFocusable(true);
		game = new GameInstance();
		gm = new GameManager(game);
	}


	public void updateBackground(){
		if (backgroundPos1 > 1200){
			backgroundPos1 = 0;
			backgroundPos2 = 1200;
		}
		backgroundPos1 += scrollSpeed;
		backgroundPos2 += scrollSpeed;
	}

	// During each timer tick
	public void actionPerformed(ActionEvent e) {
		if (game.gameStatus >= 0){
			p.update();
			gm.performStatus();
			updateBackground();
			repaint();
		}else{
			time.stop();
		}
	}

	public void paint(Graphics g) {
		//Background
		g.clearRect(0, 0, ClientGame.XFRAME, ClientGame.YFRAME);
		g.drawImage(SystemData.backgroundImg, 1200 - backgroundPos1, 0, null);
		g.drawImage(SystemData.backgroundImg, 1200 - backgroundPos2, 0, null);

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

		// Draw player
		for (Player p : game.players){
			p.draw(g, SystemData.playerImages, SystemData.bulletImages);
		}

		// Draw enemies
		for (BirdEntity e : game.birds){
			e.draw(g, SystemData.birdImages);
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
	
	public String toString(){
		return Integer.toString(this.game.gameID);
	}
}