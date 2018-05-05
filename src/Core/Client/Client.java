package Core.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import Core.Serveur.ObservableServerI;
import Services.DataUtilities.DataStorage;
import Services.DataUtilities.Data_message;
import Services.DataUtilities.events.ArrayListener;


public class Client extends UnicastRemoteObject implements Runnable, ObserverClientI {
	
	
	/** Identification block and connection details*/
	protected static final long serialVersionUID = -3125971307045486820L;
	private final ObservableServerI myServer;
	protected final String idClient;
	
	
	/** Data storage and other facilities  */
	protected DataStorage messageStorage;
	protected boolean stateSession;
	
	protected Thread main_run ;
	
    /**
     * Instaciation d'un client renseignant son serveur
     * @param Server
     * @throws RemoteException
     */
    public Client(ObservableServerI server, String id) throws RemoteException {
        this.myServer = server;
        this.idClient = id;
        
        this.setStateSession(true);
        
        /* instanciating the data pile storage of the messages of this client */
        this.messageStorage = new DataStorage(this.idClient); 
        
    }
    
    /** Observer Pattern : allow update if server notify() */
    @Override
    public void update(Data_message message) throws RemoteException {
        messageStorage.add(message);
    }
    	
    @Override
    public void send(Data_message message) throws RemoteException {
    	myServer.broadcastData(message);
    }

    @Override
    public void disconnectClient() throws RemoteException {
            myServer.disconnectClient();
            synchronized (this) {
                this.setStateSession(false);
                this.notifyAll();
            }
           
    }
    
    public void subToMessStorage(ArrayListener listener) {
    	this.messageStorage.addListener(listener);
    }
    
    public String getIdClient() {
    	return this.idClient;
    }
    
    /** private methods: Handling the different status of the Client */
	private boolean isStateSession() {
		return stateSession;
	}

	private void setStateSession(boolean stateSession) {
		this.stateSession = stateSession;
	}
	

	
	
	/** END of status handling 
	 * @throws RemoteException */
	
	@Override
	public void initializeThread() throws RemoteException {
		
		/* adding the client to the server */
        myServer.broadcastData(new Data_message(idClient, new Date(), new String("is connected")));
        
        /* calling the server method to broadcast the message */
		myServer.connectClient(this);
		
		main_run = new Thread(this);
	}
	
	
	public Thread threadAccess() {
		return this.main_run;
	}
	
	@Override
    public void run() {
		 System.out.println("Entering run state...");
		 synchronized (this) {
	            while (isStateSession()) {
	                try {
	                    this.wait();
	                } catch (InterruptedException e) {}
	            }
	        }
         System.out.println("Exiting run method");
    }
}
