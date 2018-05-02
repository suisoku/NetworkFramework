package Core.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import Core.Serveur.DataStorage;
import Core.Serveur.Data_message;
import Core.Serveur.ObservableServerI;


public class Client extends UnicastRemoteObject implements Runnable, ObserverClientI {
	
	
	/** Identification block and connection details*/
	
	private static final long serialVersionUID = -3125971307045486820L;
	private final ObservableServerI Server;
	private final String idClient;
	
	
	/** Data storage and other facilities  */
	private DataStorage messageStorage;
	private boolean stateSession;
	//private boolean sendStatus;
	
	
	
    /**
     * Instaciation d'un client renseignant son serveur
     * @param Server
     * @throws RemoteException
     */
    public Client(ObservableServerI Server, String id) throws RemoteException {
        this.Server = Server;
        this.idClient = id;
        
        this.setStateSession(true);
        
        /* instanciating the data pile storage of the messages of this client */
        this.messageStorage = new DataStorage(this.idClient); 
        
        /* adding the client to the server */
        Server.connectClient(this);
        
        /* calling the server method to broadcast the message */
        Server.broadcastData(new Data_message(idClient, new Date(), new String("is connected")));
    }
    
    /** Observer Pattern : allow update if server notify() */
    @Override
    public void update(Data_message message) throws RemoteException {
        messageStorage.add(message);
    }
    	
    
    public void send(Data_message message) throws RemoteException {
    	Server.broadcastData(message);
    }

    public void disconnectClient() throws RemoteException {
            Server.broadcastData(new Data_message(idClient, new Date(), new String("has leaved")));
            Server.disconnectClient();
            this.setStateSession(false);
    }
    
    

    /*
    private void check() {
        String text = ClientGUI.field.getText();

        if (ClientGUI.posted) {
            try {
                Server.broadcastData(idClient + " : " + text);
                ClientGUI.posted = false;
                ClientGUI.field.setText("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkDisconnect() throws RemoteException {
        if(ClientGUI.disconnected){
            Server.broadcastData(new Data_message(idClient, new Date(), new String("has leaved")));
            ClientGUI.disconnected =false;
            Server.disconnectClient();
        }
    }
    */
    
    /** private methods: Handling the different status of the Client */
	private boolean isStateSession() {
		return stateSession;
	}

	private void setStateSession(boolean stateSession) {
		this.stateSession = stateSession;
	}
	
	/*
    private boolean isSendStatus() {
		return sendStatus;
	}

	private void setSendStatus(boolean sendStatus) {
		this.sendStatus = sendStatus;
	}
	*/
	
	
	/** END of status handling */
	
	
	@Override
    public void run() {
        //no inspection InfiniteLoopStatement
        while (isStateSession());
        System.out.println("Program ended");
    }


}
