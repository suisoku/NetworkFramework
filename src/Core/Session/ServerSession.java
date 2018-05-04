package Core.Session;

import java.rmi.RemoteException;

import Core.BD.connection;
import Core.Serveur.Server;
import Services.DataUtilities.Data_message;

public class ServerSession extends Server {


	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd = new InteractionBD(connection.getConnection() , "ACCOUNTS");

	public ServerSession() throws RemoteException {
		super();
	}
	
	public void authentication(Sign details) {
		if() {
			this.chatClient.
		}
	}
	
	public void register(Sign details) {
		
	}
	
	public synchronized void send_data(Data_message data) throws RemoteException {
		
	}
	

}
