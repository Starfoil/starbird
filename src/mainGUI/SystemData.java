package mainGUI;


import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.Skin;

public class SystemData {

	public static ArrayList<Skin> allSkins = new ArrayList<Skin>();
	public static Image backgroundImg;
	public static Image[] playerImages = new Image[50];
	public static Image[] bulletImages = new Image[20];
	public static Image[] birdImages = new Image[10];
	public static Image coinImage;
	public static Image trophyIcon;
	public static Image victoryIcon;
	public static Image defeatIcon;
	public static Image playButton;
	public static Image playButtonP;
	public static Image switchButton;
	public static Image switchButtonP;
	
	public static boolean showHitbox = true;

	public static void loadGameData(){
		try {
			loadSkins("data\\Skindata.txt");
			loadImages();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void loadImages(){
		// Background
		backgroundImg = new ImageIcon(SystemData.class.getResource("/ab.jpg")).getImage();
		// Player Skins
		playerImages[0] = new ImageIcon(SystemData.class.getResource("/Player.png")).getImage();
		playerImages[1] = new ImageIcon(SystemData.class.getResource("/Player1.png")).getImage();
		playerImages[2] = new ImageIcon(SystemData.class.getResource("/Player2.png")).getImage();
		playerImages[3] = new ImageIcon(SystemData.class.getResource("/Player3.png")).getImage();
		playerImages[4] = new ImageIcon(SystemData.class.getResource("/Player4.png")).getImage();
		playerImages[5] = new ImageIcon(SystemData.class.getResource("/Player5.png")).getImage();
		// Special Skins
		playerImages[7] = new ImageIcon(SystemData.class.getResource("/Playerghost.png")).getImage();
		playerImages[16] = new ImageIcon(SystemData.class.getResource("/Player16.png")).getImage();
		playerImages[17] = new ImageIcon(SystemData.class.getResource("/Player17.png")).getImage();
		playerImages[18] = new ImageIcon(SystemData.class.getResource("/Player18.png")).getImage();
		playerImages[21] = new ImageIcon(SystemData.class.getResource("/Player21.png")).getImage();
		playerImages[24] = new ImageIcon(SystemData.class.getResource("/Player24.png")).getImage();
		playerImages[25] = new ImageIcon(SystemData.class.getResource("/Player25.png")).getImage();
		playerImages[26] = new ImageIcon(SystemData.class.getResource("/Player26.png")).getImage();
		playerImages[30] = new ImageIcon(SystemData.class.getResource("/Player30.png")).getImage();
		playerImages[31] = new ImageIcon(SystemData.class.getResource("/Player31.png")).getImage();
		playerImages[34] = new ImageIcon(SystemData.class.getResource("/Player34.png")).getImage();
		playerImages[42] = new ImageIcon(SystemData.class.getResource("/Player42.png")).getImage();
		// Bullet Skins
		bulletImages[0] = new ImageIcon(SystemData.class.getResource("/bullet0.png")).getImage();
		bulletImages[1] = new ImageIcon(SystemData.class.getResource("/bullet1.png")).getImage();
		bulletImages[2] = new ImageIcon(SystemData.class.getResource("/bullet2.png")).getImage();
		bulletImages[3] = new ImageIcon(SystemData.class.getResource("/bullet3.png")).getImage();
		bulletImages[4] = new ImageIcon(SystemData.class.getResource("/bullet4.png")).getImage();
		bulletImages[5] = new ImageIcon(SystemData.class.getResource("/bullet5.png")).getImage();
		bulletImages[6] = new ImageIcon(SystemData.class.getResource("/bullet6.png")).getImage();
		bulletImages[7] = new ImageIcon(SystemData.class.getResource("/bullet7.png")).getImage();
		bulletImages[8] = new ImageIcon(SystemData.class.getResource("/bullet8.png")).getImage();
		bulletImages[9] = new ImageIcon(SystemData.class.getResource("/bullet9.png")).getImage();
		// Enemy Skins
		birdImages[0] = new ImageIcon(SystemData.class.getResource("/ybird.png")).getImage();
		birdImages[1] = new ImageIcon(SystemData.class.getResource("/ghost.png")).getImage();
		birdImages[2] = new ImageIcon(SystemData.class.getResource("/bbird.png")).getImage();
		birdImages[3] = new ImageIcon(SystemData.class.getResource("/Bee.png")).getImage();
		birdImages[4] = new ImageIcon(SystemData.class.getResource("/pilotbird.png")).getImage();
		birdImages[5] = new ImageIcon(SystemData.class.getResource("/gbird.png")).getImage();
		birdImages[6] = new ImageIcon(SystemData.class.getResource("/poibird.png")).getImage();
		birdImages[7] = new ImageIcon(SystemData.class.getResource("/bbird2.png")).getImage();
		birdImages[8] = new ImageIcon(SystemData.class.getResource("/BossBird.png")).getImage();
		// Misc Objects
		coinImage = new ImageIcon(SystemData.class.getResource("/coindrop.png")).getImage();
		trophyIcon = new ImageIcon(SystemData.class.getResource("/trophyicon.png")).getImage();
		victoryIcon = new ImageIcon(SystemData.class.getResource("/victory.png")).getImage();
		defeatIcon = new ImageIcon(SystemData.class.getResource("/defeat.png")).getImage();
		// Buttons
		playButton = new ImageIcon(SystemData.class.getResource("/playbutton.png")).getImage();
		playButtonP = new ImageIcon(SystemData.class.getResource("/playbutton1.png")).getImage();
		switchButton = new ImageIcon(SystemData.class.getResource("/switch.png")).getImage();
		switchButtonP = new ImageIcon(SystemData.class.getResource("/switch1.png")).getImage();
	}

	private static void loadSkins(String fileName) throws NumberFormatException, IOException{
		FileReader f = new FileReader(fileName);
		BufferedReader br = new BufferedReader(f);
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			int id = Integer.parseInt(tokens[0].trim());
			String name = tokens[1].trim();
			int xs = Integer.parseInt(tokens[2].trim());
			int ys = Integer.parseInt(tokens[3].trim());
			int pow = Integer.parseInt(tokens[4].trim());
			int prc = Integer.parseInt(tokens[5].trim());
			int cr = Integer.parseInt(tokens[6].trim());
			int cp = Integer.parseInt(tokens[7].trim());
			int def = Integer.parseInt(tokens[8].trim());
			double mr = Double.parseDouble(tokens[9].trim());
			int sizeX = Integer.parseInt(tokens[10].trim());
			int sizeY = Integer.parseInt(tokens[11].trim());
			int xo = Integer.parseInt(tokens[12].trim());
			int yo = Integer.parseInt(tokens[13].trim());
			Skin s = new Skin(id, name, xs, ys, pow, prc, cr, cp ,def, mr ,sizeX, sizeY);
			s.setOffset(xo, yo);
			int shoot = Integer.parseInt(tokens[14].trim());
			int BID = Integer.parseInt(tokens[15].trim());
			int BX = Integer.parseInt(tokens[16].trim());
			int BY = Integer.parseInt(tokens[17].trim());
			s.setFire(shoot, BID, BX, BY);
			String desc = "";
			while (!(line = br.readLine()).equals("ENDBIO")){
				desc += line + "\n";
			}
			s.setDescription(desc);
			allSkins.add(s);
		}
		br.close();
	}

	public static Skin getSkin(int skinID){
		for (Skin s : allSkins){
			if (s.skinID == skinID)	return s;
		}
		return null;
	}
	
	

}
