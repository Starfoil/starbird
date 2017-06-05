package mainGUI;

import birds.EnemyEntity;

public class enemyUpdateList implements UpdateList {
	public void updateList(){
		enemyPanel.enemies.removeAllElements();
		for (EnemyEntity b : SystemData.allEnemies){
			enemyPanel.enemies.addElement(b);
		}
	}
}