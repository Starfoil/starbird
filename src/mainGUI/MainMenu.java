package mainGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.SingleplayerBoard;



public class MainMenu extends JFrame{

	//static Frame thegame;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int XFRAME = 1200;
	private static final int YFRAME = 680;

	public static JFrame frame;
	public static JTabbedPane jtp;
	public static boolean activeGame = false;
	public static SingleplayerBoard game;
	
	private static GUIPanel mainPanel;
	private static GUIPanel skinPanel;

	public static void openFrame(JTabbedPane pane){
		frame = new JFrame("Bird");
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(25, 25, XFRAME , YFRAME);
		frame.setVisible(true);

	}

	public static void openFrame(JPanel pane){
		frame = new JFrame("Bird");
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(20, 20, XFRAME , YFRAME);
		frame.setVisible(true);

	}


	public static void main(String[] args) throws IOException {
		SystemData.loadGameData();
		PlayerData.loadData();
		
		// Panels
		mainPanel = new MainMenu.mainPanel();
		skinPanel = new skinPanel();
		
		
		createTabbedPane();
		openFrame(jtp);
	}
	
	private static void createTabbedPane(){
		jtp = new JTabbedPane();
		jtp.addTab("Main", mainPanel);
		jtp.addTab("Skins", skinPanel);
		// Keep focus within game while it's running
		jtp.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (activeGame){
					game.requestFocusInWindow();
				}
			}
		});
		// Exit the game while it's running
		jtp.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if (activeGame){
					jtp.removeAll();
					activeGame = false;
					game.game.gameStatus = -1;
					jtp.addTab("Main", mainPanel);
					jtp.addTab("Skins", skinPanel);
				}
			}
		});
	}

	static class mainPanel extends GUIPanel{

		private static final long serialVersionUID = 5446757530637747748L;

		public mainPanel(){
			super();
			backgroundIMG = new ImageIcon("resources\\playPanel.jpg").getImage();
			Font headerLabelFont = new Font("Georgia", Font.PLAIN, 32);
			
			Image ruby = new ImageIcon("resources\\ruby.png").getImage();
			addLabel(ruby, 1050, 25);
			addLabel(Integer.toString(PlayerData.stars), headerLabelFont, 1125, 25);
			
			Image coin = new ImageIcon("resources\\coins.png").getImage();
			addLabel(coin, 800, 25);
			addLabel(Integer.toString(PlayerData.coins), headerLabelFont, 950, 25);
			
			addLabel(PlayerData.playerName, headerLabelFont, 25, 25);
			
			
			JButton play = createButton(SystemData.playButton, SystemData.playButtonP, 475, 450, 250, 100);

			play.addActionListener(new ActionListener() {	 
				public void actionPerformed(ActionEvent e){	
					jtp.removeAll();
					game = new SingleplayerBoard();
					game.setLayout(null);
					jtp.addTab("Game", game);
					jtp.addTab("Exit", new JPanel());
					game.requestFocusInWindow();
					activeGame = true;
					play.setIcon(new ImageIcon(SystemData.playButton));
				}
			});
		}
	}
}
