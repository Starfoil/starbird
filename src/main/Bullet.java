package main;

import java.awt.*;
import java.io.Serializable;

public class Bullet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5709034706644612873L;
	
	public int bulletID;
	public int x;
	public int y;
	public int speed;
	public int size;
	public int damage;
	
	public boolean piercing = false;
	public boolean explosive = false;
	
	public boolean live;
	public int BPID;

	public Image img;
	public Rectangle hitbox;

	public Bullet(int ID, int startX, int startY, int bulletSpeed, int bulletSize, int bulletDamage)
	{	
		this.bulletID = ID;
		x = startX;
		y = startY;
		speed = bulletSpeed;
		size = bulletSize;
		damage = bulletDamage;
		hitbox = new Rectangle(x, y, bulletSize, bulletSize);
		live = true;
		BPID = (int) ((startX + startY) * Math.random()) * 100;
	}

	public void move(){
		x += speed;
		hitbox.setLocation(x, y);
	}

	public void draw(Graphics g, Image[] bulletImages){
		Image img = bulletImages[bulletID];
		if (img != null) g.drawImage(img, x, y, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	
	public String toString(){
		return Integer.toString(BPID);
	}


}