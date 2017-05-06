package main;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Skin {
	
	int xspeed;	
	int yspeed;	
	int playerSize;
	ImageIcon II;
	Image img;
	
	int xHBOffset = 0;
	int yHBOffset = 0;
	
	int manaRegen = 1;
	
	public Skin(String skinID, int xspeed, int yspeed, int size){
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.playerSize = size;
		II = new ImageIcon("resources\\"+skinID+".png");
		img = II.getImage();
				
	}
	
	public void setOffset(int x, int y){
		this.xHBOffset = x;
		this.yHBOffset = y;
	}
	
	public void setManaRegen(int mana){
		this.manaRegen = mana;
	}

}
