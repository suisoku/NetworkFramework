package Core.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Services.DataUtilities.Data_message;



/**
 * Implemente la capacite d'envoi
 */
public interface ObserverClientI extends Remote {
    public void update(Data_message data) throws RemoteException;
    public void send(Data_message message) throws RemoteException;
    public void disconnectClient() throws RemoteException;
}
