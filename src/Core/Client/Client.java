package Core.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Core.Serveur._Server;
import Services.DataUtilities.DataStorage;
import Services.DataUtilities.DataMessage;
import Services.DataUtilities.events.ArrayListener;


public class Client extends UnicastRemoteObject implements Runnable, _Client {
	
	
	/** Identification block and connection details*/
	protected static final long serialVersionUID = -3125971307045486820L;
	private final _Server myServer;
	protected final String idClient;
	
	
	/** Data storage and other facilities  */
	private DataStorage messageStorage;
	protected boolean stateSession;
	
	protected Thread main_run ;
	
    /**
     * Instaciation d'un client renseignant son serveur
     * @param Server
     * @throws RemoteException
     */
    public Client(_Server server, String id) throws RemoteException {
        this.myServer = server;
        this.idClient = id;
        
        this.setStateSession(true);
        
        /* instanciating the data pile storage of the messages of this client */
        this.messageStorage = new DataStorage(this.idClient); 
        
    }
    
    /** Observer Pattern : allow update if server notify() */
    @Override
    public void update(DataMessage message) throws RemoteException {
        messageStorage.add(message);
    }
    	
    @Override
    public void send(DataMessage message) throws RemoteException {
    	myServer.broadcastData(message);
    }

    @Override
    public void disconnectClient() throws RemoteException {
            myServer.disconnectClient(this);
            synchronized (this) {
                this.setStateSession(false);
                this.notifyAll();
            }
           
    }
    
    
    public void subToMessStorage(ArrayListener listener) {
    	this.messageStorage.addListener(listener);
    }
    
    @Override
    public String getIdClient()  throws RemoteException{
    	return this.idClient;
    }
    

    /** private methods: Handling the different status of the Client */
	public boolean isStateSession() {
		return stateSession;
	}


	protected void setStateSession(boolean stateSession)  {
		this.stateSession = stateSession;
	}
	

	
	
	/** END of status handling 
	 * @throws RemoteException */
	
	@Override
	public void initializeThread() throws RemoteException {
		
		
		System.out.println("Initializing the thread...");
		main_run = new Thread(this);
        
        /* calling the server method to broadcast the message */
		//System.out.println(this.getClass().getName());
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

	@Override
	public void connectToServer() throws RemoteException {
		myServer.connectClient(this);
	}
	

}
