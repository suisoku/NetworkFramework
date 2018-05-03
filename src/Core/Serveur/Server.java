 package Core.Serveur;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Core.Client.ObserverClientI;
import Services.DataUtilities.Data_message;

public class Server extends UnicastRemoteObject implements ObservableServerI {

	/** default serial **/
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<ObserverClientI> chatClients;
    private static int client_count = 0;

    public Server() throws RemoteException {
        chatClients = new ArrayList<>();
    }

    @Override
    /** synchronized allow safe resources accessing **/
    public synchronized void connectClient(ObserverClientI chatClient)throws RemoteException {
        this.chatClients.add(chatClient);
        client_count++;
    }

    @Override
    public synchronized void broadcastData(Data_message message)throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).update(message);
        }
    }

    public static int getClients() {
        return client_count;
    }
    
    @Override
    public void disconnectClient() throws RemoteException {
        client_count--;
    }
    
    @Override
    public void initialize() throws RemoteException, MalformedURLException {
    	try {java.rmi.registry.LocateRegistry.createRegistry(1099);}
        catch (Exception e) {e.printStackTrace();}
        Naming.rebind("RMIChatServer",new Server());
    }

}
