package Core.Session.Server;


import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Sign;
import Core.Session.User.InterfaceUser;
import Core.Session.User.User;
import Services.DataUtilities.Data_message;

public interface InterfaceServerSession {

	public boolean authentication(InterfaceUser user) throws RemoteException, SQLException;
	public boolean register(Sign details) throws RemoteException, SQLException;
	public void sendToPool(Iterable<User> pool, Data_message data) throws RemoteException;
	public void sendToPool(Sign user , Data_message data) throws RemoteException ;

}
