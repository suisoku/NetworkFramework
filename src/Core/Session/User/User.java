package Core.Session.User;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Sign;
import Core.Client.Client;
import Core.Session.Server.InterfaceServerSession;
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
	private InterfaceServerSession Server;
		

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
	public void send(Sign user, Data_message data) throws RemoteException {
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
	
	public String getPseudo() {
		return this.signInDetails.getPseudo();
	}
	
}
