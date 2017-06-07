package mainGUI;

import main.Skin;

public class skinUpdateList implements UpdateList {
	public void updateList(){
		skinPanel.skins.removeAllElements();
		for (Skin s : PlayerData.unlockedSkins){
			skinPanel.skins.addElement(s);
		}
	}
}