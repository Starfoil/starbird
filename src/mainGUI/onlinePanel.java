package mainGUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import online.ClientBoard;
import online.ClientGame;
import main.SingleplayerBoard;
import main.Skin;

public class onlinePanel extends GUIPanel{

	JLabel connectionStatus;
	JTextField IPaddress;
	JTextField port;
	JTextField name;

	JButton connectButton;

	Font font14 = new Font("Georgia", Font.PLAIN, 14);
	Font font16 = new Font("Georgia", Font.PLAIN, 16);
	Font font18 = new Font("Georgia", Font.PLAIN, 18);
	Font font24 = new Font("Georgia", Font.PLAIN, 24);
	Font font32 = new Font("Georgia", Font.PLAIN, 32);

	public onlinePanel(){
		super();
		backgroundIMG = SystemData.skinBG;
		addLabel("IP:", font16, 350, 220);
		addLabel("Port:", font16, 700, 220);
		addLabel("Name:", font16, 425, 100);
		connectionStatus = addLabel("", font16, 500, 410);
		IPaddress = addTextField(PlayerData.savedIP, font24, 350, 250, 300, 50);
		port = addTextField(PlayerData.savedPort, font24, 700, 250, 100, 50);
		name = addTextField(PlayerData.playerName, font24, 500, 100, 200, 50);

		JButton play = createButton(SystemData.playButton, SystemData.playButtonP, 475, 450, 250, 100);
		play.addActionListener(new ActionListener() {	 
			public void actionPerformed(ActionEvent e){	
				if(updateStatusLabel()){
					MainMenu.jtp.removeAll();
					try {
						MainMenu.ogame = new ClientGame(IPaddress.getText(), Integer.parseInt(port.getText()));
					} catch (NumberFormatException | SocketException
							| UnknownHostException | InterruptedException e1) {
						e1.printStackTrace();
					}
					MainMenu.jtp.addTab("Game", MainMenu.ogame.CB);
					MainMenu.jtp.addTab("Exit", new JPanel());
					MainMenu.ogame.CB.requestFocusInWindow();
					MainMenu.activeGame = true;
					play.setIcon(new ImageIcon(SystemData.playButton));
				}
			}
		});
	}

	private boolean updateStatusLabel(){
		PlayerData.savedIP = IPaddress.getText();
		PlayerData.savedPort = port.getText();
		PlayerData.playerName = name.getText();
		PlayerData.saveData();
		if (validName(name.getText())){
			if (validIP(IPaddress.getText())){
				if (validPort(port.getText())){
					// Valid Connection
					connectionStatus.setText("");
					return true;

				}else{
					connectionStatus.setText("Invalid port entered [1024 - 65535]");
					return false;
				}
			}else{
				connectionStatus.setText("Invalid IP address entered");
				return false;
			}
		}else{
			connectionStatus.setText("Name must be 3-12 characters");
			return false;
		}
	}

	private boolean validName (String name){
		if (name.length() > 12 || name.length() < 3){
			return false;
		}
		return true;
	}

	private boolean validPort (String portstr){
		try{
			int port = Integer.parseInt(portstr);
			if ( port < 1024 || port > 65535) return false;
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	// Online code
	private boolean validIP (String ip) {
		try {
			if ( ip == null || ip.isEmpty() ) {
				return false;
			}
			String[] parts = ip.split( "\\." );
			if ( parts.length != 4 ) {
				return false;
			}
			for ( String s : parts ) {
				int i = Integer.parseInt( s );
				if ( (i < 0) || (i > 255) ) {
					return false;
				}
			}
			if ( ip.endsWith(".") ) {
				return false;
			}
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}


}
