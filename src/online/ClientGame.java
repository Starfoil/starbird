package online;

import java.net.*;
import java.util.ConcurrentModificationException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.ClientBoard;
import main.Player;


public class ClientGame extends Thread{


	private ClientBoard CB;
	final static int pingPerSeconds = 80;
	public final static int XFRAME = 1200;
	public final static int YFRAME = 675;
	
	DatagramSocket socket;
	ByteArrayInputStream byteIS;
	ObjectInputStream objectIS;
	ByteArrayOutputStream byteOS;
	ObjectOutputStream objectOS;
	byte[] playerData = new byte[8192];
	byte[] gameData = new byte[100000];
	
	InetAddress serverIP;
	int serverPort;
	
	public ClientGame(){
		startConnection();
	}
	
	public ClientGame(InetAddress serverIP, int serverPort) throws SocketException, InterruptedException{
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		startGame(serverIP, serverPort);
	}
	
	public void run(){
		while(true){
			//
		}
	}
	
	private void startGame(InetAddress serverIP, int serverPort) throws SocketException, InterruptedException{
		// Initialize Game Info
		socket = new DatagramSocket();
		GameInstance game = new GameInstance();
		Player p = new Player(Integer.toString(socket.getLocalPort()));
		game.addPlayer(p);;
		CB = new ClientBoard(game, p);
		
		// Start GUI
		JFrame frame = new JFrame();
		frame.add(CB);
		frame.setTitle("Starbird 2017");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(XFRAME, YFRAME);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		// Start threads
		Thread writer = new ClientWriterThread();
		writer.start();
		Thread reader = new ClientReaderThread();
		reader.start();	
	}
	
	private void startConnection(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JLabel statusText = new JLabel();
		statusText.setBounds(10, 40, 240, 30);
		JTextField IPAddressField = new JTextField(150);
		IPAddressField.setBounds(10, 10, 120, 30);
		JTextField PortField = new JTextField(150);
		PortField.setBounds(140, 10, 70, 30);
		JButton playButton = new JButton();
		setButton(playButton, "1.png", 220, 0 ,52, 50);
		playButton.addActionListener(new ActionListener() {	 
            public void actionPerformed(ActionEvent e){	
            	InetAddress IP;
				try {
					IP = InetAddress.getByName(IPAddressField.getText());
					int port = Integer.parseInt(PortField.getText());
					serverIP = IP;
					serverPort = port;
	            	startGame(serverIP, serverPort);
				} catch (UnknownHostException e1) {
					statusText.setText("Host IP address invalid...");
				} catch (SocketException e1) {
					statusText.setText("Connection could not be established...");
				} catch (NumberFormatException e1){
					statusText.setText("Invalid port number...");
				} catch (InterruptedException e1) {
					statusText.setText("Something went wrong...");
				}
            }}); 
		panel.add(IPAddressField);
		panel.add(PortField);
		panel.add(playButton);
		panel.add(statusText);
		frame.add(panel);
		frame.setTitle("Connect to server...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(280, 100);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	public static void main(String [] args) {
		new ClientGame();
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
	
	class ClientReaderThread extends Thread{
		public void run(){
			while(true){
				try {
					//Thread.sleep(pingRate);
					DatagramPacket incomingPacket = new DatagramPacket(gameData, gameData.length);
					socket.receive(incomingPacket);
					gameData = incomingPacket.getData();
					byteIS = new ByteArrayInputStream(gameData);
					objectIS = new ObjectInputStream(byteIS);
					GameInstance g = (GameInstance) objectIS.readObject();
					CB.updateGame(g);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class ClientWriterThread extends Thread{
		public void run(){
			while(true){
				try {					
					// Write to server
					byteOS = new ByteArrayOutputStream();
					objectOS = new ObjectOutputStream(byteOS);
					objectOS.writeObject(CB.p);
					objectOS.flush();
					playerData = byteOS.toByteArray();
					DatagramPacket sendPacket = new DatagramPacket(playerData, playerData.length, serverIP, serverPort);
					socket.send(sendPacket);
					Thread.sleep(1000 / pingPerSeconds);
					
				} catch (ConcurrentModificationException e) {
					//e.printStackTrace();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}


}
