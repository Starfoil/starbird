package main;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import mainGUI.SystemData;

public class Bullet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5709034706644612873L;
	
	public int bulletID;
	public int x;
	public int y;
	public int speed;
	public int damage;
	
	public boolean piercing;
	
	public boolean live;
	public int BPID;

	public Image img;
	public Rectangle hitbox;

	public Bullet(int ID, int startX, int startY, int bulletSizeX, int bulletSizeY,
			int bulletSpeed, int bulletDamage, boolean piercing)
	{	
		this.bulletID = ID;
		x = startX;
		y = startY;
		speed = bulletSpeed;
		damage = bulletDamage;
		this.piercing = piercing;
		hitbox = new Rectangle(x, y, bulletSizeX, bulletSizeY);
				
		live = true;
		
		// Random ID
		BPID = (int) (Math.random() * 100000000);
	}

	public void move(){
		x += speed;
		hitbox.setLocation(x, y);
	}

	public void draw(Graphics g, Image[] bulletImages){
		Image img = bulletImages[bulletID];
		if (img != null) g.drawImage(img, x, y, null);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	
	public String toString(){
		return Integer.toString(BPID);
	}


}