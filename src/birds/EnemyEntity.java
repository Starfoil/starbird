package birds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.EBullet;
import main.Player;
import mainGUI.SystemData;

public abstract class EnemyEntity {
	
	public int eID;
	public String name;
	public int xspeed;
	public int yspeed;
	
	public int dmg;
	public double maxHP;
	public int def;
	
	public int scoreValue;
	public int misc;
	public int sizex;
	public int sizey;
	
	// Misc
	public int targetRange;		// Target player only if > 0
	public int targetPriority;		// Bot target only if > 0
	public int zigFactor;			// Zig only if > 0
	public int healthDrop;			// Only drop health if > 0
	
	// Drops
	public int droprate;
	public int dropMinAmount;
	public int dropAmount;
	
	
	// Undefined Data
	public int xpos;
	public int ypos;
	public Rectangle hitbox;
	public double hp;
	public Player playerTarget = null;
	public String description;
	
	public ArrayList<EBullet> bullets  = new ArrayList<EBullet>();
	public int power;
	public boolean piercing;
	
	public EnemyEntity(int eID, String name, int xspeed, int yspeed, int dmg, int hp,
			int def, int scoreValue, int misc, int xsize, int ysize,
			int targetRange, int targetPriority, int zigFactor, int healthDrop,
			int droprate, int dropMinAmount, int dropAmount) {
		this.eID = eID;
		this.name = name;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.dmg = dmg;
		this.maxHP = hp;
		this.def = def;
		this.scoreValue = scoreValue;
		this.misc = misc;
		this.sizex = xsize;
		this.sizey = ysize;
		this.targetRange = targetRange;
		this.targetPriority = targetPriority;
		this.zigFactor = zigFactor;
		this.healthDrop = healthDrop;
		this.droprate = droprate;
		this.dropMinAmount = dropMinAmount;
		this.dropAmount = dropAmount;
		this.hp = maxHP;
	}
	
	public EnemyEntity(EnemyEntity b){
		this.eID = b.eID;
		this.name = b.name;
		this.xspeed = b.xspeed;
		this.yspeed = b.yspeed;
		this.dmg = b.dmg;
		this.maxHP = b.hp;
		this.def = b.def;
		this.scoreValue = b.scoreValue;
		this.misc = b.misc;
		this.sizex = b.sizex;
		this.sizey = b.sizey;
		this.targetRange = b.targetRange;
		this.targetPriority = b.targetPriority;
		this.zigFactor = b.zigFactor;
		this.healthDrop = b.healthDrop;
		this.droprate = b.droprate;
		this.dropMinAmount = b.dropMinAmount;
		this.dropAmount = b.dropAmount;
		this.hp = b.maxHP;
	}


	public void setDescription(String desc){
		this.description = desc;
	}
	
	public abstract void setSpawn(int x, int y);
	public abstract void update();
	public abstract void move();
	public abstract void draw(Graphics g);
		
	public void loseHealth(double rawDamage){
		double damageRecieved = rawDamage - (rawDamage * def) / 100;
		if(hp - damageRecieved > 0)	hp -= damageRecieved;
		else hp = 0;
	}
	
	public boolean isDead(){
		return (hp <= 0);
	}
	
	public String toString(){
		return this.name;
	}

}
