package Core.Serveur;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Core.Client.ObserverClientI;
import Services.DataUtilities.Data_message;

public interface ObservableServerI extends Remote {
    public void connectClient(ObserverClientI client) throws RemoteException;
    public void broadcastData(Data_message data) throws RemoteException;
    public void disconnectClient() throws RemoteException;
    public void initialize() throws RemoteException, MalformedURLException;
}
