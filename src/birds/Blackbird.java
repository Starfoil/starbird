package birds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Bullet;
import main.EnemyBullet;


public class Blackbird extends BirdManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4176426524354413155L;

	public Blackbird(int spawnSize, int spawnDeviation, int spawnDistance){
		super(spawnSize, spawnDeviation, spawnDistance);
		spawn();
	}

	public void spawn(){
			int xspawn = ((int)(Math.random() * spawnDeviation) + spawnDistance);
			int yspawn = 120;
			spawnList.add(new BlackbirdBird(8, xspawn, yspawn));
	}
}

class BlackbirdBird extends BirdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5307047519957227432L;
	//ImageIcon imgIC = new ImageIcon("resources//BossBird.png");
	static final int maxhp 	= 750;
	static final int damage 	= 999;
	static final int xspeed 	= 5;
	static final int yspeed 	= 3;
	static final int size 	= 150;
	static final int zigOffset = 300;
	static final int shootFreq = 30;
	static final int modeFreq = 1000;
	static final int range = 1200;

	private int offsetZig;
	private int zigDest;
	private boolean zigUp = false;
	private boolean zigDown = false;

	private boolean engage = false;
	private boolean target = false;

	private int modeCounter = 0;

	
	public BlackbirdBird(int eID, int x, int y) {
		super(eID, x, y);
		//img = imgIC.getImage();
		hitbox = new Rectangle(x + 10, y + 10, size, size);
		hp = maxhp;
		dmg = damage;
		bullets = new ArrayList<Bullet>();
		targetRange = range;
		boss = true;
		//targetable = false;
		targetPriority = 0;
	}

	public void draw(Graphics g, Image[] birdImages){
		g.setColor(Color.blue);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		
		if(engage){
			g.setColor(Color.red);
			g.fillRect(230, 75, maxhp, 10);
			g.setColor(Color.green);
			g.fillRect(230, 75, hp, 10);
		}
		
		Image img = birdImages[eID];
		if (img != null) g.drawImage(img, xpos, ypos, null);

		for (Bullet b : bullets){
			b.draw(g);
		}

	}

	public void checkMode(){
		modeCounter += 1;
		if (modeCounter > modeFreq && modeCounter <= 2 * modeFreq){
			target = true;
		}
		else if(modeCounter > 1000){
			target = false;
			modeCounter = 0;
		}
		//System.out.println(modeCounter + " " + target);
	}


	public void move() {
		if(!engage && xpos > 900){
			xpos -= xspeed;
		}
		else if(engage){
			checkMode();
			if(target){
				target();
			}
			else{
				strafe();
			}
			shoot();
		}
		else{
			engage = true;
		}
		hitbox.setLocation(xpos - xspeed + 10, ypos + 10);
	}

	public void shoot()
	{
		int rngShoot = (int) (Math.random() * shootFreq);
		if (rngShoot == 1){
			EnemyBullet z = new EnemyBullet(xpos + 10, ypos + 60, -12, 40, 20);
			bullets.add(z);
			z.setImage("resources\\bullet2.png");
		}
		updateBullets();
	}

	public void updateBullets(){
		ArrayList<EnemyBullet> removeList = new ArrayList<EnemyBullet>();
		for (EnemyBullet b : bullets){
			if(b.x < 0)	removeList.add(b);
			b.move();
		}
		bullets.removeAll(removeList);
	}
	

	public void target(){
		if (engage && playerTarget != null){
			if (ypos > playerTarget.ypos + 10){
				ypos -= yspeed;
			}
			else if (ypos < playerTarget.ypos - 10){
				ypos += yspeed;
			}	
		}
	}

	public void strafe(){
		strafeCheckBounds();
		if(!zigUp && !zigDown){
			strafeDown();
		}
		else if(zigDown){
			ypos += yspeed;
			if(ypos > zigDest){
				strafeUp();
			}
		}
		else if(zigUp){
			ypos -= yspeed;
			if(ypos < zigDest){
				strafeDown();
			}
		}
	}

	public void strafeCheckBounds(){
		if(ypos > 620){
			strafeUp();
		}
		else if(ypos < 0){
			strafeDown();
		}
	}

	private void strafeUp(){
		offsetZig = (int) (Math.random() * zigOffset) + 100;
		zigDest = ypos - offsetZig;
		zigDown = false;
		zigUp = true;
	}
	private void strafeDown(){
		offsetZig = (int) (Math.random() * zigOffset) + 100;
		zigDest = ypos + offsetZig;
		zigDown = true;
		zigUp = false;
	}

}



