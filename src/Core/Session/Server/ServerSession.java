package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.InteractionBD;
import Core.Sign;
import Core.BD.connection;
import Core.Client.ObserverClientI;
import Core.Serveur.Server;
import Core.Session.User.InterfaceUser;
import Core.Session.User.User;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.Data_message;

public class ServerSession extends Server implements InterfaceServerSession{
	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd = new InteractionBD(connection.getConnection() , "USERS");
	protected final ArrayList<InterfaceUser> chatClients;
	
	public ServerSession() throws RemoteException {
		super();
		chatClients = new ArrayList<InterfaceUser>();
	}
	
	@Override
	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException {
		
		if(bd.look(user.getDetails())) {
			this.connectClient(user);
			return true;
		}
		else return false;
	}
	
	private InterfaceUser lookupUser(Sign details) {
		for(InterfaceUser user : chatClients) {
			if(user.getDetails().getPseudo().equals(details.getPseudo())){
				return user;
			}
		}
		return null;
	}
	
	@Override
	public boolean register(Sign details) throws RemoteException, SQLException {
		if(!bd.look(details)) {
			bd.add(details);
			return true;
		}
		else return false;
		
	}
	
	@Override
	public synchronized void sendToPool(Iterable<User> pool , Data_message data) throws RemoteException {
		for(User user : pool) {
			user.update(data);
		}
	}
	
	@Override
	public synchronized void sendToPool(Sign user , Data_message data) throws RemoteException {
		InterfaceUser right_user = this.lookupUser(user);
		
		if(right_user != null) {
			right_user.update(data);
		}
		else throw new JbeeException("Erreur rechercher utilisateur durant send");
		
	}	

}
