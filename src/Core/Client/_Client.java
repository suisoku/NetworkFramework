package Core.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Services.DataUtilities.DataMessage;



/**
 * Implemente la capacite d'envoi
 */
public interface _Client extends Remote {
    public void update(DataMessage data) throws RemoteException;
    public void send(DataMessage message) throws RemoteException;
    public void disconnectClient() throws RemoteException;
    public void connectToServer() throws RemoteException;
    public void initializeThread() throws RemoteException;
    public String getIdClient() throws RemoteException;
	
}
