package Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import AppDemo.Client.ClientGUI;
import Core.Client.Client;
import Core.Serveur.Server;

public class noureddine_test_clientServiceGUI {

    /**
     *  ****************** MAIN METHOD ***********************
     */
    
    public static void main(String[] args) throws MalformedURLException,RemoteException, NotBoundException {
        
    	/** declaring stuff  */
        
        String chatServerURL = "rmi://localhost/RMIChatServer";
		Server chatServer = (Server) Naming.lookup(chatServerURL);
		
        
		/** threading stuff */

        String name = ClientGUI.getUserName(); // Input User
        Client clientService = new Client(chatServer, name); // Crafting clientService
        
        Thread clientThread = new Thread(clientService); // running the thread
        clientThread.start();
        
        new ClientGUI(clientService); // Instantiate GUI with clientService
        
        ClientGUI.welcomeUser(name); // Hello
        
    }
}
