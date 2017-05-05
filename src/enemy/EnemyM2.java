package enemy;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class EnemyM2 {
	
	public int amount;
	public List<Enemy2> enemies = new ArrayList<Enemy2>();
	
	public EnemyM2(int a){
		amount = a;
		spawn();
	}
	
	public void draw(Graphics g){
		for (Enemy2 e : enemies){
			e.draw(g);
		}
	}

	private void spawn(){
		int s = enemies.size();
		if(s<amount){
			for(int i=0; i < amount-s; i++){
				enemies.add(new Enemy2((int)(Math.random()*60000)+15000,(int)(Math.random()*500)+100));
			}
		}
		else if(s>amount){
			for(int i=0; i < s-amount; i++){
				enemies.get(i);
			}
		}
	}
}
