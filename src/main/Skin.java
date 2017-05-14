package main;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Skin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8703890902292938684L;
	int skinID;
	int xspeed;	
	int yspeed;	
	int playerSize;
	public ImageIcon II;
	public Image img;
	
	int xHBOffset = 0;
	int yHBOffset = 0;
	
	int manaRegen = 1;
	
	public Skin(int skinID, int xspeed, int yspeed, int size){
		this.skinID = skinID;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.playerSize = size;
	}
	
	public void setOffset(int x, int y){
		this.xHBOffset = x;
		this.yHBOffset = y;
	}
	
	public void setManaRegen(int mana){
		this.manaRegen = mana;
	}

}
