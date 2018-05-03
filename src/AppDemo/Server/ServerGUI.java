package AppDemo.Server;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

import Core.Serveur.Server;

class ServerGUI extends JFrame implements ActionListener {

	/** default serial **/
	private static final long serialVersionUID = 1L;
	
	private final JLabel welcome = new JLabel("Chat Server Initialized");
    private final JPanel panel = new JPanel();
    private final JButton close = new JButton("Terminate Session");
    private final JLabel time = new JLabel();
    private final JLabel clientList = new JLabel("Clients Connected :" + "0");
    private final JButton clients_total = new JButton("Refresh Client List");

    public ServerGUI() {
        super("Chat Server");
        setUpWindow();
        addEventListeners();
        int myLocalVar = Server.getClients();
        clientList.setText("" + myLocalVar);
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

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == close) {

            System.exit(0);
        }
        if (source == clients_total) {
            int myLocalVar = Server.getClients();
            clientList.setText("Total Connected Today: " + myLocalVar);
        }
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
    	Server server = new Server();
    	server.initialize();
    	new ServerGUI();
    }
}
