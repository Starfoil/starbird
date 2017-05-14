package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;



public class Bluejay extends BirdManager{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3076878873768498403L;



	public Bluejay(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()*500) + 100);
			spawnList.add(new BluejayBird(0, xspawn, yspawn));
		}
	}

	
	
}

class BluejayBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ImageIcon imgIC = new ImageIcon("resources//ybird.png");
	static int maxhp 	= 30;
	static int damage 	= 25;
	static int xspeed 	= 1;
	static int yspeed 	= 0;
	static int size 	= 60;
	static int priority = 3;

	public BluejayBird(int eID, int x, int y) {
		super(eID, x, y);
		//img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y + 10, size, size);
		hp = maxhp;
		dmg = damage;
		targetPriority = priority;
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
		hitbox.setLocation(xpos - xspeed + 10, ypos + 10);
	}

}



