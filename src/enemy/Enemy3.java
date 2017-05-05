package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import birds.BirdEntity;
import main.Board;


public class Enemy3 extends BirdEntity {

	public Enemy3(int x, int y) {
		super(x, y, 60);
		ImageIcon newE = new ImageIcon("bbird2.png");
		yspeed=1;
        img = newE.getImage();
        damage=4;
        extraspeed=6;
        dhp=25;
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
    	g.fillRect(x+45,y+70,dhp,5);
    	
    	g.setColor(Color.green);
    	g.fillRect(x+45,y+70,hp,5);
		}
	}
	
	public void hit(){
		if(hittaken==1){
			Board.score=Board.score+2;
			hittaken=999;
		}
	}
	
}
