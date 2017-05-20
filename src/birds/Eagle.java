package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import mainGUI.SystemData;



public class Eagle extends BirdManager{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1793779944528102072L;


	public Eagle(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}


	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()*500) + 100);
			spawnList.add(new EagleBird(2, xspawn, yspawn));
		}
	}
}

class EagleBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -754602130832876678L;
	//ImageIcon imgIC = new ImageIcon("resources//bbird.png");
	static int maxhp 	= 25;
	static int damage 	= 50;
	static int xspeed 	= 5;
	static int yspeed 	= 2;
	static int size 	= 60;
	static int range 	= 750;
	static int priority = 1;
	static int score = 20;

	public EagleBird(int eID, int x, int y) {
		super(eID, x, y);
		hitbox = new Rectangle(x + 10, y +10 , 2 * size, size);
		hp = maxhp;
		dmg = damage;
		targetRange = range;
		targetPriority = priority;
		scoreValue = score;
	}

	public void draw(Graphics g, Image[] birdImages){
		g.setColor(Color.blue);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 50, ypos + 70, maxhp, 5);
		g.setColor(Color.green);
		g.fillRect(xpos + 50, ypos + 70, hp, 5);
		
		Image img = birdImages[eID];
		if (img != null) g.drawImage(img, xpos, ypos, null);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 10, ypos + 10);
		target();
	}

	public void target(){
		if(playerTarget != null){
			if (ypos > playerTarget.ypos + 10){
				ypos -= yspeed;
			}
			else if (ypos < playerTarget.ypos - 10){
				ypos += yspeed;
			}	
		}	
	}
}



