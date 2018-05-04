package Core.Session.User;

import java.rmi.RemoteException;

import Core.Client.ObserverClientI;
import Core.Session.Sign;
import Services.DataUtilities.Data_message;

public interface InterfaceUser extends ObserverClientI{

	public Sign getDetails();
	public void send(Iterable<User> pool , Data_message data) throws RemoteException;

}
