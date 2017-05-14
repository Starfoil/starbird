package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;



public class Bomber extends BirdManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4470848347146612637L;

	public Bomber(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random()* 200) - 500);
			spawnList.add(new BomberBird(4, xspawn, yspawn));
		}
	}
}

class BomberBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4222065195134155328L;
	//ImageIcon imgIC = new ImageIcon("resources//pilotbird.png");
	static int maxhp 	= 30;
	static int damage 	= 75;
	static int xspeed 	= 4;
	static int yspeed 	= 3;
	static int size 	= 90;
	static int range 	= 1000;
	
	private boolean dive = false;

	public BomberBird(int eID, int x, int y) {
		super(eID, x, y);
		hitbox = new Rectangle(x, y, size, size);
		hp = maxhp;
		dmg = damage;
		targetRange = range;
		targetable = false;
	}

	public void draw(Graphics g, Image[] birdImages){
		g.setColor(Color.blue);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 40, ypos + 90, maxhp, 5);
		g.setColor(Color.green);
		g.fillRect(xpos + 40, ypos + 90, hp, 5);
		
		Image img = birdImages[eID];
		if (img != null) g.drawImage(img, xpos, ypos, null);
	}

	public void move() {
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed + 20, ypos + 10);
		diveTarget();
	}
	
	public void diveTarget(){
		if (playerTarget != null){
			dive = true;
		}	
		if (dive){
			ypos += yspeed;
		}
	}

}



