package birds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import main.Bullet;
import main.Player;
import mainGUI.SystemData;


public class EnemyUnit extends EnemyEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7841407624858503953L;
	/**
	 * 
	 */
	
	private int zigDest;
	private boolean zigUp = false;
	private boolean zigDown = false;
	private boolean dive = false;

	public EnemyUnit(int eID, String name, int xspeed, int yspeed, int dmg,
			int hp, int def, int scoreValue, int misc, int xsize, int ysize,
			int targetRange, int targetPriority, int zigFactor, int healthDrop,
			int droprate, int dropMinAmount, int dropAmount) {
		super(eID, name, xspeed, yspeed, dmg, hp, def, scoreValue, misc, xsize, ysize,
				targetRange, targetPriority, zigFactor, healthDrop, droprate,
				dropMinAmount, dropAmount);
		// TODO Auto-generated constructor stub
	}
	
	public EnemyUnit(EnemyEntity b){
		super(b);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		if (SystemData.showHitbox) g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		drawHPBar(g);
		
		g.drawImage(SystemData.birdImages[eID], xpos, ypos, null);
	}

	public void move(){
		if (zigFactor > 0){
			zigzag();
		}
		else if (zigFactor == -1){
			diveTarget();
		}
		if (targetRange > 0 && zigFactor != -1){
			boundTarget();
		}
		xpos -= xspeed;
		hitbox.setLocation(xpos - xspeed, ypos);
	}
			
	public void boundTarget(){
		if(playerTarget != null){
			boolean isOverTarget = ypos > playerTarget.ypos + 10;
			boolean isUnderTarget = ypos < playerTarget.ypos - 10;
			if (isOverTarget){
				ypos -= yspeed;
			}
			else if (isUnderTarget){
				ypos += yspeed;
			}	
		}	
	}
	
	private void diveTarget(){
		if (playerTarget != null){
			dive = true;
		}	
		if (dive){
			ypos += yspeed;
		}
	}
	
	private void zigzag(){
		if(!zigUp && !zigDown){
			int offsetZig = (int) (Math.random() * zigFactor);
			zigDest = ypos + offsetZig;
			zigDown = true;
		}
		else if(zigDown){
			ypos += yspeed;
			if(ypos > zigDest){
				int offsetZig = (int) (Math.random() * zigFactor);
				zigDest = ypos - offsetZig;
				zigDown = false;
				zigUp = true;
			}
		}
		else if(zigUp){
			ypos -= yspeed;
			if(ypos < zigDest){
				int offsetZig = (int) (Math.random() * zigFactor);
				zigDest = ypos + offsetZig;
				zigDown = true;
				zigUp = false;
			}
		}
	}
	
	public void setSpawn(int x, int y){
		this.xpos = x;
		this.ypos = y;
		this.hitbox = new Rectangle(x, y, sizex, sizey);
	}
	

}
