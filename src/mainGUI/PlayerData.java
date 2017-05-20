package mainGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.*;

public class PlayerData {

	//final static File saveFile = new File(System.getenv("APPDATA")+"\\udrewbird.txt");
	final static File saveFile = new File("data\\data.txt");
	public static String playerName;
	public static Skin currentSkin;
	public static ArrayList<Skin> unlockedSkins;

	public static int coins;	// Default currency
	public static int stars;	// P2W currency

	public static int level;	// EXP
	public static int exp;

	public static void loadData(){
		if (!saveFile.exists()){
			playerName = "Default";
			currentSkin = SystemData.allSkins.get(0);
			unlockedSkins = new ArrayList<Skin>();
			coins = 0;
			stars = 0;
			exp = 0;
		}else{
			try {
				loadSaveData();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveData() throws IOException{
		if (!saveFile.exists()) saveFile.createNewFile();
		FileWriter fw = new FileWriter(saveFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(playerName);
		bw.newLine();
		bw.write(Integer.toString(currentSkin.skinID));
		bw.newLine();
		bw.write(Integer.toString(coins));
		bw.newLine();
		bw.write(Integer.toString(stars));
		bw.newLine();
		bw.write(Integer.toString(exp));
		bw.newLine();
		for (Skin S : unlockedSkins){
			bw.write(Integer.toString(S.skinID));
			bw.newLine();
		}
		bw.write("ENDSKINS");
		bw.close();
	}

	public static void loadSaveData() throws IOException{
		FileReader fr = new FileReader(saveFile);
		BufferedReader br = new BufferedReader(fr);
		
		playerName = br.readLine();
		currentSkin = SystemData.getSkin(Integer.parseInt(br.readLine()));
		coins = Integer.parseInt(br.readLine());
		stars = Integer.parseInt(br.readLine());
		exp = Integer.parseInt(br.readLine());
		
		String line = null;
		unlockedSkins = new ArrayList<Skin>();
		while (!(line = br.readLine()).equals("ENDSKINS")){
			unlockedSkins.add(SystemData.getSkin(Integer.parseInt(line)));
		}
		br.close();

	}

}
