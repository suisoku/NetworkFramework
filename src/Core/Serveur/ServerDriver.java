package Core.Serveur;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

class ServerDriver {

	public static void main (String [] args) throws RemoteException, MalformedURLException {
        try {
        	//System.setProperty("java.security.policy","src/java.policy");
        	//System.setSecurityManager(new SecurityManager());
        	
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
        new ServerGUI();
        Naming.rebind("RMIChatServer",new ChatServer());
    }
}
