package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import birds.BirdEntity;

public class Birdbot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -631828441655422224L;
	int botskinID;
	public int xpos = 75;
	public int ypos = 200;
	public int xspeed;
	public int yspeed;

	int maxHealth = 135;
	public int health;
	int cooldown = 0;
	public int targetCooldown = 0;


	public Rectangle hitbox;
	public ArrayList<Bullet> bullets;

	ImageIcon II;
	Image img;

	public BirdEntity target;
	boolean lockedOn = false;

	boolean right = false;
	boolean left = false;
	int offset;
	int destX;
	int offsetLimit = 250;
	public boolean invincible = false;

	public Birdbot(int skinID, int xspeed, int yspeed, int sizeX, int sizeY){
		this.botskinID = skinID;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		health = maxHealth;
		//II = new ImageIcon("resources\\"+skinID+".png");
		//img = II.getImage();
		hitbox = new Rectangle(xpos, ypos, sizeX, sizeY);
		bullets = new ArrayList<Bullet>();
	}

	public void setSpawn(int x, int y){
		xpos = x;
		ypos = y;
		hitbox.setLocation(x, y);
	}

	public void update(){
		move();
		shoot();
	}

	private void move(){
		if(target != null){
			if(target.boss) retreat();
			else{
				moveX();
				moveY();	
			}			
		}
		hitbox.setLocation(xpos, ypos);
	}

	private void strafeLeft(){
		offset = (int) (Math.random() * offsetLimit);
		destX = xpos - offset;
		right = false;
		left = true;
	}

	private void strafeRight(){
		offset = (int) (Math.random() * offsetLimit);
		destX = xpos + offset;
		right = true;
		left = false;
	}

	private void checkBounds(){
		if(xpos < 25){
			strafeRight();
		}
		else if(xpos > 500){
			strafeLeft();
		}
	}

	private void moveX(){
		checkBounds();
		if(!right && !left){
			strafeRight();
		}
		else if(right){
			xpos += xspeed;
			if(xpos > destX){
				strafeLeft();
			}
		}
		else if(left){
			xpos -= xspeed;
			if(xpos < destX){
				strafeRight();
			}
		}
	}

	private void moveY(){
		if(ypos > target.ypos + 15){
			ypos -= yspeed;
			lockedOn = false;
		}
		else if(ypos < target.ypos - 15){
			ypos += yspeed;
			lockedOn = false;
		}
		else{
			lockedOn = true;
		}
	}

	private void retreat(){
		if(ypos > -200){
			ypos -= yspeed;
		}
	}

	public void draw(Graphics g, Image[] playerImages, Image[] bulletImages){
		Image img = playerImages[botskinID];
		if (img != null) g.drawImage(img, xpos, ypos, null);
		
		g.setColor(Color.white);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos, ypos + 100, maxHealth, 5);
		g.setColor(Color.green);
		g.fillRect(xpos, ypos + 100, health, 5);

		// Bullets
		for (Bullet b : bullets){
			b.draw(g, bulletImages);
		}
	}

	private void shoot(){
		cooldown += 1;
		targetCooldown += 1;
		if (cooldown > 15 && target != null && lockedOn){
			Bullet z = new Bullet(0, xpos + 75 , ypos + 30 , 10, 40, 10);
			bullets.add(z);
			z.explosive = true;
			cooldown = 0;
		}
		updateBullets();
	}	


	private void updateBullets(){
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

}
