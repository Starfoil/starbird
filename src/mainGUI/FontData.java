package mainGUI;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public class FontData {
	private static FontData instance = new FontData();
	private Map<String, Font> fontData = new HashMap<>();
	
	private void add(String font, int type, int fontsize){
		String key = font+type+fontsize;
		fontData.put(key, new Font(font, type, fontsize));
	}
	
	
	public static FontData getInstance(){
		return instance;
	}
	
	public Font getFont(String font, int type, int fontsize){
		String key = font+type+fontsize;
		if (!fontData.containsKey(key))
			add(font, type, fontsize);
		return fontData.get(key);		
	}
}
