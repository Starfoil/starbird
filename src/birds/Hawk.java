package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import mainGUI.SystemData;


public class Hawk extends BirdManager{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7014241707851576871L;

	public Hawk(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()*500) + 100);
			spawnList.add(new HawkBird(7, xspawn, yspawn));
		}
	}
}

class HawkBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7174888209458480602L;
	//ImageIcon imgIC = new ImageIcon("resources//bbird2.png");
	static int maxhp 	= 10;
	static int damage 	= 30;
	static int xspeed 	= 8;
	static int yspeed 	= 0;
	static int size 	= 50;
	static int priority = 2;
	static int score = 15;

	public HawkBird(int eID, int x, int y) {
		super(eID, x, y);
		//img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y +10 , 2 * size, size);
		hp = maxhp;
		dmg = damage;
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
	}

}



