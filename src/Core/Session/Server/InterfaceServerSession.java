package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import Core.UserInfo;
import Core.Serveur.ObservableServerI;
import Core.Session.User.InterfaceUser;
import Services.DataUtilities.Data_message;

public interface InterfaceServerSession extends ObservableServerI{

	public boolean authentication(InterfaceUser user) throws RemoteException;
	public boolean register(UserInfo details) throws RemoteException, SQLException;
	public void sendToPool(Iterable<UserInfo> pool, Data_message data) throws RemoteException;
	public void sendToPool(UserInfo user , Data_message data) throws RemoteException ;
	public InterfaceUser lookupUser(UserInfo details) throws RemoteException;
}
