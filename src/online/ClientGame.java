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

import main.GameInstance;
import main.Player;
import mainGUI.MainMenu;
import mainGUI.PlayerData;


public class ClientGame extends Thread{


	public ClientBoard CB;
	final static int pingPerSeconds = 80;

	DatagramSocket socket;
	ByteArrayInputStream byteIS;
	ObjectInputStream objectIS;
	ByteArrayOutputStream byteOS;
	ObjectOutputStream objectOS;
	byte[] playerData = new byte[8192];
	byte[] gameData = new byte[100000];

	InetAddress serverIP;
	int serverPort;

	public static Thread writer;
	public static Thread reader;

	public ClientGame(String serverIP, int serverPort) throws SocketException, InterruptedException, UnknownHostException{
		this.serverIP = InetAddress.getByName(serverIP);
		this.serverPort = serverPort;

		// Initialize Game Info
		socket = new DatagramSocket();
		GameInstance game = new GameInstance();

		String conPort = Integer.toString(socket.getLocalPort());
		Player p = new Player(PlayerData.playerName);
		game.addPlayer(p);
		p.playable = true;
		CB = new ClientBoard(game, p);	

		// Start threads
		writer = new ClientWriterThread();
		writer.start();
		reader = new ClientReaderThread();
		reader.start();
	}

	class ClientReaderThread extends Thread{
		public void run(){
			while(!Thread.currentThread().isInterrupted()){
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
			while(!Thread.currentThread().isInterrupted()){
				//System.out.println(this.toString());
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
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					//e.printStackTrace();
				}

			}	
		}
	}


}
