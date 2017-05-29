package main;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Skin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8703890902292938684L;
	
	public int skinID;
	public String name;
	
	public int xspeed;	
	public int yspeed;	
	public int power;
	public boolean piercing = false;
	
	public int fspeed;
	public int frate;
	public int defense;
	public double manaRegen;
	public int sizeX;
	public int sizeY;
	
	public boolean canShoot = true;
	public int bulletID;
	public int bulletXSize;
	public int bulletYSize;
	public int manaCost;
	
	
	public String description;
	
	public ImageIcon II;
	public Image img;
	
	public int xHBOffset = 0;
	public int yHBOffset = 0;
	
	
	
	
	
	public Skin(int skinID, String name, int xspeed, int yspeed, int power,
			int piercing, int fspeed, int frate, int defense, double manaRegen, int sizeX, int sizeY
			) {
		this.skinID = skinID;
		this.name = name;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.power = power;
		if (piercing == 1) this.piercing = true;
		this.fspeed = fspeed;
		this.frate = frate;
		this.defense = defense;
		this.manaRegen = manaRegen;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public void setFire(int shootMode, int bulletID, int bulletX, int bulletY, int manaCost){
		if (shootMode == 0)		canShoot = false;
		this.bulletID = bulletID;
		this.bulletXSize = bulletX;
		this.bulletYSize = bulletY;
		this.manaCost = manaCost;
	}

	public void setOffset(int x, int y){
		this.xHBOffset = x;
		this.yHBOffset = y;
	}
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public String toString(){
		return this.name;
	}

}
