package Core.Session.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.UserInfo;
import Core.Client.ObserverClientI;
import Services.DataUtilities.Data_message;


public interface InterfaceUser extends ObserverClientI{
	
	public UserInfo getDetails() throws RemoteException;
	public void send(Iterable<UserInfo> pool , Data_message data) throws RemoteException;
	public void send(UserInfo user, Data_message data) throws RemoteException;
	public void signOut() throws RemoteException;
//	public void joinPool(InterfaceGroupe pool) throws RemoteException;
//	public void leavePool(InterfaceGroupe pool) throws RemoteException;
	public boolean isAuthentificated() throws RemoteException;
	public String getPseudo() throws RemoteException;
	public void setAuthentificated(boolean t) throws RemoteException;
	public void register() throws RemoteException, SQLException;
	public ArrayList<Data_message> getMessageStack() throws RemoteException;
	public void reloadData(ArrayList<Data_message> loadDataMessages) throws RemoteException;
}
