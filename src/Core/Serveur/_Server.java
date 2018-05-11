package Core.Serveur;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Core.Client._Client;
import Services.DataUtilities.DataMessage;

public interface _Server extends Remote {
    public void connectClient(_Client client) throws RemoteException;
    public void broadcastData(DataMessage data) throws RemoteException;
    public void initialize() throws RemoteException, MalformedURLException;
	public void disconnectClient(_Client oc) throws RemoteException;
	public int getNbClients() throws RemoteException;
}
