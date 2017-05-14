package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Hatter extends BirdManager{

	public Hatter(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()* 500) + 100);
			spawnList.add(new HatterBird(5, xspawn, yspawn));
		}
	}
}

class HatterBird extends BirdEntity{

	//ImageIcon imgIC = new ImageIcon("resources//dfbird.png");
	static int maxhp 	= 30;
	static int damage 	= 25;
	static int xspeed 	= 1;
	static int yspeed 	= 0;
	static int size 	= 80;

	public HatterBird(int eID, int x, int y) {
		super(eID, x, y);
		//img = imgIC.getImage();
		hitbox = new Rectangle(x, y, size, size);
		hp = maxhp;
		dmg = damage;
	}

	public void draw(Graphics g, Image[] birdImages){
		g.setColor(Color.blue);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 30, ypos + 60, maxhp, 5);
		g.setColor(Color.green);
		g.fillRect(xpos + 30, ypos + 60, hp, 5);
		
		Image img = birdImages[eID];
		if (img != null) g.drawImage(img, xpos, ypos, null);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 15, ypos);
	}

}



