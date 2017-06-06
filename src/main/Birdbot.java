package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import mainGUI.FontData;
import mainGUI.SystemData;
import birds.Boss;
import birds.EnemyEntity;
import birds.EnemyUnit;

public class Birdbot extends Friend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -631828441655422224L;
	Skin skin;
	public int xpos;
	public int ypos;

	int maxHealth = 200;
	public int health;
	int maxMana = 200;
	public int mana;
	
	public Rectangle hitbox;
	public ArrayList<Bullet> bullets;
	
	int cooldown = 0;
	
	// Targeting
	public EnemyEntity target;
	boolean lockedOn = false;
	public int targetCooldown = 0;
	
	// Zig
	boolean right = false;
	boolean left = false;
	int offset;
	int destX;
	int offsetLimit = 250;
	
	public boolean invincible = false;

	public Birdbot(int skinID){
		Random RNG = new Random();
		this.xpos = RNG.nextInt(500) + 10;
		this.ypos = RNG.nextInt(600) + 50;
		this.skin = SystemData.getSkin(skinID);
		this.health = maxHealth;
		this.mana = maxMana;
		hitbox = new Rectangle(xpos, ypos, skin.sizeX, skin.sizeY);
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
		targetCooldown += 1;
		updateMana();
	}
	
	public void updateMana(){
		if(mana < maxMana){
			mana += skin.manaRegen;
		}
	}

	public void move(){
		if(target != null){
			if(target instanceof Boss) retreat();
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
		else if(xpos > 1000){
			strafeLeft();
		}
	}

	private void moveX(){
		checkBounds();
		if(!right && !left){
			strafeRight();
		}
		else if(right){
			xpos += skin.xspeed;
			if(xpos > destX){
				strafeLeft();
			}
		}
		else if(left){
			xpos -= skin.xspeed;
			if(xpos < destX){
				strafeRight();
			}
		}
	}

	private void moveY(){
		if(ypos > target.ypos + 15){
			ypos -= skin.yspeed;
			lockedOn = false;
		}
		else if(ypos < target.ypos - 15){
			ypos += skin.yspeed;
			lockedOn = false;
		}
		else{
			lockedOn = true;
		}
	}

	private void retreat(){
		if(ypos > -200){
			ypos -= skin.yspeed;
		}
	}

	public void draw(Graphics g, Image[] playerImages, Image[] bulletImages){
		g.drawImage(playerImages[skin.skinID], xpos, ypos, null);
		
		g.setColor(Color.white);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		g.setFont(FontData.getInstance().getFont("Arial", Font.PLAIN, 12)); g.setColor(Color.BLACK);
		g.drawString(skin.name, xpos + skin.sizeX / 4, ypos);
		
//		g.setColor(Color.red);
//		g.fillRect(xpos, ypos + 100, maxHealth, 5);
//		g.setColor(Color.green);
//		g.fillRect(xpos, ypos + 100, health, 5);

		// Bullets
		for (Bullet b : bullets){
			b.draw(g, bulletImages);
		}
	}

	public void shoot(){
		cooldown -= 1;
		if (cooldown < 0 && target != null && lockedOn && mana >= skin.manaCost){
			Bullet z = new Bullet(skin.bulletID, xpos + skin.xHBOffset , ypos + skin.yHBOffset, 
					skin.bulletXSize, skin.bulletYSize, skin.fspeed, skin.power, skin.piercing);
			bullets.add(z);
			cooldown = 20 / skin.frate;
			mana -= skin.manaCost;
		}
		updateBullets();
	}	


	public void updateBullets(){
		ArrayList<Bullet> removeList = new ArrayList<Bullet>();
		for (Bullet b : bullets){
			if(b.x > 1200)	removeList.add(b);
			b.move();
		}
		bullets.removeAll(removeList);
	}


	public void updateHealth(double rawDamage){
		double damageRecieved = rawDamage - (rawDamage * skin.defense) / 100;
		if (health - damageRecieved > 0){
			health -= damageRecieved;
		}
		else{
			health = 0;
		}
	}

}
