package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import online.ClientGame;
import birds.*;

public class ClientBoard extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9096180433067674090L;

	public Image backgroundImg;

	Timer time;

	int backgroundPos1;
	int backgroundPos2;
	final int scrollSpeed = 2;

	static int score=0;

	public Player p;
	public GameInstance game;

	Image[] playerImages = new Image[10];
	Image[] bulletImages = new Image[4];
	Image[] birdImages = new Image[10];

	// Constructor
	public ClientBoard(GameInstance game, Player p) throws InterruptedException {
		this.game = game;
		this.p = p;
		setupImages();
		setupGame();			
	}

	public void setupImages(){
		// Background
		backgroundImg = new ImageIcon(ClientBoard.class.getResource("/test.jpg")).getImage();
		// Player Skins
		playerImages[0] = new ImageIcon(ClientBoard.class.getResource("/Player.png")).getImage();
		playerImages[1] = new ImageIcon(ClientBoard.class.getResource("/Player1.png")).getImage();
		playerImages[2] = new ImageIcon(ClientBoard.class.getResource("/Player2.png")).getImage();
		playerImages[3] = new ImageIcon(ClientBoard.class.getResource("/Player3.png")).getImage();
		// Bullet Skins
		bulletImages[0] = new ImageIcon(ClientBoard.class.getResource("/bullet.png")).getImage();
		bulletImages[1] = new ImageIcon(ClientBoard.class.getResource("/bullet1.png")).getImage();
		bulletImages[2] = new ImageIcon(ClientBoard.class.getResource("/bullet4.png")).getImage();
		bulletImages[3] = new ImageIcon(ClientBoard.class.getResource("/bullet5.png")).getImage();
		// Enemy Skins
		birdImages[0] = new ImageIcon(ClientBoard.class.getResource("/ybird.png")).getImage();
		birdImages[1] = new ImageIcon(ClientBoard.class.getResource("/ybird.png")).getImage();
		birdImages[2] = new ImageIcon(ClientBoard.class.getResource("/bbird.png")).getImage();
		birdImages[3] = new ImageIcon(ClientBoard.class.getResource("/Bee.png")).getImage();
		birdImages[4] = new ImageIcon(ClientBoard.class.getResource("/pilotbird.png")).getImage();
		birdImages[5] = new ImageIcon(ClientBoard.class.getResource("/gbird.png")).getImage();
		birdImages[6] = new ImageIcon(ClientBoard.class.getResource("/poibird.png")).getImage();
		birdImages[7] = new ImageIcon(ClientBoard.class.getResource("/bbird2.png")).getImage();
		birdImages[8] = new ImageIcon(ClientBoard.class.getResource("/BossBird.png")).getImage();
	}


	public void setupGame(){
		this.backgroundPos1 = 0;
		this.backgroundPos2 = 1200;
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer(10, this);
		time.start();
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
	}

	public void updateGame(GameInstance gi){
		for (int i = 0; i < gi.players.size(); i++){
			Player PI = gi.players.get(i);
			if(PI.ID.equals(p.ID)){
				p.health = PI.health;
				ArrayList<Bullet> bulletRemoveList = new ArrayList<Bullet>();
				for (Bullet b : p.bullets){
					for (Bullet pb : PI.bullets){
						if (b.BPID == pb.BPID && !pb.live){
							bulletRemoveList.add(b);
						}
					}
				}
				p.bullets.removeAll(bulletRemoveList);
			}
		}
		this.game.birds = gi.birds;
		this.game.players = gi.players;
		this.game.bots = gi.bots;
	}

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}
	}

}