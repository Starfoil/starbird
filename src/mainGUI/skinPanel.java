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

import main.Skin;

public class skinPanel extends GUIPanel{

	DefaultListModel<Skin> skins = new DefaultListModel<Skin>();
	
	JLabel currentSkinName;
	JLabel currentSkinX;
	JLabel currentSkinY;
	JLabel currentSkinPow;
	JLabel currentSkinPrc;
	JLabel currentSkinCr;
	JLabel currentSkinCp;
	JLabel currentSkinDef;
	JLabel currentSkinMr;
	JLabel currentSkinSize;
	JLabel currentSkinImage;
	JLabel bulletImage;
	JTextArea currentSkinDesc;
	
	JLabel skinSelectX;
	JLabel skinSelectY;
	JLabel skinSelectPow;
	JLabel skinSelectPrc;
	JLabel skinSelectCr;
	JLabel skinSelectCp;
	JLabel skinSelectDef;
	JLabel skinSelectMr;
	JLabel skinSelectSize;
	JLabel skinSelectImage;
	
	Font font14 = new Font("Georgia", Font.PLAIN, 14);
	Font font16 = new Font("Georgia", Font.PLAIN, 16);
	Font font18 = new Font("Georgia", Font.PLAIN, 18);
	Font font24 = new Font("Georgia", Font.PLAIN, 24);
	Font font32 = new Font("Georgia", Font.PLAIN, 32);

	public skinPanel(){
		super();
		backgroundIMG = SystemData.skinBG;
		addScrollList("Skins", skins, 900, 50, 250, 200);
		
		currentSkinName = addLabel(PlayerData.currentSkin.name, font32, 25, 25);
		currentSkinImage = addLabel(SystemData.playerImages[PlayerData.currentSkin.skinID], 20, 60, 150, 150);
		currentSkinX = addLabel("X Speed : " + PlayerData.currentSkin.xspeed, font24, 275, 25);
		currentSkinY = addLabel("Y Speed : " + PlayerData.currentSkin.yspeed, font24, 275, 75);
		currentSkinPow = addLabel("Power : " + PlayerData.currentSkin.power, font24, 275, 125);
		currentSkinPrc = addLabel("Piercing : " + PlayerData.currentSkin.piercing, font24, 275, 175);
		currentSkinCr = addLabel("Fire Speed : " + PlayerData.currentSkin.fspeed, font24, 500, 25);
		currentSkinCp = addLabel("Fire Rate : " + PlayerData.currentSkin.frate, font24, 500, 75);
		currentSkinMr = addLabel("Mana Regen : " + PlayerData.currentSkin.manaRegen, font24, 500, 125);
		currentSkinDef = addLabel("Defense : " + PlayerData.currentSkin.defense, font24, 500, 175);
		currentSkinDesc = addTextbox(PlayerData.currentSkin.description, font14, 25, 240, 600, 400);
		
		skinSelectX = addLabel("X Speed : " + PlayerData.currentSkin.xspeed, font16, 900, 400);
		skinSelectY = addLabel("Y Speed : " + PlayerData.currentSkin.yspeed, font16, 900, 425);
		skinSelectPow = addLabel("Power : " + PlayerData.currentSkin.power, font16, 900, 450);
		skinSelectPrc = addLabel("Piercing : " + PlayerData.currentSkin.piercing, font16, 900, 475);
		skinSelectCr = addLabel("Fire Speed : " + PlayerData.currentSkin.fspeed, font16, 1025, 400);
		skinSelectCp = addLabel("Fire Rate : " + PlayerData.currentSkin.frate, font16, 1025, 425);
		skinSelectMr = addLabel("Mana Regen : " + PlayerData.currentSkin.manaRegen, font16, 1025, 450);
		skinSelectDef = addLabel("Defense : " + PlayerData.currentSkin.defense, font16, 1025, 475);
		skinSelectImage = addLabel(SystemData.playerImages[PlayerData.currentSkin.skinID], 950, 275, 150, 150);
		
		addLabel("Projectile", 745, 50);
		bulletImage = addLabel(SystemData.bulletImages[PlayerData.currentSkin.bulletID], 675, 80, 200, 60);
		
		updateList();
	}

	private JScrollPane addScrollList(String label, DefaultListModel<Skin> inv, int x, int y, int width, int height){
		JList<Skin> inventoryList = new JList<Skin>(inv);
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryList.addListSelectionListener(new ListSelectionListener(){
			@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent ev){
				Skin s =(((JList<Skin>) ev.getSource()).getSelectedValue());
				if (s != null) updateSelectionLabels(s);
			}});
		inventoryList.setFont(font18);
		JScrollPane inventoryListScroll = new JScrollPane(inventoryList);
		inventoryListScroll.setBounds(x, y, width, height);
		JLabel inventoryLabel = new JLabel(label);
		inventoryLabel.setBounds(x, y - 25, width, 25);
		add(inventoryListScroll);
		add(inventoryLabel);
		JButton switchButton = createButton(SystemData.switchButton, SystemData.switchButtonP,
				x + 85, y + height + 10, 75, 30);
		switchButton.addActionListener(new ActionListener() {	 
			public void actionPerformed(ActionEvent e){	
				Skin s = inventoryList.getSelectedValue();
				if (s != null) PlayerData.currentSkin = s;
				updateCurrentSkinLabels();
				PlayerData.saveData();
			}}); 
		add(switchButton);
		return inventoryListScroll;
	}

	private void updateCurrentSkinLabels(){
		currentSkinName.setText(PlayerData.currentSkin.name);
		currentSkinX.setText("X Speed : " + PlayerData.currentSkin.xspeed);
		currentSkinY.setText("Y Speed : " + PlayerData.currentSkin.yspeed);
		currentSkinPow.setText("Power : " + PlayerData.currentSkin.power);
		currentSkinPrc.setText("Piercing : " + PlayerData.currentSkin.piercing);
		currentSkinCr.setText("Fire Speed : " + PlayerData.currentSkin.fspeed);
		currentSkinCp.setText("Fire Rate : " + PlayerData.currentSkin.frate);
		currentSkinDef.setText("Defense : " + PlayerData.currentSkin.defense);
		currentSkinMr.setText("Mana Regen : " + PlayerData.currentSkin.manaRegen);
		currentSkinImage.setIcon(new ImageIcon(SystemData.playerImages[PlayerData.currentSkin.skinID]));
		currentSkinDesc.setText(PlayerData.currentSkin.description);
		bulletImage.setIcon(new ImageIcon(SystemData.bulletImages[PlayerData.currentSkin.bulletID]));
	}

	private void updateSelectionLabels(Skin s){
		skinSelectX.setText("X Speed : " + s.xspeed);
		skinSelectY.setText("Y Speed : " + s.yspeed);
		skinSelectPow.setText("Power : " + s.power);
		skinSelectPrc.setText("Piercing : " + s.piercing);
		skinSelectCr.setText("Fire Speed : " + s.fspeed);
		skinSelectCp.setText("Fire Rate : " + s.frate);
		skinSelectDef.setText("Defense : " + s.defense);
		skinSelectMr.setText("Mana Regen : " + s.manaRegen);
		skinSelectImage.setIcon(new ImageIcon(SystemData.playerImages[s.skinID]));
	}

	private void updateList(){
		skins.removeAllElements();
		for (Skin s : PlayerData.unlockedSkins){
			skins.addElement(s);
		}
	}
}
