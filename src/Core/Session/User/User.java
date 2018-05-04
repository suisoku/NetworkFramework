package Core.Session.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import Core.Client.Client;
import Core.Session.Sign;
import Core.Session.Server.InterfaceServerSession;
import Core.Session.User.InterfaceUser;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.Data_message;
import Services.Groups.InterfaceGroupe;

public class User extends Client implements InterfaceUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Sign signInDetails;
	protected boolean authentificated;
	protected InterfaceServerSession Server = (InterfaceServerSession)this.Server;
		

	public User(InterfaceServerSession server, Sign details) throws RemoteException, SQLException  {
		super(server, details.getPseudo()); 
		
		this.Server = server;
		this.signInDetails = details;
		
		if(!this.Server.authentication(this)) {
			throw new JbeeException("Authentification failed");
		}
		else this.authentificated = true;
	}
	
	public Sign getDetails() {
		return this.signInDetails;
	}

	@Override
	public void send(Iterable<User> pool, Data_message data) throws RemoteException {
		this.Server.sendToPool(pool, data);
	}
	
	@Override
	public void send(User user, Data_message data) throws RemoteException {
		this.Server.sendToPool(user, data);
	}
	
	@Override
	public void signOut() throws RemoteException {
		this.disconnectClient();
		this.authentificated = false;
	}
	
	public boolean isAuthentificated() {
		return authentificated;
	}

	@Override
	public void joinPool(InterfaceGroupe pool) {
		
		/* save group */ 
	}

	@Override
	public void leavePool(InterfaceGroupe pool) {
	}
	
	/** static method to register in server 
	 * 
	 *  */
	public static void register(InterfaceServerSession server , Sign details) throws RemoteException, SQLException {
		server.register(details);
	}
	
}
