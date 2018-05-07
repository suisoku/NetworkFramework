package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import Core.UserInfo;
import Core.BD.InteractionBD;
import Core.BD.connection;
import Core.Client.ObserverClientI;
import Core.Serveur.Server;
import Core.Session.User.InterfaceUser;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.Data_message;

public class ServerSession extends Server implements InterfaceServerSession{
	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd ;
	
	private HashMap<String, InterfaceUser> users;
	
	public ServerSession() throws RemoteException {
		super();
		
		bd = new InteractionBD(connection.getConnection() , "USERS", "DATAMESSAGE");
		
		users = new HashMap<String, InterfaceUser>();
	}
	
	@Override
	public boolean authentication(InterfaceUser user) throws RemoteException {
			try {
				return bd.identify(user.getDetails());
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}	
	}
	
	@Override	
	public InterfaceUser lookupUser(UserInfo details) throws RemoteException {
		for(String key: users.keySet()) {
			if(key.equals(details.getPseudo())){
				return users.get(key);
			}
		}
		return null;
	}
	
	@Override
	public boolean register(UserInfo details) throws RemoteException, SQLException {
		if(!bd.identify(details)) {
			bd.add(details);
			return true;
		}
		else return false;
		
	}
	

	
	@Override
	public synchronized void sendToPool(UserInfo user_details , Data_message data) throws RemoteException {
		
		
		
		try {
			if(bd.lookUser(user_details)) {
				
				InterfaceUser right_user = this.lookupUser(user_details);
				if(right_user != null) {	right_user.update(data);	}
				
				bd.storeIntoBD(user_details.getPseudo(), data);
			}
			else  throw new JbeeException("user not found in DB");
		}
		catch (SQLException | ParseException e) {e.printStackTrace();}
	}	
	
	
	/** not tested yet **/
	@Override
	public synchronized void sendToPool(Iterable<UserInfo> pool , Data_message data) throws RemoteException {
		for(UserInfo u : pool) {
			sendToPool(u , data);
		}
	}
	
 
    /** synchronized allow safe resources accessing  **/
	@Override
    public synchronized void connectClient(ObserverClientI u) throws RemoteException {
		
		InterfaceUser user =  (InterfaceUser)u;
		if(authentication(user)) {
			
			this.users.put(user.getPseudo(), user);
		
			try {
				user.reloadData(bd.loadDataMessages(user.getDetails()));
			} catch (SQLException | ParseException e) { 	e.printStackTrace();}
		
			user.setAuthentificated(true);
		}
		else user.setAuthentificated(false);
	}
	
	@Override
	public synchronized void disconnectClient(ObserverClientI u) throws RemoteException {
		 this.users.remove(u.getIdClient());
	}
	
	@Override 
	public synchronized int getNbClients() throws RemoteException {
		return this.users.size();
	}
	
}
