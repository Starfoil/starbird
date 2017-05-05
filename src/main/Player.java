package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//Static Class. Does not have to be instanced.
public class Player {
	Image img;

	boolean left = false, right = false, up = false, down = false;

	public static int xpos;
	public static int ypos;
	public static int distance;	// x distance
	

	int dSpeed = 2;		// move distance speed
	int xspeed = 4;		// move x speed
	int yspeed = 4;		// move y speed

	int maxMana = 400;
	int maxHealth = 300;
	int mana = maxMana;
	int health = maxHealth;

	Rectangle hitbox;
	int playerSize = 100;

	ArrayList<Bullet> bullets;

	ImageIcon s = new ImageIcon("resources\\Player.png");

	//public static ArrayList getBullets()
	{
		//	return bullets;
	}

	public Player() {
		this.xpos = 100;
		this.ypos = 300;
		img = s.getImage();
		hitbox = new Rectangle(xpos, ypos, playerSize,  playerSize);
		bullets = new ArrayList<Bullet>();
	}

	public void draw(Graphics g){
		// Information
		g.setFont(new Font("Arial", Font.PLAIN, 12)); 
		g.drawString("X :"+xpos,40,550);
		g.drawString("Y :"+ypos,40,570);
		g.drawString("D :"+distance,40,590);

		// Health and mana bars
		g.setColor(Color.red);
		g.fillRect(750,20,maxMana,10);
		g.fillRect(50,20,maxHealth,10);
		g.setColor(Color.blue);
		g.fillRect(750, 20, mana, 10);
		g.setColor(Color.green);
		g.fillRect(50,20,health,10);

		// Player
		g.drawImage(img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		
		// Bullets
		for (Bullet b : bullets){
			b.draw(g);
		}
	}

	public void basicFire()//Method to run when when fired
	{
		if(mana > 40){
			Bullet z = new Bullet(xpos + 75 , ypos + 30 , 10, 40, 10);
			bullets.add(z);
			mana = mana - 40;
			z.explosive = true;
			z.setImage("resources\\bullet.png");
		}	
	}
	
	public void piercingFire()//Method to run when when fired
	{
		if(mana > 80){
			Bullet z = new Bullet(xpos + 75 , ypos + 30 , 12, 40, 3);
			bullets.add(z);
			mana = mana - 80;
			z.piercing = true;
			z.setImage("resources\\bullet1.png");
		}	
	}
	
	public void massiveFire()//Method to run when when fired
	{
		if(mana > 200){
			Bullet z = new Bullet(xpos, ypos, 6, 100, 100);
			bullets.add(z);
			mana = mana - 200;
			z.explosive = true;
			z.setImage("resources\\bullet4.png");
		}	
	}
	
	public void update(){
		move();
		updateMana();
		updateBullets();
	}

	public void move() {
		distance += dSpeed;
		if (right == true && xpos < 1200 - playerSize)		xpos += xspeed;
		if (left == true && xpos > 0)						xpos -= xspeed;
		if (up == true && ypos > 0)							ypos = ypos - yspeed;
		if (down == true && ypos < 750 - playerSize)		ypos = ypos + yspeed;
		hitbox.setLocation(xpos, ypos);
	}

	public void updateMana(){
		if(mana < maxMana){
			mana += 1;
		}
	}
	
	public void updateBullets(){
		ArrayList<Bullet> removeList = new ArrayList<Bullet>();
		for (Bullet b : bullets){
			if(b.x > 1200)	removeList.add(b);
			b.move();
		}
		bullets.removeAll(removeList);
	}
	
	public void updateHealth(int damageTaken){
		if (health - damageTaken > 0){
			health -= damageTaken;
		}
		else{
			health = 0;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) 	left = true;          
		if (key == KeyEvent.VK_RIGHT)	right = true;
		if (key == KeyEvent.VK_UP) 		up = true;
		if (key == KeyEvent.VK_DOWN)	down = true;
		if (key == KeyEvent.VK_SPACE)	basicFire();
		if (key == KeyEvent.VK_Z)		piercingFire();
		if (key == KeyEvent.VK_X)		massiveFire();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)	left = false;
		if (key == KeyEvent.VK_RIGHT)	right = false;
		if (key == KeyEvent.VK_UP)		up = false;
		if (key == KeyEvent.VK_DOWN)	down = false;

	}

}