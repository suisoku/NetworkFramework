package AppDemo.Server;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Core.BD.InteractionBD;
import Core.Session.Server.ServerSession;

class ServerGUI extends JFrame implements ActionListener {

	/** default serial **/
	private static final long serialVersionUID = 1L;
	
	private final JLabel welcome = new JLabel("Chat Server Initialized");
    private final JPanel panel = new JPanel();
    private final JButton close = new JButton("Terminate Session");
    private final JLabel time = new JLabel();
    private final JLabel clientList = new JLabel("Clients Connected :" + "0");
    private final JButton clients_total = new JButton("Refresh Client List");
    private  int nb;
    ServerSession server;
    
    public ServerGUI(ServerSession server) throws RemoteException {
        super("Serveur de communication");
        setUpWindow();
        addEventListeners();
        
        this.server = server;
        clientList.setText("" + server.getNbClients());
    }

    private void addEventListeners() {
        close.addActionListener(this);
        clients_total.addActionListener(this);
    }

    private void setUpWindow() {
        Container c = getContentPane();
        c.add(panel);

        panel.add(welcome);
        panel.add(time);
        panel.add(clientList);
        panel.add(clients_total);
        panel.add(close);
        setSize(200, 200);
        setResizable(false);
        setVisible(true);
        showTime();
    }

    private void showTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        time.setText(dateFormat.format(cal.getTime()));
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == close) {

            System.exit(0);
        }
        if (source == clients_total) {
        	try {
				nb = server.getNbClients();
				clientList.setText("Total Connected TodayY: " + nb);
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			}
			
            
        }
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, SQLException {
    	
    	InteractionBD personal_bd = new InteractionBD("USERS", "DATAMESSAGE");
    	
    	personal_bd.setPools_table("POOLS");
    	personal_bd.setGroup_data_table("GROUP_DATA");
    	personal_bd.setUser_pool_table("USER_POOL");
    	personal_bd.setPosts_table("POSTS");
    	personal_bd.setPool_post_table("POOL_POST");
    	personal_bd.setComments_table("COMMENTS");
    	personal_bd.setProfile_table("PROFIL");
    	
    	personal_bd.creationsSchema();
    	
    	ServerSession server = new ServerSession(personal_bd);
    	server.initialize();
    	
    	server.poolHandler().reloadEverything();
    	
    	new ServerGUI(server);
    }
}
