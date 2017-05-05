package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import birds.BirdEntity;
import main.Board;


public class Enemy1 extends BirdEntity {

	public Enemy1(int x, int y) {
		super(x, y, 90);
		ImageIcon newE = new ImageIcon("gbird.png");
        img = newE.getImage();
        damage=3;
        extraspeed=1;
        dhp=100;
        hp=dhp;
	}

	@Override
	public void draw(Graphics g) {
		move();
		hit();
		if(!isDead()){
		g.setColor(Color.blue);
		g.drawImage(img, x, y, null);
		g.drawRect(hb.x,hb.y,hb.width,hb.height);
		
		g.setColor(Color.red);
    	g.fillRect(x+10,y+80,dhp,5);
    	
    	g.setColor(Color.green);
    	g.fillRect(x+10,y+80,hp,5);
		}
	}
	
	public void hit(){
		if(hittaken==4){
			Board.score=Board.score+5;
			hittaken=999;
		}
	}

}
