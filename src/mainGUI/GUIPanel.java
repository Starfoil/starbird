package mainGUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class GUIPanel extends JPanel{
	
	Image backgroundIMG;
	
	public GUIPanel(){
		setLayout(null);
	}
	
	public JButton createButton(Image img, Image imgPressed, int locX, int locY, int sizeX, int sizeY){
		JButton x = new JButton();
		x.setIcon(new ImageIcon(img));
		x.setFocusPainted(false);
	    x.setRolloverEnabled(true);
	    x.setBorderPainted(false);
	    x.setContentAreaFilled(false);
		x.setLocation(locX, locY);
		x.setSize(sizeX,sizeY);
		x.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				x.setIcon(new ImageIcon(imgPressed));
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				x.setIcon(new ImageIcon(img));
			}
		});
		add(x);
		return x;
	}
	
	protected void paintComponent(Graphics g) {
		if (backgroundIMG != null) g.drawImage(backgroundIMG, 0, 0, null);
	}
	
	public JLabel addLabel(String info, int x, int y){
		JLabel label = new JLabel(info);
		label.setBounds(x, y, 300, 45);
		add(label);	 	
		return label;
	}
	
	public JLabel addLabel(String info, Font font, int x, int y){
		JLabel label = new JLabel(info);
		label.setFont(font);
		label.setBounds(x, y, 300, 45);
		add(label);	 	
		return label;
	}
	
	public JLabel addLabel(Image img, int x, int y){
		JLabel label = new JLabel(new ImageIcon(img));
		label.setBounds(x, y, img.getWidth(null) + 10, img.getHeight(null) + 10);
		add(label);	 	
		return label;
	}
	
	public JLabel addLabel(Image img, int x, int y, int width, int height){
		JLabel label;
		if (img != null) 	label = new JLabel(new ImageIcon(img));
		else 				label = new JLabel();
		label.setBounds(x, y, width, height);
		add(label);	 	
		return label;
	}
	
	public JTextArea addTextbox(String text, Font font, int x, int y, int width, int height){
		JTextArea box = new JTextArea(text);
		box.setFont(font);
		box.setBounds(x, y, width, height);
		box.setEditable(false);
		box.setOpaque(false);
		add(box);
		return box;
	}
	
	public JTextField addTextField(String text, Font font, int x, int y, int width, int height){
		JTextField textField = new JTextField(text);
		textField.setFont(font);
		textField.setBounds(x, y, width, height);
		add(textField);
		
		return textField;
	}
	
	
}
