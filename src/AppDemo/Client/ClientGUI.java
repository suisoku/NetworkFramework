package AppDemo.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Core.BD.Predicat;
import Core.Session.AccountInfo;
import Core.Session.Server._ServerSession;
import Core.Session.User.User;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.DataUtilities.FileData;
import Services.DataUtilities.events.ArrayListener;
import Services.DataUtilities.events.EventMessage;

public class ClientGUI extends JFrame implements ActionListener, ArrayListener {

	/** ------------------- Identity related declarations ----------------- */
	private final User clientService;
	private static final long serialVersionUID = 1L;

	/** ---------------------- State related declarations ------------------- */
	static Boolean posted = false;
	static Boolean disconnected = false;

	/** ----------------------- GUI related declaration----------------------- */
	private static final JLabel message = new JLabel("Joined at: ");
	private static final JLabel welcome = new JLabel();
	static final JTextField field = new JTextField(20);
	private static final JButton post = new JButton("Post Message");
	private static final JButton disconnect = new JButton("Disconnect");
	static final JTextArea area = new JTextArea(5, 5);

	/** ----HARD STUFF TEST---- */
	private static final JButton upload = new JButton("Upload File");
	static final JTextField fieldUser = new JTextField(20);

	/** ---- END OF HARD STUFF TEST---- */

	public ClientGUI(User clientService) {

		super("ClientGUI Chat");
		this.clientService = clientService;
		setUpPanel(this);
		setAddEventListeners();
		clientService.subToMessStorage(this);
	}

	private void setUpPanel(ClientGUI clientGUI) {
		JPanel panel_north = new JPanel();
		JPanel panel_middle = new JPanel(new GridLayout(1, 2));
		panel_north.add(welcome);
		panel_north.add(message);
		JPanel panel_south = new JPanel();
		panel_south.add(field);
		panel_south.add(fieldUser);
		panel_south.add(post);
		panel_south.add(upload);

		clientGUI.setResizable(false);
		clientGUI.setVisible(false);
		clientGUI.showTime();

		area.setEditable(false);
		addScrollPanelToPanel(panel_middle);

		clientGUI.setUserTextColor();
		setComponentColors(panel_south, panel_north);

		clientGUI.setBounds(400, 400, 750, 500);
		addEverythingToMainPanel(clientGUI, panel_middle, panel_north, panel_south);

		area.setBackground(Color.white);
		clientGUI.setVisible(true);
	}

	private void addScrollPanelToPanel(JPanel panel_middle) {
		JScrollPane scrollPane = new JScrollPane(area);
		panel_middle.add(scrollPane);
	}

	private void setComponentColors(JPanel panel_south, JPanel panel_north) {
		post.setBackground(Color.BLACK);
		post.setForeground(Color.WHITE);
		disconnect.setBackground(Color.BLACK);
		disconnect.setForeground(Color.WHITE);
		panel_north.setBackground(Color.WHITE);
		panel_south.setBackground(Color.WHITE);
	}

	private void addEverythingToMainPanel(ClientGUI clientGUI, JPanel panel_middle, JPanel panel_north,
			JPanel panel_south) {
		JPanel c = new JPanel();
		c.setBackground(Color.WHITE);
		c.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.setLayout(new BorderLayout(0, 0));
		clientGUI.setContentPane(c);
		c.add(panel_north, BorderLayout.NORTH);
		c.add(panel_middle, BorderLayout.CENTER);
		c.add(panel_south, BorderLayout.SOUTH);
		panel_north.add(disconnect);
	}

	private void setAddEventListeners() {
		field.addActionListener(this);
		fieldUser.addActionListener(this);
		post.addActionListener(this);
		disconnect.addActionListener(this);
		upload.addActionListener(this);
	}

	private int generateRandomRGBValue() {
		Random rand = new Random();
		return rand.nextInt(255);
	}

	private void setUserTextColor() {
		area.setForeground(new Color(generateRandomRGBValue(), generateRandomRGBValue(), generateRandomRGBValue()));
	}

	private void showTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		message.setText("Joined: " + dateFormat.format(cal.getTime()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == post) {
			System.out.println(field.getText());
			posted = true;

			/** --ADDING HARD STUFF-- */
			try {
				/**this.clientService.send(new AccountInfo(new Predicat("PSEUDO", fieldUser.getText())),
						new DataMessage(this.clientService.getIdClient(), new Date(), field.getText())); **/
				this.clientService.groupService().sendToChatGroup(new DataMessage(this.clientService.getIdClient(), new Date(), field.getText()), "LIONS");

				ClientGUI.posted = false;
				ClientGUI.field.setText("");
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/** ------ END OF HARD STUFF -- */
		}

		else if (source == disconnect) {

			disconnected = true;

			/** --ADDING HARD STUFF-- */
			try {
				// this.clientService.send(new Data_message(this.clientService.getIdClient(),
				// new Date(), "has disconnected" ));
				this.clientService.signOut();
				ClientGUI.disconnected = false;
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/** ------ END OF HARD STUFF -- */

			dispose();
		}

		else if (source == upload) {
			System.out.println("Uploading ...");

			FileData fileTest = new FileData("2.jpg", "3.jpg");
			try {
				fileTest.serializeFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					this.clientService.send(new DataMessage(this.clientService.getIdClient(), new Date(), fileTest));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			ClientGUI.field.setText("");
		}
	}

	@Override
	public void message_added(EventMessage e) {

		ClientGUI.area.append("\n\n" + e.getLastMessage().getDate() + " - " + e.getLastMessage().getId_sender());

		if (e.getLastMessage().getData() instanceof String) {
			ClientGUI.area.append(" send : " + e.getLastMessage().getData());
		}

		else if (e.getLastMessage().getData() instanceof FileData) {
			ClientGUI.area.append(" envoi de fichier");
			FileData f = (FileData) e.getLastMessage().getData();
			try {
				f.unserializeFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static String getUserName() {
		String name = JOptionPane.showInputDialog("Enter Your Name");
		welcome.setText("Welcome " + name);
		return name;
	}

	public static String getPassword() {
		String pass = JOptionPane.showInputDialog("Enter Your pwd");
		return pass;
	}

	public static void welcomeUser(String name) {
		System.out.println("Welcome " + name);
		System.out.println("Please Enter A Message");
	}

	public static void main(String[] args)
			throws MalformedURLException, RemoteException, NotBoundException, SQLException, JbeeException {

		/** looking remote interface of server */
		String chatServerURL = "rmi://localhost/RMIChatServer";
		_ServerSession chatServer = (_ServerSession) Naming.lookup(chatServerURL);
		
		
		AccountInfo account_details = new AccountInfo(new Predicat("PSEUDO" , ClientGUI.getUserName() ), new Predicat("PASSWORD" ,ClientGUI.getPassword()) );
		
		User userService = new User(chatServer, account_details);
		
		/**
		 * decoupled way String name = ClientGUI.getUserName(); String password =
		 * ClientGUI.getPassword(); Sign details = new Sign(name, password);
		 * 
		 **/
		/**
		 * you have the possibility to register the user before connect him ->
		 * userService.register();
		 */

		// userService.register();
		/** authentication -> connection -> dataReloading took in charge **/
		userService.connectToServer();

		if (userService.isAuthentificated()) {
			System.out.println("logged");

			/** initializing and starting the thread **/

			userService.initializeThread();
			userService.threadAccess().start();

			
			//userService.groupService().createChatGroup("LIONS");
			
			//userService.groupService().joinPool("LIONS");
			
			 //userService.groupService().createPubPool("TESTPOST");
			// userService.groupService().joinPool("TESTPOST");
			 
			 //Publication p = new Publication(userService.getDetails() , "Les chats" , "les chats c bien ouououee");
			 //userService.groupService().addPost(p, "TESTPOST");
			 
		//	 HashMap<UUID , Publication> map_post = userService.groupService().getMapPosts("TESTPOST");
			 
			 
			 /** ---------------------**/
			// Publication p = map_post.values().iterator().next();
			// System.out.println(p.getIdPost());
			// System.out.println(map_post.keySet().iterator().next());
			 //System.out.println("test" + map_post.get(p.getIdPost()).getTitlePost());
			 //Comment c = new Comment(userService.getDetails(), "Commentaire1");
			 
			//userService.groupService().addComment(c, p.getIdPost(), "TESTPOST");
			
		/*	for(Publication ps : map_post.values()) {
				System.out.println(ps.getTitlePost() + "  : " + ps.getIdPost());
				for(Comment cc : ps.getCommentList())System.out.println("Comment   : " + cc.getTextField());
			}*/
			
		/*	ArrayList<Object> list = new ArrayList<Object>();
			list.add(userService.getDetails().getPseudo());
			list.add("sssss@sss");
			list.add("gggggggggggggggg");
			
			Profile p = new Profile(userService.getDetails(), list);
			
			//userService.createProfile(p);
			
			// en 2 eme temps

			
			userService.editProfile(p);
			userService.loadProfile();
			for(Object o : userService.getProfile().getListElements())System.out.println(o);
			
			
			
			
			for(ArrayList<Object> list : userService.searchInBD("EMP","KING")) {
				for(Object o : list)System.out.println(o);
			}
			*/
			
			for(String s : userService.getAllChatGroups()) {
				System.out.println(s);
			}
			System.out.println("----------");
			for(String s : userService.getAllPostpools()) {
				System.out.println(s);
			}
			/** launching GUI **/
			new ClientGUI(userService);
			ClientGUI.welcomeUser(userService.getPseudo());

			/** reloading the user previous messages **/
			for (DataMessage mess : userService.getMessageStack()) {
				if (mess.getData() instanceof String) {
					ClientGUI.area
							.append("\n\n" + mess.getDate() + " - " + mess.getId_sender() + "  : " + mess.getData());
				}
			}

		} else
			System.out.println("log in : failed");
	}
}
