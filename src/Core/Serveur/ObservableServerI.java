package Core.Serveur;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Core.Client.ObserverClientI;

public interface ObservableServerI extends Remote {
    void connectClient(ObserverClientI client) throws RemoteException;
    void broadcastData(Data_message data) throws RemoteException;
    void disconnectClient() throws RemoteException;
}
