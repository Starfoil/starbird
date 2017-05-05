package main;

import java.awt.*;
import javax.swing.*;

public class Bullet {
	int x;
	int y;
	int speed;
	int size;
	int damage;
	
	boolean piercing = false;
	boolean explosive = false;

	Image img;
	Rectangle hitbox;

	public Bullet(int startX, int startY, int bulletSpeed, int bulletSize, int bulletDamage)
	{
		x = startX;
		y = startY;
		speed = bulletSpeed;
		size = bulletSize;
		damage = bulletDamage;
		hitbox = new Rectangle(x, y, bulletSize, bulletSize);
	}
	
	public void setImage(String fileName){
		ImageIcon newBullet = new ImageIcon(fileName);
		img = newBullet.getImage();
	}

	public void move(){
		x += speed;
		hitbox.setLocation(x, y);
	}

	public void draw(Graphics g){
		g.drawImage(img, x, y, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}


}