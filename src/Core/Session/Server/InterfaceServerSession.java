package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Serveur.ObservableServerI;
import Core.Session.User.InterfaceUser;
import Core.Session.User.User;
import Services.DataUtilities.Data_message;

public interface InterfaceServerSession extends ObservableServerI {

	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException;
	public boolean register(InterfaceUser user) throws RemoteException, SQLException;
	public void sendToPool(Iterable<User> pool, Data_message data) throws RemoteException;
	public void sendToPool(User user , Data_message data) throws RemoteException ;
}
