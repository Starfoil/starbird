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

	Bluejay b;
	Eagle e;
	Hawk h;
	
	CollisionManager CM;

	// Constructor
	public Board() throws InterruptedException {
		p = new Player();
		addKeyListener(new AL());
		setFocusable(true);

		this.backgroundPos1 = 0;
		this.backgroundPos2 = 1200;

		ImageIcon i = new ImageIcon("resources\\test.jpg");
		backgroundImg = i.getImage();

		time = new Timer(15, this);
		time.start();

		// Enemy Spawn
		b = new Bluejay(50, 15000, 500);
		e = new Eagle(25, 20000, 1000);
		h = new Hawk(25, 20000, 1000);
		
		// Collision management system
		CM = new CollisionManager(p);
		CM.addEnemy(b);
		CM.addEnemy(e);
		CM.addEnemy(h);
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
		CM.bulletCollision();
		CM.playerCollision();
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
		
		// Draw enemies (call once)
		e.draw(g);
		h.draw(g);
		b.draw(g);

	}

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}
	}



	//	ArrayList bullets = Player.getBullets();
	//	for (int w = 0; w < bullets.size(); w++)
	//	{
	//		Bullet m = (Bullet) bullets.get(w);//draw:
	//		if (m.getVisible() == true)
	//		{
	//			m.move();
	//			for(int i = 0; i<manager.amount;i++){
	//				if(m.hitbox.intersects(manager.enemies.get(i).hb)){
	//					if(manager.enemies.get(i).hp>0){
	//						if((manager.enemies.get(i).hp-25>=0)){
	//							manager.enemies.get(i).hp=manager.enemies.get(i).hp-25;
	//							bullets.remove(w);
	//							manager.enemies.get(i).hittaken++;
	//							//System.out.println("HIT!");
	//						}
	//					}
	//				}		
	//			}
	//			for(int i = 0; i<manager1.amount;i++){
	//				if(m.hitbox.intersects(manager1.enemies.get(i).hb)){
	//					if(manager1.enemies.get(i).hp>0){
	//						if((manager1.enemies.get(i).hp-25>=0)){
	//							manager1.enemies.get(i).hp=manager1.enemies.get(i).hp-25;
	//							bullets.remove(w);
	//							manager1.enemies.get(i).hittaken++;
	//						}
	//					}
	//				}		
	//			}
	//			for(int i = 0; i<manager2.amount;i++){
	//				if(m.hitbox.intersects(manager2.enemies.get(i).hb)){
	//					if(manager2.enemies.get(i).hp>0){
	//						if((manager2.enemies.get(i).hp-25>=0)){
	//							manager2.enemies.get(i).hp=manager2.enemies.get(i).hp-25;
	//							bullets.remove(w);
	//							manager2.enemies.get(i).hittaken++;
	//						}
	//					}
	//				}		
	//			}
	//			for(int i = 0; i<manager3.amount;i++){
	//				if(m.hitbox.intersects(manager3.enemies.get(i).hb)){
	//					if(manager3.enemies.get(i).hp>0){
	//						if((manager3.enemies.get(i).hp-25>=0)){
	//							manager3.enemies.get(i).hp=manager3.enemies.get(i).hp-25;
	//							bullets.remove(w);
	//							manager3.enemies.get(i).hittaken++;
	//						}
	//					}
	//				}		
	//			}
	//		}
	//		else bullets.remove(w);
	//	}	


}