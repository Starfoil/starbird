package todo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainMenu extends JFrame{
	
	//static Frame thegame;
	
	private static final int XFRAME = 1200;
	private static final int YFRAME = 675;
	
	public static void setButton(JButton x, String file, int locX, int locY, int sizeX, int sizeY){
		ImageIcon e = new ImageIcon("resources\\"+file);
		final Image img = e.getImage();
		x.setIcon(new ImageIcon(img));
		x.setFocusPainted(false);
	    x.setRolloverEnabled(true);
	    x.setBorderPainted(false);
	    x.setContentAreaFilled(false);
		x.setLocation(locX, locY);
		x.setSize(sizeX,sizeY);
	}
	
	public static void openFrame(JPanel pane){
		JFrame frame = new JFrame("Starbird 2017 XD");
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(20, 20, XFRAME , YFRAME);
		frame.setVisible(true);
	}
	

	public static void main(String[] args) throws IOException {
		Image mainBG = new ImageIcon("resources\\menub1.png").getImage();
		JPanel mainPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(mainBG, 0, 0, null);
			}
		};
		Image levelBG = new ImageIcon("resources\\menub2.png").getImage();
		JPanel levelPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(levelBG, 0, 0, null);
			}
		};
		mainPanel.setLayout(null);
		levelPanel.setLayout(null);
		

		JButton play = new JButton();
		setButton(play, "button.png", XFRAME - 200 , YFRAME - 200, 120, 60);
		JButton exit = new JButton();
		setButton(exit, "button1.png", XFRAME - 200 , YFRAME - 100, 120, 60);
		JButton Level1 = new JButton();
		setButton(Level1, "1.png", 350,200,55,50);
		JButton Level2 = new JButton();
		setButton(Level2, "2.png", 450,200,55,50);
		JButton Level3 = new JButton();
		setButton(Level3, "3.png", 550,200,55,50);
		mainPanel.add(play);
		mainPanel.add(exit);
		levelPanel.add(Level1);
		levelPanel.add(Level2);
		levelPanel.add(Level3);
		play.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	openFrame(levelPanel);
            }}); 
		exit.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	System.exit(0);
            }}); 
		
		
		openFrame(mainPanel);
		
	}
		

}
