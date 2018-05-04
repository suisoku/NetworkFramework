package Core.Session;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.BD.connection;
import Core.Serveur.Server;
import Services.DataUtilities.Data_message;

public class ServerSession extends Server implements InterfaceServerSession{


	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd = new InteractionBD(connection.getConnection() , "ACCOUNTS");

	public ServerSession() throws RemoteException {
		super();
	}
	
	@Override
	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException {
		
		if(bd.look(user.getDetails())) {
			this.connectClient(user);
			return true;
		}
		else return false;
	}
	
	@Override
	public boolean register(InterfaceUser user) throws RemoteException, SQLException {
		if(!bd.look(user.getDetails())) {
			bd.add(user.getDetails());
			return true;
		}
		else return false;
		
	}
	
	public synchronized void sendToPool(Iterable<User> pool , Data_message data) throws RemoteException {
		
	}
	public synchronized void sendToPool(User user , Data_message data) throws RemoteException {
		
	}
	

}
