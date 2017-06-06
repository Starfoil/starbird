package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import mainGUI.FontData;
import mainGUI.PlayerData;
import mainGUI.SystemData;


//Static Class. Does not have to be instanced.
public class Player extends Friend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3316827426786034879L;
	public String ID;

	boolean left = false, right = false, up = false, down = false;
	boolean basicFire = false;

	public int xpos;
	public int ypos;

	public Skin skin;


	public double maxMana = 400;
	public double maxHealth = 400;
	public double mana = maxMana;
	public double health = maxHealth;

	public Rectangle hitbox;
	public ArrayList<Bullet> bullets;

	int cooldown = 0;
	public boolean playable = false;
	public int movetoX;
	public int movetoY;

	public Player(String ID) {
		this.ID = ID;
		xpos = 100;
		ypos = 300;
		skin = PlayerData.currentSkin;
		hitbox = new Rectangle(xpos, ypos, skin.sizeX, skin.sizeY);
		bullets = new ArrayList<Bullet>();
	}

	public Player(String ID, int xspawn, int yspawn) {
		this.ID = ID;
		xpos = xspawn;
		ypos = yspawn;
		skin = PlayerData.currentSkin;
		hitbox = new Rectangle(xpos, ypos, skin.sizeX, skin.sizeY);
		bullets = new ArrayList<Bullet>();
	}


	public void draw(Graphics g, Image[] playerImages, Image[] bulletImages){
		g.drawImage(playerImages[skin.skinID], xpos, ypos, null);
		
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, skin.sizeX, skin.sizeY);
		g.setFont(FontData.getInstance().getFont("Arial", Font.PLAIN, 12)); g.setColor(Color.BLACK);
		g.drawString(ID, xpos + skin.sizeX / 4, ypos);

		for (Bullet b : bullets){
			b.draw(g, bulletImages);
		}	
	}


	public void fireBullet(){	
		if(mana >= skin.manaCost){
			Bullet z = new Bullet(skin.bulletID, xpos + skin.xHBOffset , ypos + skin.yHBOffset, 
					skin.bulletXSize, skin.bulletYSize, skin.fspeed, skin.power, skin.piercing);
			bullets.add(z);
			mana = mana - skin.manaCost;
		}	
	}

	public void update(){
		if (health > 0){
			move();
			shoot();
		}
		else if (health <= 0){
			this.skin = SystemData.getSkin(7);
			move();
		}
		updateMana();
		updateBullets();
	}

	public void move() {
		if (playable){
			if (right == true && xpos < 1200 - skin.sizeX)		xpos += skin.xspeed;		
			if (left == true && xpos > 0)						xpos -= skin.xspeed;
			if (up == true && ypos > 0)							ypos = ypos - skin.yspeed;
			if (down == true && ypos < 650 - skin.sizeY)		ypos = ypos + skin.yspeed;
			hitbox.setLocation(xpos, ypos);
		}	
	}

	public void updateMana(){
		if(mana < maxMana){
			mana += skin.manaRegen;
		}
	}

	public void shoot(){
		if (playable){
			cooldown -= 1;
			boolean ready = false;
			if(cooldown < 0) ready = true;
			if(basicFire && ready && skin.canShoot){
				fireBullet();
				cooldown = 20 / skin.frate;
			}
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
	
	

	public void updateHealth(double rawDamage){
		double damageRecieved = rawDamage - (rawDamage * skin.defense) / 100;
		if (health - damageRecieved > 0){
			health -= damageRecieved;
		}
		else{
			health = 0;
		}
	}
	
	public void healHealth(int heal){
		if (health + heal > maxHealth){
			health = maxHealth;
		}
		else{
			health += heal;
		}
	}

	public void moveTo(int x, int y, int speed){
		playable = false;
		boolean xcheck = false;
		boolean ycheck = false;
		if (xpos < x - 10) 			xpos += speed;
		else if (xpos > x + 10) 	xpos -= speed;
		else xcheck = true;
		if (ypos < y - 10) 			ypos += speed;
		else if (ypos > y + 10) 	ypos -= speed;
		else ycheck = true;
		hitbox.setLocation(xpos, ypos);
		if (xcheck && ycheck) playable = true;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) 	left = true;          
		if (key == KeyEvent.VK_RIGHT)	right = true;
		if (key == KeyEvent.VK_UP) 		up = true;
		if (key == KeyEvent.VK_DOWN)	down = true;
		if (key == KeyEvent.VK_SPACE)	basicFire = true;
		if (key == KeyEvent.VK_P){
			if (SystemData.showHitbox) SystemData.showHitbox = false;
			else SystemData.showHitbox = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)	left = false;
		if (key == KeyEvent.VK_RIGHT)	right = false;
		if (key == KeyEvent.VK_UP)		up = false;
		if (key == KeyEvent.VK_DOWN)	down = false;
		if (key == KeyEvent.VK_SPACE)	basicFire = false;
	}

	public String toString(){
		return this.ID + " : [" + health + " | " + xpos + ", " + ypos + "]";
	}

}