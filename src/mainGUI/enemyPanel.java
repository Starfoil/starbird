package mainGUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import birds.Boss;
import birds.EnemyEntity;
import birds.EnemyEntity;
import main.Skin;

public class enemyPanel extends GUIPanel{

	DefaultListModel<EnemyEntity> enemies = new DefaultListModel<EnemyEntity>();
	
	JLabel currentName;
	JLabel currentX;
	JLabel currentY;
	JLabel currentDmg;
	JLabel currentHp;
	JLabel currentDef;
	
	JLabel currentScr;
	JLabel currentDR;
	JLabel currentDA;
	
	JLabel currentImage;
	JTextArea currentDesc;
	
	Font font14 = new Font("Georgia", Font.PLAIN, 14);
	Font font18 = new Font("Georgia", Font.PLAIN, 18);
	Font font20 = new Font("Georgia", Font.PLAIN, 20);
	Font font24 = new Font("Georgia", Font.PLAIN, 24);
	Font font32 = new Font("Georgia", Font.PLAIN, 32);
	Font font40 = new Font("Georgia", Font.PLAIN, 40);

	public enemyPanel(){
		super();
		backgroundIMG = SystemData.enemyBG;
		addScrollList("Enemies", enemies, 800, 50, 300, 500);
		EnemyEntity b = SystemData.allEnemies.get(0);
		
		currentName = addLabel("", 	font40,	325, 50);
		currentImage = addLabel(null, 170, 0, 150, 150);
		
		currentX = addLabel("", 	font24, 75, 150);
		currentY = addLabel("", 	font24, 75, 200);
		currentDmg = addLabel("", 	font24, 75, 250);
		currentHp = addLabel("", 	font24, 75, 300);
		currentDef = addLabel("", 	font24, 75, 350);
		
		currentScr = addLabel("", 	font32, 450, 200);
		
		currentDR = addLabel("", 	font18, 450, 275);
		currentDA = addLabel("", 	font18, 450, 315);

		currentDesc = addTextbox("", font14, 50, 420, 600, 400);
		
		if (b != null) updateLabels(b);
		updateList();
	}

	private JScrollPane addScrollList(String label, DefaultListModel<EnemyEntity> inv, int x, int y, int width, int height){
		JList<EnemyEntity> inventoryList = new JList<EnemyEntity>(inv);
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryList.addListSelectionListener(new ListSelectionListener(){
			@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent ev){
				EnemyEntity s =(((JList<EnemyEntity>) ev.getSource()).getSelectedValue());
				if (s != null) updateLabels(s);
			}});
		inventoryList.setFont(font20);
		JScrollPane inventoryListScroll = new JScrollPane(inventoryList);
		inventoryListScroll.setBounds(x, y, width, height);
		JLabel inventoryLabel = new JLabel(label);
		inventoryLabel.setBounds(x + width / 2 - 30, y - 25, width, 25);
		add(inventoryListScroll);
		add(inventoryLabel);
		return inventoryListScroll;
	}

	private void updateLabels(EnemyEntity b){
		currentName.setText("" + b.name);
		currentX.setText("X Speed : " + b.xspeed);
		currentY.setText("Y Speed : " + b.yspeed);
		if(b instanceof Boss){
			if(b.piercing)	currentDmg.setText("Damage : " + b.power + "p");
			else			currentDmg.setText("Damage : " + b.power);
		}
		else currentDmg.setText("Damage : " + b.dmg);
		currentHp.setText("Health : " + b.maxHP);
		currentDef.setText("Defense : " + b.def);
		currentScr.setText("Score : " + b.scoreValue);
		currentDR.setText("Coin Drop Rate : " + b.droprate + "%");
		currentDA.setText("Coin Drop Amount : [" + b.dropMinAmount + " - " + (b.dropMinAmount + b.dropAmount) + "]");
		currentImage.setIcon(new ImageIcon(SystemData.birdImages[b.eID]));
		currentDesc.setText(b.description);
	}

	private void updateList(){
		enemies.removeAllElements();
		for (EnemyEntity b : SystemData.allEnemies){
			enemies.addElement(b);
		}
	}
}
