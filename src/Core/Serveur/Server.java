 package Core.Serveur;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Core.Client.ObserverClientI;
import Core.Session.Server.ServerSession;
import Services.DataUtilities.Data_message;

public class Server extends UnicastRemoteObject implements ObservableServerI {
	/** default serial **/
	private static final long serialVersionUID = 1L;
	private final ArrayList<ObserverClientI> chatClients;
	
    public Server() throws RemoteException {
        chatClients = new ArrayList<ObserverClientI>();
    }

    @Override
    /** synchronized allow safe resources accessing **/
    public synchronized void connectClient(ObserverClientI chatClient)throws RemoteException {
        this.chatClients.add(chatClient);
    }

    @Override
    public synchronized void broadcastData(Data_message message)throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).update(message);
        }
    }

    @Override
    public synchronized int getNbClients()  throws RemoteException {
        return  this.chatClients.size();
	}
    

    
    @Override
    public synchronized void disconnectClient(ObserverClientI c) throws RemoteException {
        for(int i = 0 ; i < this.chatClients.size() ; i++){
        	if(this.chatClients.get(i).getIdClient().equals(c.getIdClient()) )this.chatClients.remove(i);	
        }
    }
    
    @Override
    public void initialize() throws RemoteException, MalformedURLException {
    	try {java.rmi.registry.LocateRegistry.createRegistry(1099);}
        catch (Exception e) {e.printStackTrace();}
        Naming.rebind("RMIChatServer", this);
    }


}
