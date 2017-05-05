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
	
	static Frame thegame;
	
	public static void setButton(JButton x, String file, int locX, int locY, int sizeX, int sizeY){
		ImageIcon e = new ImageIcon(MainMenu.class.getResource("/resources/"+file));
		final Image img = e.getImage();
		x.setIcon(new ImageIcon(img));
		x.setFocusPainted(false);
	    x.setRolloverEnabled(true);
	    x.setBorderPainted(false);
	    x.setContentAreaFilled(false);
		x.setLocation(locX, locY);
		x.setSize(sizeX,sizeY);
	}
	
	public static void setCharacter(final JFrame frame, JButton x, final String file, final int playernum){
		x.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	Player.s = new ImageIcon(Player.class.getResource("/resources/"+file));
            	Board.player=playernum;
            	frame.dispose();
            }}); 
	}

	public static void main(String[] args) throws IOException {
		
		Data.read();


		ImageIcon newE = new ImageIcon(MainMenu.class.getResource("/resources/menub1.png"));
		final Image img = newE.getImage();
		JPanel pane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 32)); 
				g.drawString("Points: "+Data.totalPoint(), 40,620);
			}
		};
		pane.setLayout(null);
		
		JButton play = new JButton();
		setButton(play, "button.png", 560,350,120,60);
		
		JButton exit = new JButton();
		setButton(exit, "button1.png", 560,490,120,60);
		
		JButton book = new JButton();
		setButton(book, "button2.png", 970,570,200,60);
		
		//JButton score = new JButton();
		//setButton(score, "button3.png", 560,420,130,60);
		
		JButton character = new JButton();
		setButton(character, "button4.png", 555,420,130,60);

		
		pane.add(play);
		pane.add(exit);
		pane.add(book);
		//pane.add(score);

		
		pane.add(character);
		
		final JFrame frame = new JFrame("Menu");
		//frame.setLayout(null);
		//frame.getContentPane().setLayout(new FlowLayout());
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(20, 20, 1200,675);
		frame.setVisible(true);
		
		
		play.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
//            	Player.game=true;
//				try {
//					thegame = new Frame();
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				LevelSelection x = new LevelSelection();
            }}); 
		exit.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	System.exit(0);
            }}); 
		book.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	JFrame book = new JFrame("Bird Book");
            	ImageIcon newE = new ImageIcon(MainMenu.class.getResource("/resources/birdbook.png"));
        		final Image img = newE.getImage();
        		JPanel pane = new JPanel() {
        			protected void paintComponent(Graphics g) {
        				super.paintComponent(g);
        				g.drawImage(img, 0, 0, null);
        			}
        		};
        		pane.setLayout(null);
        		book.add(pane);
        		book.setResizable(false);
        		book.pack();
        		book.setBounds(80, 80, 810,395);
            	book.setVisible(true);
            }}); 
//		score.addActionListener(new ActionListener() {	 
//            public void actionPerformed(ActionEvent e)
//            {	
//            	JFrame book = new JFrame("Score");
//            	ImageIcon newE = new ImageIcon(MainMenu.class.getResource("/resources/menub.png"));
//        		final Image img = newE.getImage();
//        		JPanel pane = new JPanel() {
//        			protected void paintComponent(Graphics g) {
//        				super.paintComponent(g);
//        				g.drawImage(img, 0, 0, null);
//        				g.setColor(Color.BLACK);
//        				g.setFont(new Font("Arial", Font.PLAIN, 30)); 
//        				g.drawString("Top 10 Scores", 100, 50);
//        				g.setFont(new Font("Arial", Font.PLAIN, 22)); 
//        				for(int i=0;i<10;i++){
//        					g.drawString(""+(i+1)+".", 100, 100+(i*35));
//        					g.drawString(""+Data.top10().get(i), 150, 100+(i*35));
//        				}
//        				g.setFont(new Font("Arial", Font.PLAIN, 10)); 
//        				g.drawString("Reset game to update highscores and points", 185, 460);
//        			}
//        		};
//        		pane.setLayout(null);
//        		book.add(pane);
//        		book.setResizable(false);
//        		book.pack();
//        		book.setBounds(80, 20, 450,500);
//            	book.setVisible(true);
//            }}); 
		character.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	
            	final JFrame book = new JFrame("Charater");
            	ImageIcon newE = new ImageIcon(MainMenu.class.getResource("/resources/menub.png"));
        		final Image img = newE.getImage();
        		JPanel pane = new JPanel() {
        			protected void paintComponent(Graphics g) {
        				super.paintComponent(g);
        				g.drawImage(img, 0, 0, null);
        				
        			}
        		};
        		
        		
        		JButton defbird = new JButton();
        		setButton(defbird, "Player.png", 20,20,130,90);
        		JButton lightningbird = new JButton();
        		setButton(lightningbird, "Player1.png", 150,20,130,90);
        		JButton dragonbird = new JButton();
        		setButton(dragonbird, "Player2.png", 280,20,130,90);
        		JButton reddragonbird = new JButton();
        		setButton(reddragonbird, "Player3.png", 20,120,130,90);
        		pane.add(lightningbird);
        		pane.add(defbird);
        		pane.add(dragonbird);
        		pane.add(reddragonbird);
        		pane.setLayout(null);
        		book.add(pane);
        		book.setResizable(false);
        		book.pack();
        		book.setBounds(80, 20, 450,500);
            	book.setVisible(true);
        		
            	setCharacter(book,defbird,"Player.png",0);
        		setCharacter(book,lightningbird,"Player1.png",1);
        		setCharacter(book,dragonbird,"Player2.png",2);
        		setCharacter(book,reddragonbird,"Player3.png",3);
        		
        		
            }}); 

	}
		

}
