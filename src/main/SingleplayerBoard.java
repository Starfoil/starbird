package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import online.ClientGame;
import online.GameInstance;
import online.GameManager;
import birds.*;

public class SingleplayerBoard extends JPanel implements ActionListener{

	public Image backgroundImg;

	Timer time;

	int backgroundPos1;
	int backgroundPos2;
	final int scrollSpeed = 2;

	static int score=0;
	Player p;

	GameInstance game;
	GameManager gm;

	Image[] playerImages = new Image[10];
	Image[] bulletImages = new Image[4];
	Image[] birdImages = new Image[10];


	// Constructor
	public SingleplayerBoard() throws InterruptedException {	
		setupImages();
		setupGame();
		setupObjects();
		time = new Timer(15, this);
		time.start();
	}

	public void setupObjects(){
		// Players
		p = new Player("P1");
		game.addPlayer(p);

		// Enemy Spawn
		game.addEnemies(new Bluejay(20, 3000, 500));
		//game.addEnemies(new Eagle(30, 30000, 5000));
		//game.addEnemies(new Bomber(25, 12000, 5000));
		//GM.addEnemies(new Buzzer(75, 20000, 3000));
		//GM.addEnemies(new Hawk(16, 24000, 10000));
		//GM.addEnemies(new Spoder(8, 20000, 2000));
		game.addEnemies(new Wraith(15, 3000, 1000));
		//GM.addEnemies(new Blackbird(1, 0, 2500));

		// Bots
		//GM.addBot();

	}

	public void setupImages(){
		// Background
		backgroundImg = new ImageIcon(SingleplayerBoard.class.getResource("/test.jpg")).getImage();
		// Player Skins
		playerImages[0] = new ImageIcon(SingleplayerBoard.class.getResource("/Player.png")).getImage();
		playerImages[1] = new ImageIcon(SingleplayerBoard.class.getResource("/Player1.png")).getImage();
		playerImages[2] = new ImageIcon(SingleplayerBoard.class.getResource("/Player2.png")).getImage();
		playerImages[3] = new ImageIcon(SingleplayerBoard.class.getResource("/Player3.png")).getImage();
		// Bullet Skins
		bulletImages[0] = new ImageIcon(SingleplayerBoard.class.getResource("/bullet.png")).getImage();
		bulletImages[1] = new ImageIcon(SingleplayerBoard.class.getResource("/bullet1.png")).getImage();
		bulletImages[2] = new ImageIcon(SingleplayerBoard.class.getResource("/bullet4.png")).getImage();
		bulletImages[3] = new ImageIcon(SingleplayerBoard.class.getResource("/bullet5.png")).getImage();
		// Enemy Skins
		birdImages[0] = new ImageIcon(SingleplayerBoard.class.getResource("/ybird.png")).getImage();
		birdImages[1] = new ImageIcon(SingleplayerBoard.class.getResource("/ghost.png")).getImage();
		birdImages[2] = new ImageIcon(SingleplayerBoard.class.getResource("/bbird.png")).getImage();
		birdImages[3] = new ImageIcon(SingleplayerBoard.class.getResource("/Bee.png")).getImage();
		birdImages[4] = new ImageIcon(SingleplayerBoard.class.getResource("/pilotbird.png")).getImage();
		birdImages[5] = new ImageIcon(SingleplayerBoard.class.getResource("/gbird.png")).getImage();
		birdImages[6] = new ImageIcon(SingleplayerBoard.class.getResource("/poibird.png")).getImage();
		birdImages[7] = new ImageIcon(SingleplayerBoard.class.getResource("/bbird2.png")).getImage();
		birdImages[8] = new ImageIcon(SingleplayerBoard.class.getResource("/BossBird.png")).getImage();
	}

	public void setupGame(){
		this.backgroundPos1 = 0;
		this.backgroundPos2 = 1200;
		addKeyListener(new AL());
		setFocusable(true);

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
		p.update();
		gm.update();
		gm.checkCollision();
		updateBackground();
		repaint();
	}

	public void paint(Graphics g) {
		//Background
		g.clearRect(0, 0, ClientGame.XFRAME, ClientGame.YFRAME);
		g.drawImage(backgroundImg, 1200 - backgroundPos1, 0, null);
		g.drawImage(backgroundImg, 1200 - backgroundPos2, 0, null);

		// Draw health/mana bars
		g.setColor(Color.red);
		g.fillRect(750, 20, p.maxMana, 10);
		g.fillRect(50, 20, p.maxHealth, 10);
		g.setColor(Color.blue);
		g.fillRect(750, 20, p.mana, 10);
		g.setColor(Color.green);
		g.fillRect(50, 20, p.health, 10);


		// Draw player
		for (Player p : game.players){
			p.draw(g, playerImages, bulletImages);
		}

		// Draw enemies
		for (BirdEntity e : game.birds){
			e.draw(g, birdImages);
		}

		// Draw bots
		for (Birdbot b : game.bots){
			b.draw(g, playerImages, bulletImages);
		}

		if (p.health <= 0){
			g.setFont(new Font("Arial", Font.PLAIN, 72)); 
			g.drawString("SMOKED!!!", 200, 300);
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

	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame();
		frame.add(new SingleplayerBoard());
		frame.setTitle("Singeplayer Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}



}