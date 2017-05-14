package online;

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

import birds.*;
import main.Player;

public class Server extends Thread{

	// Server variables
	private final int port = 7780;
	private DatagramSocket socket;
	ByteArrayInputStream byteIS;
	ObjectInputStream objectIS;
	ByteArrayOutputStream byteOS;
	ObjectOutputStream objectOS;
	byte[] playerData = new byte[8192];
	byte[] gameData = new byte[100000];
	
	//Game variables
	private final int updatesPerSecond = 80;
	private GameManager GM;
	private GameInstance game;
	private boolean play;
	
	public ArrayList<SocketAddress> connections = new ArrayList<SocketAddress>();
	JLabel serverText;

	public Server(){
		game = new GameInstance();
		GM = new GameManager(game);
		setupObjects();
		serverText = new JLabel();
		play = false;
	}

	public void setupObjects(){
		game.addEnemies(new Bluejay(25, 5000, 500));
		game.addEnemies(new Hatter(2, 10000, 2000));
		game.addEnemies(new Eagle(20, 30000, 6000));
		game.addEnemies(new Bomber(10, 12000, 8000));
		game.addEnemies(new Buzzer(25, 20000, 3000));
		game.addEnemies(new Hawk(15, 24000, 10000));
		//game.addEnemies(new Spoder(8, 20000, 2000));
		//GM.addEnemies(new Blackbird(1, 0, 2500));
		game.addBot();
	}


	public void run(){
		while (true){
			generateText();
			if(play){
				try {
					Thread.sleep(1000/updatesPerSecond);
					GM.update();
				} catch (InterruptedException e) {e.printStackTrace();}
			}	
		}
	}

	public static void main(String [] args) {
		try {
			Server SGM = new Server();
			SGM.socket = new DatagramSocket(SGM.port);
			InetAddress localHost = InetAddress.getLocalHost();
//			System.out.println("Starting server on " + localHost 
//					+ " on port " + SGM.socket.getLocalPort() + "...");
			SGM.start();
			
			Thread reader = SGM.new ServerThread();
			reader.start();
			
			// GUI
			Image mainBG = new ImageIcon(Server.class.getResource("/test.jpg")).getImage();
			JPanel mainPanel = new JPanel() {
				private static final long serialVersionUID = -2101634729821671537L;
				@Override
				protected void paintComponent(Graphics g) {
					g.drawImage(mainBG, 0, 0, null);
				}
			};
			mainPanel.setLayout(null);
			SGM.serverText.setBounds(10, 40, 400, 140);
			JLabel serverInitialText = new JLabel();
			serverInitialText.setText("Starting server on " + localHost 
					+ " on port " + SGM.socket.getLocalPort() + "...");
			serverInitialText.setBounds(10, 10, 400, 30);
			JButton playButton = new JButton();
			setButton(playButton, "p1.png", 300, 50, 120, 60);
			playButton.addActionListener(new ActionListener() {	 
	            public void actionPerformed(ActionEvent e)
	            {	
	            	if(!SGM.play) SGM.play = true;
	            	else SGM.play = false;
	            }}); 
			mainPanel.add(SGM.serverText);
			mainPanel.add(serverInitialText);
			mainPanel.add(playButton);
			JFrame frame = new JFrame("Starbird Server");
			frame.add(mainPanel);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setBounds(20, 20, 400 , 200);
			frame.setVisible(true);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateText(){
		String text = "<html>";
		for (SocketAddress SC : connections){
			text += SC.toString() + "<br>";
		}
		text += "</html>";
		serverText.setText(text);
	}
	
	private void updateConnections(DatagramPacket packet){
		SocketAddress ip = packet.getSocketAddress();
		if (!connections.contains(ip)) connections.add(ip);
	}
	
	public static void setButton(JButton x, String file, int locX, int locY, int sizeX, int sizeY){
		Image img = new ImageIcon(Server.class.getResource("/"+file)).getImage();
		x.setIcon(new ImageIcon(img));
		x.setFocusPainted(false);
	    x.setRolloverEnabled(true);
	    x.setBorderPainted(false);
	    x.setContentAreaFilled(false);
		x.setLocation(locX, locY);
		x.setSize(sizeX,sizeY);
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
					game.updatePlayer(p);
					GM.checkCollision();
					
					updateConnections(incomingPacket);
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

				} catch(ConcurrentModificationException e){
					// Nothing...
				} catch(IOException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		}
	}




}
