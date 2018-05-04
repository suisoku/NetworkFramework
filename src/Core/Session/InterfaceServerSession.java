package Core.Session;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Serveur.ObservableServerI;

public interface InterfaceServerSession extends ObservableServerI {

	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException;
	public boolean register(InterfaceUser user) throws RemoteException, SQLException;
	
}
