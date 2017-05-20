package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import mainGUI.SystemData;


public class Buzzer extends BirdManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Buzzer(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random() * 800));
			spawnList.add(new BuzzerBird(3, xspawn, yspawn));
		}
	}
}

class BuzzerBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ImageIcon imgIC = new ImageIcon("resources//Bee.png");
	static int maxhp 	= 10;
	static int damage 	= 15;
	static int xspeed 	= 6;
	static int yspeed 	= 2;
	static int size 	= 50;
	static int zigOffset = 180;
	static int score = 5;
	
	private int offsetZig;
	private int zigDest;
	private boolean zigUp = false;
	private boolean zigDown = false;

	public BuzzerBird(int eID, int x, int y) {
		super(eID, x, y);
		hitbox = new Rectangle(x + 10, y + 10, size, size);
		hp = maxhp;
		dmg = damage;
		targetable = false;
		scoreValue = score;
	}

	public void draw(Graphics g, Image[] birdImages){
		g.setColor(Color.blue);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 30, ypos + 60, maxhp, 5);
		g.setColor(Color.green);
		g.fillRect(xpos + 30, ypos + 60, hp, 5);
		
		Image img = birdImages[eID];
		if (img != null) g.drawImage(img, xpos, ypos, null);

	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 10, ypos + 10);
		zigzag();
	}
	
	public void zigzag(){
		if(!zigUp && !zigDown){
			offsetZig = (int) (Math.random() * zigOffset);
			zigDest = ypos + offsetZig;
			zigDown = true;
		}
		else if(zigDown){
			ypos += yspeed;
			if(ypos > zigDest){
				offsetZig = (int) (Math.random() * zigOffset);
				zigDest = ypos - offsetZig;
				zigDown = false;
				zigUp = true;
			}
		}
		else if(zigUp){
			ypos -= yspeed;
			if(ypos < zigDest){
				offsetZig = (int) (Math.random() * zigOffset);
				zigDest = ypos + offsetZig;
				zigDown = true;
				zigUp = false;
			}
		}
	}

}



