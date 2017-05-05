package enemy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Board;
import main.Player;


public class EnemyBoss extends BirdEntity{
	
	int hp;
	int dhp;
	ArrayList BossBullets;
	int i;
	int iy;

	public EnemyBoss(int x, int y) {
		super(x,250,0);
		hb = new Rectangle(x+10,y+75, 75, 75);
		ImageIcon newE = new ImageIcon(getClass().getResource("/resources/BossBird.png"));
        img = newE.getImage();
        damage=5;
        extraspeed=-4;
        hp=50;
        dhp=50;
        i = (int)(Math.random()*500);
        iy = 250;
        BossBullets = new ArrayList();
	}
	
	public void draw(Graphics g){
		moveBoss();
		score();
		if(!isDead()){
		g.setColor(Color.blue);
		g.drawImage(img, x, y, null);
		//g.drawRect(hb.x,hb.y,hb.width,hb.height);
		
		g.setColor(Color.red);
    	g.fillRect(x-15,y+180,dhp*5,10);
    		
    	g.setColor(Color.green);
    	g.fillRect(x-15,y+180,hp*5,10);
    	
		for (int w = 0; w < BossBullets.size(); w++)
		{
			BossBullet m = (BossBullet) BossBullets.get(w);
			g.drawImage(m.getImage(), m.getX(), m.getY(), null);
		}
		}
		
	}
	
	
	public void moveBoss(){
		if(!isDead()){
		if(collide()==true){
			if(Player.healthlength>0){
				if(Player.healthlength-damage>0){
				Player.healthlength = Player.healthlength - damage;
				}else{
					Player.healthlength=0;
				}
			}
			
		}

		for (int w = 0; w < BossBullets.size(); w++)
		{
			BossBullet m = (BossBullet) BossBullets.get(w);
			if (m.getVisible() == true)
			{
				m.move();
        		if(m.hitbox.intersects(Player.hitbox)){
    			if(Player.healthlength>0){
    				if(Player.healthlength-damage>0){
    				Player.healthlength = Player.healthlength - damage;
    				}else{
    					Player.healthlength=0;
    				}
    			}
    		}
			}
			else {
				BossBullets.remove(w);	
			}
		}
		
		if(x<Player.x-750){
			int rng = (int) (Math.random()*50);
			zigzag();
			if(rng==1){
				fireball();
			}
		}
		else{
			x=x-Player.dx-extraspeed;
			hb.x=hb.x-Player.dx-extraspeed;
		}
		
		
		hb.y=y+75;
		movey();
		}
		if(isDead()){
			img=null;
		}
	}
	
	public void zigzag(){
		int dest = iy+i;
		if(y<dest){
			y++;
			if(y==dest){
				i = (int)(Math.random()*-300);
			}
		}
		else if(y>dest){
			y--;
			if(y==dest){
				i = (int)(Math.random()*300);
			}
		}
		
		
	}
	
	public void fireball(){
			BossBullet z = new BossBullet(850, (y+100));
			BossBullets.add(z);
	}

	public boolean isDead(){
		if(hp<=0){
		return true;
		}
		else{
			return false;
		}
	}
	
	public void score(){
		if(isDead()){
			Board.bossStatus=true;
			
			if(point){
			Board.length=Player.x;
			//Board.ee = new ImageIcon(Board.class.getResource("/resources/menubird1.png"));
			//Board.imgee = Board.ee.getImage();
			Board.score=Board.score+2500;
			Player.healthlength=0;
			point=false;
			}
		}
	}
	
}
