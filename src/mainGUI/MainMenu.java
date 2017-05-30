package mainGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import online.ClientBoard;
import online.ClientGame;
import main.SingleplayerBoard;



public class MainMenu extends JFrame{

	//static Frame thegame;

	/**
	 * 
	 */
	private static final String version = "Beta 1.2";
	
	private static final long serialVersionUID = 1L;
	public static final int XFRAME = 1200;
	public static final int YFRAME = 700;

	public static JFrame frame;
	public static JTabbedPane jtp;
	public static boolean activeGame = false;
	public static SingleplayerBoard game;
	public static ClientGame ogame;

	private static GUIPanel mainPanel;
	private static GUIPanel skinPanel;
	private static GUIPanel onlinePanel;
	private static GUIPanel shopPanel;
	private static GUIPanel enemyPanel;


	public static void openFrame(JTabbedPane pane){
		frame = new JFrame("Bird");
		frame.add(pane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (dim.width / 2 - XFRAME / 2), (dim.height / 2 - YFRAME / 2) - 15);
		frame.setSize(XFRAME, YFRAME);
		//frame.setBounds(0, 0, XFRAME , YFRAME);
		frame.setVisible(true);

	}

	public static void main(String[] args) throws IOException {
		PrintStream out = new PrintStream(new FileOutputStream("errorlog.txt"));
		//System.setOut(out);
		System.setErr(out);
		SystemData.loadGameData();
		PlayerData.loadData();
		
		// Panels
		mainPanel = new MainMenu.mainPanel();
		skinPanel = new skinPanel();
		onlinePanel = new onlinePanel();
		enemyPanel = new enemyPanel();

		createTabbedPane();
		openFrame(jtp);
	}

	private static void createPanels(){
		jtp.addTab("Play", mainPanel);
		jtp.addTab("Characters", skinPanel);
		//jtp.addTab("Multiplayer", onlinePanel);
		jtp.addTab("Enemies", enemyPanel);
	}

	private static void createTabbedPane(){
		jtp = new JTabbedPane();
		createPanels();

		// Keep focus within game while it's running
		jtp.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (activeGame){
					if(game != null) game.requestFocusInWindow();
					if(ogame != null) ogame.CB.requestFocusInWindow();
				}
			}
		});
		// Exit the game while it's running
		jtp.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if (activeGame){
					jtp.removeAll();
					((mainGUI.MainMenu.mainPanel) mainPanel).updateLabels();
					activeGame = false;
					if (game != null) game.game.gameStatus = -1;
					if (ogame != null) {
						ogame.CB.game.gameStatus = -1;
						ClientGame.writer.interrupt();
						ClientGame.reader.interrupt();
					}
					createPanels();
				}
			}
		});
	}

	static class mainPanel extends GUIPanel{

		private static final long serialVersionUID = 5446757530637747748L;
		JLabel coins;
		JLabel ruby;
		JLabel highscore;
		
		JComboBox<LevelSpawner> levels;
		
		public mainPanel(){
			super();
			backgroundIMG = SystemData.mainBG;
			Font headerLabelFont = new Font("Georgia", Font.PLAIN, 32);
			addLabel(SystemData.rubyIcon, 1050, 20);
			ruby = addLabel(Integer.toString(PlayerData.stars), headerLabelFont, 1110, 20);

			addLabel(SystemData.coinImage, 850, 20);
			coins = addLabel(Integer.toString(PlayerData.coins), headerLabelFont, 910, 20);

			addLabel(SystemData.trophyIcon, 1000, 580);
			highscore = addLabel(Integer.toString(PlayerData.highscore), headerLabelFont, 1060, 585);

			addLabel(version, 10, 0);


			JButton play = createButton(SystemData.playButton, SystemData.playButtonP, 475, 500, 250, 100);
			play.addActionListener(new ActionListener() {	 
				public void actionPerformed(ActionEvent e){	
					jtp.removeAll();
					LevelSpawner levelSelected =  new LevelSpawner((LevelSpawner) levels.getSelectedItem());
					game = new SingleplayerBoard(levelSelected);
					jtp.addTab("Game", game);
					jtp.addTab("Exit", new JPanel());
					game.requestFocusInWindow();
					activeGame = true;
					play.setIcon(new ImageIcon(SystemData.playButton));
				}
			});
			Font font16 = new Font("Georgia", Font.PLAIN, 16);
			levels = new JComboBox<LevelSpawner>();
			levels.setBounds(500, 450, 200, 30);
			levels.setFont(font16);
			levels.setMaximumRowCount(5);
			updateLevels();
			add(levels);
		}

		public void updateLabels(){
			ruby.setText(Integer.toString(PlayerData.stars));
			coins.setText(Integer.toString(PlayerData.coins));
			highscore.setText(Integer.toString(PlayerData.highscore));
		}
		
		public void updateLevels(){
			levels.removeAllItems();
			for (LevelSpawner ls : SystemData.allLevels){
				levels.addItem(ls);
			}
		}
	}
}
