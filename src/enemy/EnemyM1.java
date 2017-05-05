package enemy;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class EnemyM1 {
	
	public int amount;
	public List<Enemy1> enemies = new ArrayList<Enemy1>();
	
	public EnemyM1(int a){
		amount = a;
		spawn();
	}
	
	public void draw(Graphics g){
		for (Enemy1 e : enemies){
			e.draw(g);
		}
	}

	private void spawn(){
		int s = enemies.size();
		if(s<amount){
			for(int i=0; i < amount-s; i++){
				enemies.add(new Enemy1((int)(Math.random()*50000)+5000,(int)(Math.random()*500)+100));
			}
		}
		else if(s>amount){
			for(int i=0; i < s-amount; i++){
				enemies.get(i);
			}
		}
	}
}
