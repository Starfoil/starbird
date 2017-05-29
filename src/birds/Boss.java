package birds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import main.Bullet;
import main.EBullet;
import main.Player;
import mainGUI.SystemData;


public class Boss extends EnemyEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4413647894820602131L;
	
	private int shootFreq;
	private int bulletID;
	private int offsetX;
	private int offsetY;
	private int bulletX;
	private int bulletY;
	private int speed;
	
	private int offsetZig;
	private int zigDest;
	private boolean zigUp = false;
	private boolean zigDown = false;
	private boolean engage = false;
	private boolean targetMode = false;
	private int strafeTimer = 1000;
	private int targetTimer = 500;
	private int modeCounter = 0;
	
	public Boss(int eID, String name, int xspeed, int yspeed, int dmg, int hp,
			int def, int scoreValue, int misc, int xsize, int ysize,
			int targetRange, int targetPriority, int zigFactor, int healthDrop,
			int droprate, int dropMinAmount, int dropAmount, int shootFreq,
			int bulletID, int offsetX, int offsetY, int bulletX,
			int bulletY, int speed, int power, int piercing) {
		super(eID, name, xspeed, yspeed, dmg, hp, def, scoreValue, misc, xsize,
				ysize, targetRange, targetPriority, zigFactor, healthDrop,
				droprate, dropMinAmount, dropAmount);
		this.shootFreq = shootFreq;
		this.bulletID = bulletID;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.bulletX = bulletX;
		this.bulletY = bulletY;
		this.speed = speed;
		this.power = power;
		if(piercing == 1)	this.piercing = true;
	}
	
	public Boss(Boss b){
		super(b);
		this.shootFreq = b.shootFreq;
		this.bulletID = b.bulletID;
		this.offsetX = b.offsetX;
		this.offsetY = b.offsetY;
		this.bulletX = b.bulletX;
		this.bulletY = b.bulletY;
		this.speed = b.speed;
		this.power = b.power;
		this.piercing = b.piercing;
	}

	public void setSpawn(int x, int y){
		this.xpos = x;
		this.ypos = y;
		hitbox = new Rectangle(xpos + offsetX, ypos + offsetY, 75, 75);
	}
	
	public void update(){
		move();
	}
	
	public void checkMode(){
		modeCounter += 1;
		if (modeCounter > strafeTimer && !targetMode){
			targetMode = true;
			modeCounter = 0;
		}
		else if(modeCounter > targetTimer && targetMode){
			targetMode = false;
			modeCounter = 0;
		}
	}


	public void move() {
		if(!engage && xpos > 1200 - sizex){
			xpos -= xspeed;
		}
		else if(engage){
			checkMode();
			if(targetMode){
				boundTarget();
			}
			else{
				strafe();
			}
			shoot();
		}
		else{
			engage = true;
		}
		hitbox.setLocation(xpos + offsetX, ypos + offsetY);
	}

	public void shoot()
	{
		int rngShoot = (int) (Math.random() * shootFreq);
		if (rngShoot == 1){
			EBullet z = new EBullet(bulletID, xpos + offsetX , ypos + offsetY, 
					bulletX, bulletY, speed, power, piercing);
			bullets.add(z);
		}
		updateBullets();
	}

	public void updateBullets(){
		ArrayList<EBullet> removeList = new ArrayList<EBullet>();
		for (EBullet b : bullets){
			if(b.x < 0)	removeList.add(b);
			b.move();
		}
		bullets.removeAll(removeList);
	}
	
	public void boundTarget(){
		if(playerTarget != null){
			if (ypos + offsetY > playerTarget.ypos + playerTarget.skin.yHBOffset + 10){
				ypos -= yspeed;
			}
			else if (ypos + offsetY < playerTarget.ypos + playerTarget.skin.yHBOffset - 10){
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
		if(ypos + sizey > 700){
			strafeUp();
		}
		else if(ypos < 0){
			strafeDown();
		}
	}

	private void strafeUp(){
		offsetZig = (int) (Math.random() * zigFactor) + 100;
		zigDest = ypos - offsetZig;
		zigDown = false;
		zigUp = true;
	}
	private void strafeDown(){
		offsetZig = (int) (Math.random() * zigFactor) + 100;
		zigDest = ypos + offsetZig;
		zigDown = true;
		zigUp = false;
	}

	@Override
	public void draw(Graphics g) {
		if(engage){
			g.setColor(Color.red);
			int xhploc = (int) (sizex / 2 - maxHP / 2);
			g.fillRect(xpos + xhploc, ypos + sizey, (int) maxHP, 5);
			g.setColor(Color.green);
			g.fillRect(xpos + xhploc, ypos + sizey, (int) hp, 5);
		}
		
		g.drawImage(SystemData.birdImages[eID], xpos, ypos, null);
		
		for (Bullet b : bullets){
			b.draw(g, SystemData.bulletImages);
		}
		
		g.setColor(Color.blue);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		if (targetMode) g.drawImage(SystemData.targetIcon, xpos + sizex / 2 - 25,
				ypos + sizey + 8, null);
	}
	
}
