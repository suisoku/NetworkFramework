package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import Core.BD.InteractionBD;
import Core.Client._Client;
import Core.Serveur.Server;
import Core.Session.AccountInfo;
import Core.Session.User._User;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.Groups.PoolHandler;

public class ServerSession extends Server implements _ServerSession{
	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd ;
	
	private HashMap<String, _User> users;
	private PoolHandler poolhandler;
	
	
	public ServerSession(InteractionBD bd_con) throws RemoteException {
		super();
		users = new HashMap<String, _User>();
		
		this.bd = bd_con;
		this.poolhandler = new PoolHandler(this.bd);
	}
	
	
	@Override
	public boolean authentication(_User user) throws RemoteException {
			try {
				return bd.identify(user.getDetails());
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}	
	}
	
	@Override	
	public _User lookupUser(AccountInfo details) throws RemoteException {
		for(String key: users.keySet()) {
			if(key.equals(details.getPseudo())){
				return users.get(key);
			}
		}
		return null;
	}
	
	@Override
	public boolean register(AccountInfo details) throws RemoteException, SQLException {
		if(!bd.identify(details)) {
			bd.addAccount(details);
			return true;
		}
		else return false;
		
	}
	

	
	@Override
	public synchronized void sendToPool(AccountInfo user_details , DataMessage data) throws RemoteException {
		
		
		
		try {
			if(bd.lookUser(user_details)) {
				
				_User right_user = this.lookupUser(user_details);
				if(right_user != null) {	right_user.update(data);	}
				
				bd.storeIntoBD(user_details.getPseudo(), data);
			}
			else  throw new JbeeException("user not found in DB");
		}
		catch (SQLException | ParseException e) {e.printStackTrace();}
	}	
	
	
	/** not tested yet **/
	@Override
	public synchronized void sendToPool(Iterable<AccountInfo> pool , DataMessage data) throws RemoteException {
		for(AccountInfo u : pool) {
			sendToPool(u , data);
		}
	}
	
 
    /** synchronized allow safe resources accessing  **/
	@Override
    public synchronized void connectClient(_Client u) throws RemoteException {
		
		_User user =  (_User)u;
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
	public synchronized void disconnectClient(_Client u) throws RemoteException {
		 this.users.remove(u.getIdClient());
	}
	
	@Override 
	public synchronized int getNbClients() throws RemoteException {
		return this.users.size();
	}
	
	@Override	
	public synchronized PoolHandler p_handler() throws RemoteException {
		return this.poolhandler;
	}
	
	
}
