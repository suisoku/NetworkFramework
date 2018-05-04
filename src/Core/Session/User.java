package Core.Session;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;

import Core.Client.Client;
import Core.Serveur.ObservableServerI;
import JBeeExceptions.JbeeException;

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
		
	}
	
	public Sign getDetails() {
		return this.signInDetails;
	}
	
	
	
	
}
