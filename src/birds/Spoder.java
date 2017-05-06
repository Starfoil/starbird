package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Spoder extends BirdManager{

	public Spoder(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()*500) + 100);
			spawnList.add(new SpoderBird(xspawn, yspawn));
		}
	}
}

class SpoderBird extends BirdEntity{

	ImageIcon imgIC = new ImageIcon("resources//poibird.png");
	static int maxhp 	= 120;
	static int damage 	= 75;
	static int xspeed 	= 2;
	static int yspeed 	= 0;
	static int size 	= 120;

	public SpoderBird(int x, int y) {
		super(x, y);
		img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y, size, size);
		hp = maxhp;
		dmg = damage;
	}

	public void draw(Graphics g){
		g.setColor(Color.blue);
		g.drawImage(img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 10, ypos + 120, maxhp, 5);

		g.setColor(Color.green);
		g.fillRect(xpos + 10, ypos + 120, hp, 5);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 10, ypos);
	}

}



