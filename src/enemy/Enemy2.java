package enemy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import birds.BirdEntity;
import main.Board;


public class Enemy2 extends BirdEntity {

	public Enemy2(int x, int y) {
		super(x, y, 75);
		ImageIcon newE = new ImageIcon("bbird.png");
        img = newE.getImage();
        damage=5;
        extraspeed=4;
        dhp=50;
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
    	g.fillRect(x+30,y+70,dhp,5);
    	
    	g.setColor(Color.green);
    	g.fillRect(x+30,y+70,hp,5);
		}
	}
	
	public void hit(){
		if(hittaken==2){
			Board.score=Board.score+3;
			hittaken=999;
		}
	}
	
}
