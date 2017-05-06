package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.Player;


public class Eagle extends BirdManager{


	public Eagle(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}


	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()*500) + 100);
			spawnList.add(new EagleBird(xspawn, yspawn));
		}
	}
}

class EagleBird extends BirdEntity{

	ImageIcon imgIC = new ImageIcon("resources//bbird.png");
	static int maxhp 	= 25;
	static int damage 	= 50;
	static int xspeed 	= 6;
	static int yspeed 	= 2;
	static int size 	= 60;

	public EagleBird(int x, int y) {
		super(x, y);
		img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y +10 , 2 * size, size);
		hp = maxhp;
		dmg = damage;
	}

	public void draw(Graphics g){
		g.setColor(Color.blue);
		g.drawImage(img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 50, ypos + 70, maxhp, 5);

		g.setColor(Color.green);
		g.fillRect(xpos + 50, ypos + 70, hp, 5);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 10, ypos + 10);
		target();
		//System.out.println(xpos);
	}

	public void target(){
		if ((xpos - Player.xpos) < 500 && (xpos - Player.xpos) > -150){
			if (ypos > Player.ypos + 10){
				ypos -= yspeed;
			}
			else if (ypos < Player.ypos - 10){
				ypos += yspeed;
			}	
		}
	}
}



