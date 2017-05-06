package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.Player;


public class Bomber extends BirdManager{

	public Bomber(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()* 200) - 500);
			spawnList.add(new BomberBird(xspawn, yspawn));
		}
	}
}

class BomberBird extends BirdEntity{

	ImageIcon imgIC = new ImageIcon("resources//pilotbird.png");
	static int maxhp 	= 30;
	static int damage 	= 75;
	static int xspeed 	= 6;
	static int yspeed 	= 4;
	static int size 	= 90;

	public BomberBird(int x, int y) {
		super(x, y);
		img = imgIC.getImage();
		hitbox = new Rectangle(x, y, size, size);
		hp = maxhp;
		dmg = damage;
	}

	public void draw(Graphics g){
		g.setColor(Color.blue);
		g.drawImage(img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 40, ypos + 90, maxhp, 5);

		g.setColor(Color.green);
		g.fillRect(xpos + 40, ypos + 90, hp, 5);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 20, ypos + 10);
		target();
	}
	
	public void target(){
		if ((xpos - Player.xpos) < 1200){
				ypos += yspeed;
		}
	}

}



