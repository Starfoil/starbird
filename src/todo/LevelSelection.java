import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//This code doesn't work properly because of an error with creating instances of frame.
//We scrapped this class. It is no longer used.

public class LevelSelection extends JFrame{
	
	JFrame frame;

	
	public LevelSelection(){
		ImageIcon newE = new ImageIcon(LevelSelection.class.getResource("/resources/menub2.png"));
		final Image img = newE.getImage();
		JPanel pane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		pane.setLayout(null);
		
		
		JButton Level1 = new JButton();
		setButton(Level1, "1.png", 350,200,55,50);
		
		JButton Level2 = new JButton();
		setButton(Level2, "2.png", 450,200,55,50);
		
		JButton Level3 = new JButton();
		setButton(Level3, "3.png", 550,200,55,50);
		
		JButton Level4 = new JButton();
		setButton(Level4, "4.png", 650,200,55,50);
		
		JButton Level5 = new JButton();
		setButton(Level5, "5.png", 750,200,55,50);
		
		JButton Level6 = new JButton();
		setButton(Level6, "6.png", 850,200,55,50);
		
		JButton Level7 = new JButton();
		setButton(Level7, "7.png", 350,300,55,50);
		
		JButton Level8 = new JButton();
		setButton(Level8, "8.png", 450,300,55,50);
		
		JButton Level9 = new JButton();
		setButton(Level9, "9.png", 550,300,55,50);
		
		JButton Level10 = new JButton();
		setButton(Level10, "10.png", 640,290,75,70);
		
		JButton Level11 = new JButton();
		setButton(Level11, "11.png", 740,290,75,70);
		
		JButton Level12 = new JButton();
		setButton(Level12, "12.png", 840,290,75,70);
		
		JButton Level13 = new JButton();
		setButton(Level13, "13.png", 340,380,75,70);
		
		JButton Level14 = new JButton();
		setButton(Level14, "14.png", 440,380,75,70);
		
		JButton LevelB = new JButton();
		setButton(LevelB, "B.png", 1075,550,60,60);
		

		
		
		
		pane.add(Level1);
		pane.add(Level2);
		pane.add(Level3);
		pane.add(Level4);
		pane.add(Level5);
		pane.add(Level6);
		pane.add(Level7);
		pane.add(Level8);
		pane.add(Level9);
		pane.add(Level10);
		pane.add(Level11);
		pane.add(Level12);
		pane.add(Level13);
		pane.add(Level14);
		pane.add(LevelB);

		
		
		
		
		frame = new JFrame("Level Selection");
		//frame.setLayout(null);
		//frame.getContentPane().setLayout(new FlowLayout());
		frame.add(pane);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(125, 75, 1200,675);
		frame.setVisible(true);
		
				
		setLevel(Level1,1);
		setLevel(Level2,2);
		setLevel(Level3,3);
		setLevel(Level4,4);
		setLevel(Level5,5);
		setLevel(Level6,6);
		setLevel(Level7,7);
		setLevel(Level8,8);
		setLevel(Level9,9);
		setLevel(Level10,10);
		setLevel(Level11,11);
		setLevel(Level12,12);
		setLevel(Level13,13);
		setLevel(Level14,14);
		setLevel(LevelB,15);
	
		
		
	}
	
	public void setLevel(JButton x, final int level){
		x.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e)
            {	

			Player.game=true;
			Board.gamelevel=level;
			try {
				MainMenu.thegame= new Frame();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }}); 
	}
	
	
	public static void setButton(JButton x, String file, int locX, int locY, int sizeX, int sizeY){
		ImageIcon e = new ImageIcon(LevelSelection.class.getResource("/resources/"+file));
		final Image img = e.getImage();
		x.setIcon(new ImageIcon(img));
		x.setFocusPainted(false);
	    x.setRolloverEnabled(true);
	    x.setBorderPainted(false);
	    x.setContentAreaFilled(false);
		x.setLocation(locX, locY);
		x.setSize(sizeX,sizeY);
	}


	

}
