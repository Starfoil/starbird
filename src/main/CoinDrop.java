package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import mainGUI.SystemData;

public class CoinDrop {
	
	private static final long serialVersionUID = 1L;
	int xpos;
	int ypos;
	
	int xspeed 	= 1;
	int size 	= 50;
	
	Rectangle hitbox;
	
	int amountDrop;

	public CoinDrop(int amountDrop, int x, int y) {
		xpos = x;
		ypos = y;
		hitbox = new Rectangle(x, y, size, size);
		this.amountDrop = amountDrop;
	}

	public void draw(Graphics g, Image coinImage){
		g.setColor(Color.blue);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		if (coinImage != null) g.drawImage(coinImage, xpos, ypos, null);
		
	}

	public void update() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed, ypos);
	}

	
}
