package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Core.Serveur._Server;
import Core.Session.AccountInfo;
import Core.Session.User._User;
import Services.DataUtilities.DataMessage;
import Services.Groups.PoolHandler;

public interface _ServerSession extends _Server{

	public boolean authentication(_User user) throws RemoteException;
	public boolean register(AccountInfo details) throws RemoteException, SQLException;
	public void sendToPool(Iterable<AccountInfo> pool, DataMessage data) throws RemoteException;
	public void sendToPool(AccountInfo user , DataMessage data) throws RemoteException ;
	public _User lookupUser(AccountInfo details) throws RemoteException;
	public PoolHandler p_handler() throws RemoteException;
}
