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

import main.Board;
import main.Frame;
import main.Player;


public class MainMenu extends JFrame{
	
	static Frame thegame;
	
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
	

	public static void main(String[] args) throws IOException {


		ImageIcon newE = new ImageIcon("resources\\menub1.png");
		Image img = newE.getImage();
		JPanel pane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 32)); 
			}
		};
		pane.setLayout(null);
		
		JButton play = new JButton();
		setButton(play, "button.png", 560,350,120,60);
		
		JButton exit = new JButton();
		setButton(exit, "button1.png", 560,490,120,60);
		
		JButton book = new JButton();
		setButton(book, "button2.png", 970,570,200,60);

		
		pane.add(play);
		pane.add(exit);
		pane.add(book);

		
		final JFrame frame = new JFrame("Menu");
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(20, 20, 1200,675);
		frame.setVisible(true);
		
		
		play.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            }}); 
		exit.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	System.exit(0);
            }}); 
		book.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
//            	JFrame book = new JFrame("Bird Book");
//            	ImageIcon newE = new ImageIcon("resources\\birdbook.png");
//        		final Image img = newE.getImage();
//        		JPanel pane = new JPanel() {
//        			protected void paintComponent(Graphics g) {
//        				super.paintComponent(g);
//        				g.drawImage(img, 0, 0, null);
//        			}
//        		};
//        		pane.setLayout(null);
//        		book.add(pane);
//        		book.setResizable(false);
//        		book.pack();
//        		book.setBounds(80, 80, 810,395);
//            	book.setVisible(true);
            }}); 

	}
		

}
