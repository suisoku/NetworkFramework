package Core.Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Core.Serveur.ObservableServerI;
import Services.DataUtilities.ArrayListener;
import Services.DataUtilities.Data_message;
import Services.DataUtilities.EventMessage;

public class ClientGUI extends JFrame implements ActionListener, ArrayListener {


	/** ------------------- Identity related declarations -----------------*/
	private final Client clientService;
	private static final long serialVersionUID = 1L;
	
	
	/** ---------------------- State related declarations -------------------*/
    static Boolean posted = false;
    static Boolean disconnected = false;
    
    /** -----------------------   GUI related declaration-----------------------*/
	private static final JLabel message = new JLabel("Joined at: ");
    private static final JLabel welcome = new JLabel();
    static final JTextField field = new JTextField(20);
    private static final JButton post = new JButton("Post Message");
    private static final JButton disconnect = new JButton("Disconnect");
    static final JTextArea area = new JTextArea(5, 5);

    
    
    
    public ClientGUI(Client clientService) {
    	
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
        panel_south.add(post);

        clientGUI.setResizable(false);
        clientGUI.setVisible(false);
        clientGUI.showTime();

        area.setEditable(false);
        addScrollPanelToPanel(panel_middle);

        clientGUI.setUserTextColor();
        setComponentColors(panel_south,panel_north);

        clientGUI.setBounds(400, 400, 750, 500);
        addEverythingToMainPanel(clientGUI,panel_middle,panel_north,panel_south);

        area.setBackground(Color.white);
        clientGUI.setVisible(true);
    }

    private void addScrollPanelToPanel(JPanel panel_middle){
        JScrollPane scrollPane = new JScrollPane(area);
        panel_middle.add(scrollPane);
    }

    private void setComponentColors(JPanel panel_south, JPanel panel_north){
        post.setBackground(Color.BLACK);
        post.setForeground(Color.WHITE);
        disconnect.setBackground(Color.BLACK);
        disconnect.setForeground(Color.WHITE);
        panel_north.setBackground(Color.WHITE);
        panel_south.setBackground(Color.WHITE);
    }

    private void addEverythingToMainPanel(ClientGUI clientGUI, JPanel panel_middle, JPanel panel_north, JPanel panel_south){
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

    private void setAddEventListeners(){
        field.addActionListener(this);
        post.addActionListener(this);
        disconnect.addActionListener(this);
    }

    private int generateRandomRGBValue(){
        Random rand = new Random();
        return rand.nextInt(255);
    }

    private void setUserTextColor(){
        area.setForeground(new Color(generateRandomRGBValue(),generateRandomRGBValue(),generateRandomRGBValue()));
    }

    private void showTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        message.setText("Joined: " + dateFormat.format(cal.getTime()));
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == post) {
            System.out.println(field.getText());
            posted = true;
            
            /** 	--ADDING HARD STUFF-- */
            try {
				this.clientService.send(new Data_message(this.clientService.getIdClient(), new Date(), field.getText() ));
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
            
            /** 	--ADDING HARD STUFF-- */
            try {
            	this.clientService.send(new Data_message(this.clientService.getIdClient(), new Date(), "has disconnected" ));
				this.clientService.disconnectClient();
				ClientGUI.disconnected = false;
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            /** ------ END OF HARD STUFF -- */
            
            dispose();
        }
    }
    
    public void message_added(EventMessage e) {
    	
        ClientGUI.area.append("\n\n" + 
        		e.getLastMessage().getDate() +
        		" - " +
        		e.getLastMessage().getId_sender() +
        		"send : " +
        		e.getLastMessage().getData().toString() ); 
    }



    public static String getUserName(){
        String name = JOptionPane.showInputDialog("Enter Your Name");
        welcome.setText("Welcome " + name);
        return name;
    }

    public static void welcomeUser(String name){
        System.out.println("Welcome " + name);
        System.out.println("Please Enter A Message");
    }

    public static void main(String[] args) throws MalformedURLException,RemoteException, NotBoundException {
        
    	/** declaring stuff  */
        
        String chatServerURL = "rmi://localhost/RMIChatServer";
		ObservableServerI chatServer = (ObservableServerI) Naming.lookup(chatServerURL);
		
        
		/** threading stuff */

        String name = ClientGUI.getUserName(); // Input User
        Client clientService = new Client(chatServer, name); // Crafting clientService
        
        Thread clientThread = new Thread(clientService); // running the thread
        clientThread.start();
        
        new ClientGUI(clientService); // Instantiate GUI with clientService
        
        ClientGUI.welcomeUser(name); // Hello
    }
}
