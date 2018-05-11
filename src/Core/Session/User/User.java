package Core.Session.User;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.Client.Client;
import Core.Session.AccountInfo;
import Core.Session.Server._ServerSession;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataStorage;
import Services.Groups.GroupService;
import Services.DataUtilities.DataMessage;

public class User extends Client implements _User{

	private static final long serialVersionUID = 1L;
	
	protected AccountInfo signInDetails;
	protected boolean authentificated = false;
	private _ServerSession myServer;
	private DataStorage messageStorage;
	private GroupService gs;
	
	public User(_ServerSession server, AccountInfo details) throws RemoteException, SQLException  {
		super(server, details.getPseudo()); 
		
		this.myServer = (_ServerSession) server;
		this.signInDetails = details;
		this.gs = new GroupService(details, server);
	}
	
	@Override
	public AccountInfo getDetails() throws RemoteException {
		return this.signInDetails;
	}

	@Override 
	public void send(Iterable<AccountInfo> pool, DataMessage data) throws RemoteException {
		this.myServer.sendToPool(pool, data);
	}
	
	@Override
	public void send(AccountInfo user, DataMessage data) throws RemoteException {
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
	
	@Override
	public  void register() throws RemoteException, SQLException {
		this.myServer.register(this.signInDetails);
	}
	
	@Override
	public String getPseudo() throws RemoteException {
		return this.signInDetails.getPseudo();
	}
	
	@Override
	public ArrayList<DataMessage> getMessageStack() throws RemoteException{
		if(this.messageStorage == null ) throw new JbeeException("Reloading Data failed or empty");
		else return (ArrayList<DataMessage>)this.messageStorage;
	}

	@Override
	public void reloadData(ArrayList<DataMessage> bdDataMessages) throws RemoteException {
		this.messageStorage = new DataStorage(this.getPseudo(), bdDataMessages);
		
	}
	
	@Override 
	public synchronized GroupService groupService() throws RemoteException {
		return this.gs;
	}
}
