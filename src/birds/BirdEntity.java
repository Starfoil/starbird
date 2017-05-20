package birds;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import main.Bullet;
import main.Player;


public abstract class BirdEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7841407624858503953L;
	/**
	 * 
	 */
	public int eID;
	public Image img;
	public int xpos;
	public int ypos;
	public Rectangle hitbox;
	public int hp;
	public int dmg;
	public ArrayList<Bullet> bullets;
	public Player playerTarget = null;
	public int targetRange = -1;
	public boolean boss = false;
	public boolean targetable = true;
	public int targetPriority = 5;
	public int scoreValue;
	public boolean dropcoins = false;
	
	public BirdEntity(int eID, int xpos, int ypos){
		this.eID = eID;
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public abstract void move();
	
	public abstract void draw(Graphics g, Image[] birdImages);
	
	
	public void bulletHit(int bulletDamage){
		if(hp - bulletDamage > 0){
			hp -= bulletDamage;
		}
		else{
			hp = 0;
		}
	}
	
	public boolean isDead(){
		return (hp <= 0);
	}
	
	public void update(){
		move();
	}
	
	public String toString(){
		return Integer.toString(this.eID);
	}

}
