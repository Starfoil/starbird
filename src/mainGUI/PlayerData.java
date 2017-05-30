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

	final static File saveFile = new File(System.getenv("APPDATA")+"\\sauce.txt");
	//final static File saveFile = new File("data\\data.txt");
	public static String playerName;
	public static Skin currentSkin;
	public static ArrayList<Skin> unlockedSkins;
	public static Player player;

	public static int coins;	// Default currency
	public static int stars;	// P2W currency

	public static int highscore;

	public static String savedIP;
	public static String savedPort;

	public static void loadData(){
		if (!saveFile.exists()){
			playerName = "Beta Test";
			unlockedSkins = new ArrayList<Skin>();
			unlockedSkins.add(SystemData.getSkin(0));
			unlockedSkins.add(SystemData.getSkin(1));
			unlockedSkins.add(SystemData.getSkin(2));
			unlockedSkins.add(SystemData.getSkin(3));
			currentSkin = unlockedSkins.get(0);
			coins = 0;
			stars = 0;
			highscore = 0;
			savedIP = "0.0.0.0";
			savedPort = "0000";
		}else{
			loadSaveData();
		}
		player = new Player(playerName);
		//unlockAllSkins();
	}

	public static void saveData(){
		if (!saveFile.exists()){
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
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
			bw.write(Integer.toString(highscore));
			bw.newLine();
			for (Skin S : unlockedSkins){
				bw.write(Integer.toString(S.skinID));
				bw.newLine();
			}
			bw.write("ENDSKINS");
			bw.newLine();
			bw.write(savedIP);
			bw.newLine();
			bw.write(savedPort);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadSaveData(){
		try{
			FileReader fr = new FileReader(saveFile);
			BufferedReader br = new BufferedReader(fr);

			playerName = br.readLine();
			currentSkin = SystemData.getSkin(Integer.parseInt(br.readLine()));
			coins = Integer.parseInt(br.readLine());
			stars = Integer.parseInt(br.readLine());
			highscore = Integer.parseInt(br.readLine());

			String line = null;
			unlockedSkins = new ArrayList<Skin>();
			while (!(line = br.readLine()).equals("ENDSKINS")){
				unlockedSkins.add(SystemData.getSkin(Integer.parseInt(line)));
			}
			savedIP = br.readLine();
			savedPort = br.readLine();
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void unlockAllSkins(){
		unlockedSkins.removeAll(unlockedSkins);
		unlockedSkins.addAll(SystemData.allSkins);
	}

}
