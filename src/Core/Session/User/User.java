package Core.Session.User;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.UserInfo;
import Core.Client.Client;
import Core.Session.Server.InterfaceServerSession;
import Services.DataUtilities.DataStorage;
import Services.DataUtilities.Data_message;

public class User extends Client implements InterfaceUser{

	private static final long serialVersionUID = 1L;
	
	protected UserInfo signInDetails;
	protected boolean authentificated = false;
	private InterfaceServerSession myServer;
	private DataStorage messageStorage;

	public User(InterfaceServerSession server, UserInfo details) throws RemoteException, SQLException  {
		super(server, details.getPseudo()); 
		
		this.myServer = (InterfaceServerSession) server;
		this.signInDetails = details;
	}
	
	@Override
	public UserInfo getDetails() throws RemoteException {
		return this.signInDetails;
	}

	@Override 
	public void send(Iterable<UserInfo> pool, Data_message data) throws RemoteException {
		this.myServer.sendToPool(pool, data);
	}
	
	@Override
	public void send(UserInfo user, Data_message data) throws RemoteException {
		this.myServer.sendToPool(user, data);
		this.myServer.sendToPool(this.signInDetails, data);
	}
	
	@Override
	public void signOut() throws RemoteException {
		this.disconnectClient();
		this.myServer.disconnectClient(this);
		this.authentificated = false;
	}
	
	@Override
	public boolean isAuthentificated() throws RemoteException{
		return authentificated;
	}
	
	@Override
	public void setAuthentificated(boolean t) throws RemoteException{
		this.authentificated = t;
	}


//	@Override
//	public void joinPool(InterfaceGroupe pool)  throws RemoteException{} 

//	@Override
//	public void leavePool(InterfaceGroupe pool) throws RemoteException {}
	
	@Override
	public  void register() throws RemoteException, SQLException {
		this.myServer.register(this.signInDetails);
	}
	
	@Override
	public String getPseudo() throws RemoteException {
		return this.signInDetails.getPseudo();
	}
	
	@Override
	public ArrayList<Data_message> getMessageStack() throws RemoteException{
		return (ArrayList<Data_message>)this.messageStorage;
	}

	@Override
	public void reloadData(ArrayList<Data_message> bdDataMessages) throws RemoteException {
		this.messageStorage = new DataStorage(this.getPseudo(), bdDataMessages);
		
	}
}
