package Core.Session.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.Client._Client;
import Core.Session.AccountInfo;
import Services.DataUtilities.DataMessage;
import Services.Groups.GroupService;


public interface _User extends _Client{
	
	public AccountInfo getDetails() throws RemoteException;
	public void send(Iterable<AccountInfo> pool , DataMessage data) throws RemoteException;
	public void send(AccountInfo user, DataMessage data) throws RemoteException;
	public void signOut() throws RemoteException;
//	public void joinPool(InterfaceGroupe pool) throws RemoteException;
//	public void leavePool(InterfaceGroupe pool) throws RemoteException;
	public boolean isAuthentificated() throws RemoteException;
	public String getPseudo() throws RemoteException;
	public void setAuthentificated(boolean t) throws RemoteException;
	public void register() throws RemoteException, SQLException;
	public ArrayList<DataMessage> getMessageStack() throws RemoteException;
	public void reloadData(ArrayList<DataMessage> loadDataMessages) throws RemoteException;
	public GroupService groupService() throws RemoteException;
}
