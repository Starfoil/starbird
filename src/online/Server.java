package online;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import birds.*;
import main.GameInstance;
import main.GameManager;
import main.Player;
import main.Skin;
import mainGUI.PlayerData;

public class Server extends Thread{

	// Server variables
	private final int port = 6969;
	private DatagramSocket socket;
	ByteArrayInputStream byteIS;
	ObjectInputStream objectIS;
	ByteArrayOutputStream byteOS;
	ObjectOutputStream objectOS;
	byte[] playerData = new byte[8192];
	byte[] gameData = new byte[65535];

	//Game variables
	private final int updatesPerSecond = 80;
	private ServerGameManager GM;
	private GameInstance game;
	private boolean lock = false;

	private JPanel mainPanel;
	private JFrame frame;
	private JLabel gameStatus;
	private JTextArea playerListBox;
	private JButton startButton;

	public ArrayList<SocketAddress> connections = new ArrayList<SocketAddress>();
	JLabel serverText;

	public Server() throws SocketException, UnknownHostException{
		game = new GameInstance();
		game.gameStatus = 1;
		GM = new ServerGameManager(game);
		socket = new DatagramSocket(port);

		setupObjects();
		createGUI();
	}

	public void createGUI() throws UnknownHostException{
		// GUI
		InetAddress localHost = InetAddress.getLocalHost();
		Image mainBG = new ImageIcon(Server.class.getResource("/test.jpg")).getImage();
		mainPanel = new JPanel() {
			private static final long serialVersionUID = -2101634729821671537L;
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(mainBG, 0, 0, null);
			}
		};
		mainPanel.setLayout(null);
		serverText = addLabel("Starting server on " + localHost 
				+ " on port " + socket.getLocalPort() + "...", 10, 10);
		gameStatus = addLabel("Game status : "+ game.gameStatus, 10, 30);
		playerListBox = addTextbox("", 10, 80, 350, 240);

		startButton = new JButton("Start game");
		startButton.setBounds(400, 300, 150, 50);
		startButton.addActionListener(new ActionListener() {	 
			public void actionPerformed(ActionEvent e){	
				game.gameStatus = 2;
			}}); 
		mainPanel.add(startButton);

		frame = new JFrame("Starbird Server");
		frame.add(mainPanel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(20, 20, 600 , 400);
		frame.setVisible(true);
	}

	public void setupObjects(){
		//game.addEnemies(new Bluejay(120, 4000, 2000));
		//game.addEnemies(new Eagle(170, 75000, 15000));
		//game.addEnemies(new Bomber(80, 45000, 25000));
		//game.addEnemies(new Buzzer(180, 80000, 3000));
		//game.addEnemies(new Hawk(150, 125000, 25000));
		//game.addEnemies(new Wraith(40, 15000, 30000));
		game.addEnemies(new Hatter(1, 0, 1500));
	}


	public void run(){
		while (true){
			updateLabels();
			try {
				GM.performUpdateStatus();;
				Thread.sleep(1000/updatesPerSecond);
				if (game.gameStatus == -1){
					System.exit(0);
				}
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	public static void main(String [] args) {
		try {
			Server SGM = new Server();

			SGM.start();

			Thread reader = SGM.new ServerThread();
			reader.start();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void updateLabels(){
		gameStatus.setText("Game Status : " + game.gameStatus);
		String s = "Connections : \n";
		for (SocketAddress SC : connections){
			s += SC.toString() + "\n";
		}
		playerListBox.setText(s);
	}

	public JLabel addLabel(String info, int x, int y){
		JLabel label = new JLabel(info);
		label.setBounds(x, y, 600, 45);
		mainPanel.add(label);	 	
		return label;
	}

	public JTextArea addTextbox(String text, int x, int y, int width, int height){
		JTextArea box = new JTextArea(text);
		box.setBounds(x, y, width, height);
		box.setEditable(false);
		box.setOpaque(false);
		mainPanel.add(box);
		return box;
	}


	private void updateConnections(DatagramPacket packet){
		SocketAddress ip = packet.getSocketAddress();
		if (!connections.contains(ip)) connections.add(ip);
	}

	class ServerThread extends Thread{
		public void run(){
			while(true){
				try{
					// Read PLAYERDATA from client
					DatagramPacket incomingPacket = new DatagramPacket(playerData, playerData.length);
					socket.receive(incomingPacket);
					playerData = incomingPacket.getData();
					byteIS = new ByteArrayInputStream(playerData);
					objectIS = new ObjectInputStream(byteIS);
					Player p = (Player) objectIS.readObject();
					updateConnections(incomingPacket);

					//if(!lock){
						//lock = true;
						GM.updatePlayer(p);
						GM.performCollisionStatus();
						//lock = false;
					//}

					// Reply with GAMEDATA to client
					InetAddress IPAddress = incomingPacket.getAddress();
					int port = incomingPacket.getPort();
					//System.out.println("Replying to client " + IPAddress+":"+port);
					byteOS = new ByteArrayOutputStream();
					objectOS = new ObjectOutputStream(byteOS);
					objectOS.writeObject(game);
					objectOS.flush();
					gameData = byteOS.toByteArray();
					//System.out.println("GAME SIZE : " + gameData.length);
					DatagramPacket replyPacket =
							new DatagramPacket(gameData, gameData.length, IPAddress, port);
					socket.send(replyPacket);
					lock = false;

				} catch(ConcurrentModificationException e){
					e.printStackTrace();
				} catch(IOException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		}
	}




}
