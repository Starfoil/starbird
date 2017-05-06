package birds;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.EnemyBullet;


public abstract class BirdEntity {

	public Image img;
	public int xpos;
	public int ypos;
	public Rectangle hitbox;
	public int hp;
	public int dmg;
	public ArrayList<EnemyBullet> bullets;
	
	public BirdEntity(int xpos, int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public abstract void move();
	
	public abstract void draw(Graphics g);
	
	public void bulletHit(int bulletDamage){
		if(hp - bulletDamage > 0){
			hp -= bulletDamage;
		}
		else{
			hp = 0;
		}
	}
	

}
