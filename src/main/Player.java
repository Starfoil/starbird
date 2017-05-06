package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//Static Class. Does not have to be instanced.
public class Player {
	//Image img;

	boolean left = false, right = false, up = false, down = false;
	boolean basicFire = false, pierceFire = false, explosiveFire = false, collateralFire = false;
	
	public static int xpos;
	public static int ypos;
	
	public Skin skin;

	//int xspeed = 4;		// move x speed
	//int yspeed = 4;		// move y speed

	int maxMana = 400;
	int maxHealth = 300;
	int mana = maxMana;
	int health = maxHealth;

	Rectangle hitbox;
	//int playerSize = 100;

	ArrayList<Bullet> bullets;

	//ImageIcon s = new ImageIcon("resources\\Player.png");
	
	int cooldown = 0;
	int cooldownCap = 10;

	public Player() {
		xpos = 100;
		ypos = 300;
		skin = new Skin("Player", 4, 4, 100);
		hitbox = new Rectangle(xpos, ypos, skin.playerSize,  skin.playerSize);
		bullets = new ArrayList<Bullet>();
	}
	
	public void changeSkin(){
		skin = new Skin("Player1", 8, 6, 75);
		skin.setOffset(20, 15);
		skin.setManaRegen(5);
		hitbox = new Rectangle(xpos, ypos, skin.playerSize + 20,  skin.playerSize);
	}

	public void draw(Graphics g){
		// Information
		g.setFont(new Font("Arial", Font.PLAIN, 12)); 
		g.drawString("X :"+xpos,40,550);
		g.drawString("Y :"+ypos,40,570);

		// Health and mana bars
		g.setColor(Color.red);
		g.fillRect(750,20,maxMana,10);
		g.fillRect(50,20,maxHealth,10);
		g.setColor(Color.blue);
		g.fillRect(750, 20, mana, 10);
		g.setColor(Color.green);
		g.fillRect(50,20,health,10);

		// Player
		g.drawImage(skin.img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		
		// Bullets
		for (Bullet b : bullets){
			b.draw(g);
		}
	}

	public void basicFire()//Method to run when when fired
	{	
		int manaCost = 30;
		if(mana > manaCost){
			Bullet z = new Bullet(xpos + 75 , ypos + 30 , 10, 40, 10);
			bullets.add(z);
			mana = mana - manaCost;
			z.explosive = true;
			z.setImage("resources\\bullet.png");
		}	
	}
	
	public void piercingFire()//Method to run when when fired
	{	
		int manaCost = 60;
		if(mana > manaCost){
			Bullet z = new Bullet(xpos + 75 , ypos + 30 , 12, 40, 2);
			bullets.add(z);
			mana = mana - manaCost;
			z.piercing = true;
			z.setImage("resources\\bullet1.png");
		}	
	}
	
	public void massiveFire()//Method to run when when fired
	{	
		int manaCost = 120;
		if(mana > manaCost){
			Bullet z = new Bullet(xpos, ypos, 8, 100, 100);
			bullets.add(z);
			mana = mana - manaCost;
			z.explosive = true;
			z.setImage("resources\\bullet4.png");
		}	
	}
	
	public void collateralFire()
	{
		int manaCost = 280;
		if(mana > manaCost){
			Bullet z = new Bullet(xpos, ypos, 8, 100, 5);
			bullets.add(z);
			mana = mana - manaCost;
			z.piercing = true;
			z.setImage("resources\\bullet5.png");
		}	
	}
	
	public void update(){
		move();
		updateMana();
		updateBullets();
		shoot();
	}

	public void move() {
		//distance += dSpeed;
		if (right == true && xpos < 1200 - skin.playerSize)		xpos += skin.xspeed;
		if (left == true && xpos > 0)						xpos -= skin.xspeed;
		if (up == true && ypos > 0)							ypos = ypos - skin.yspeed;
		if (down == true && ypos < 750 - skin.playerSize)		ypos = ypos + skin.yspeed;
		hitbox.setLocation(xpos + skin.xHBOffset, ypos + skin.yHBOffset);
	}

	public void updateMana(){
		if(mana < maxMana){
			mana += skin.manaRegen;
		}
	}
	
	public void shoot(){
		cooldown -= 1;
		boolean ready = false;
		if(cooldown < 0) ready = true;
		if(basicFire && ready){
			basicFire();
			cooldown = cooldownCap;
		}
		else if(pierceFire && ready){
			piercingFire();
			cooldown = cooldownCap;
		}
		else if(explosiveFire && ready){
			massiveFire();
			cooldown = 2 * cooldownCap;
		}
		else if(collateralFire && ready){
			collateralFire();
			cooldown = 2 * cooldownCap;
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
		if (key == KeyEvent.VK_SPACE)	basicFire = true;
		if (key == KeyEvent.VK_Z)		pierceFire = true;
		if (key == KeyEvent.VK_X)		explosiveFire = true;
		if (key == KeyEvent.VK_C)		collateralFire = true;
		if (key == KeyEvent.VK_P)		changeSkin();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)	left = false;
		if (key == KeyEvent.VK_RIGHT)	right = false;
		if (key == KeyEvent.VK_UP)		up = false;
		if (key == KeyEvent.VK_DOWN)	down = false;
		if (key == KeyEvent.VK_SPACE)	basicFire = false;
		if (key == KeyEvent.VK_Z)		pierceFire = false;
		if (key == KeyEvent.VK_X)		explosiveFire = false;
		if (key == KeyEvent.VK_C)		collateralFire = false;
	}

}