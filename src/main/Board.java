package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import birds.*;

public class Board extends JPanel implements ActionListener{
	Player p;
	public Image backgroundImg;

	Timer time;

	int backgroundPos1;
	int backgroundPos2;
	final int scrollSpeed = 2;

	static int score=0;
	
	static ArrayList<BirdManager> birds = new ArrayList<BirdManager>();
	
	CollisionManager CM;

	// Constructor
	public Board() throws InterruptedException {
		setupObjects();
		setupBackground();
		setupGameSystem();		
	}
	
	public void setupObjects(){
		p = new Player();
		
		// Enemy Spawn
		birds.add(new Bluejay(40, 30000, 500));
		birds.add(new Eagle(12, 20000, 12000));
		birds.add(new Bomber(8, 12000, 18000));
		birds.add(new Buzzer(75, 20000, 3000));
		birds.add(new Hawk(16, 24000, 10000));
		birds.add(new Spoder(4, 20000, 5000));
		birds.add(new Blackbird(1, 0, 1000));
		
	}
	
	public void setupBackground(){
		this.backgroundPos1 = 0;
		this.backgroundPos2 = 1200;
		ImageIcon i = new ImageIcon("resources\\test.jpg");
		backgroundImg = i.getImage();
	}
	
	public void setupGameSystem(){
		CM = new CollisionManager(p);
		for (BirdManager bm : birds){
			CM.addEnemy(bm);
		}
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer(15, this);
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
		CM.collisionCheck();
		updateBackground();
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw score
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Arial", Font.PLAIN, 48)); 
		g2d.drawString(""+score, 550, 44);

		//Background
		g2d.drawImage(backgroundImg, 1200 - backgroundPos1, 0, null);
		g2d.drawImage(backgroundImg, 1200 - backgroundPos2, 0, null);


		// Draw player
		p.draw(g);
		
		// Draw enemies
		for (BirdManager bm : birds){
			bm.draw(g);
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

}