package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Serveur.ObservableServerI;
import Core.Session.User.InterfaceUser;

public interface InterfaceServerSession extends ObservableServerI {

	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException;
	public boolean register(InterfaceUser user) throws RemoteException, SQLException;
	
}
