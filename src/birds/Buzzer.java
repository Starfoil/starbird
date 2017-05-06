package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Buzzer extends BirdManager{

	public Buzzer(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}
	
	public void spawn(){
		for(int i=0; i < spawnSize; i++){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = ((int)(Math.random() * 800));
			spawnList.add(new BuzzerBird(xspawn, yspawn));
		}
	}
}

class BuzzerBird extends BirdEntity{

	ImageIcon imgIC = new ImageIcon("resources//Bee.png");
	static int maxhp 	= 10;
	static int damage 	= 15;
	static int xspeed 	= 6;
	static int yspeed 	= 2;
	static int size 	= 50;
	static int zigOffset = 180;
	
	private int offsetZig;
	private int zigDest;
	private boolean zigUp = false;
	private boolean zigDown = false;

	public BuzzerBird(int x, int y) {
		super(x, y);
		img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y + 10, size, size);
		hp = maxhp;
		dmg = damage;
	}

	public void draw(Graphics g){
		g.setColor(Color.blue);
		g.drawImage(img, xpos, ypos, null);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		g.setColor(Color.red);
		g.fillRect(xpos + 30, ypos + 60, maxhp, 5);

		g.setColor(Color.green);
		g.fillRect(xpos + 30, ypos + 60, hp, 5);

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



