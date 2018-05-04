package Core.Session.User;

import java.rmi.RemoteException;

import Core.Client.ObserverClientI;
import Core.Session.Sign;
import Services.DataUtilities.Data_message;
import Services.Groups.InterfaceGroupe;

public interface InterfaceUser extends ObserverClientI{
	
	public Sign getDetails();
	public void send(Iterable<User> pool , Data_message data) throws RemoteException;
	public void send(User user, Data_message data) throws RemoteException;
	public void signOut() throws RemoteException;
	public void joinPool(InterfaceGroupe pool);
	public void leavePool(InterfaceGroupe pool);
}
