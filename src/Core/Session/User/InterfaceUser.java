package Core.Session.User;

import java.rmi.RemoteException;
import Core.Sign;
import Core.Client.ObserverClientI;
import Services.DataUtilities.Data_message;


public interface InterfaceUser extends ObserverClientI{
	
	public Sign getDetails() throws RemoteException;
	public void send(Iterable<User> pool , Data_message data) throws RemoteException;
	public void send(Sign user, Data_message data) throws RemoteException;
	public void signOut() throws RemoteException;
//	public void joinPool(InterfaceGroupe pool) throws RemoteException;
//	public void leavePool(InterfaceGroupe pool) throws RemoteException;
	public boolean isAuthentificated() throws RemoteException;
	public String getPseudo() throws RemoteException;
}
