package Core.Session;

import java.rmi.RemoteException;

import Core.Client.Client;
import Core.Serveur.ObservableServerI;

public class User extends Client {

	private Sign signInDetails;
	
	
	public User(Sign details) throws RemoteException {
		super();
	}
	
	
	
}
