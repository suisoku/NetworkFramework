package Core.Session.User;
import java.rmi.RemoteException;
import java.sql.SQLException;
import Core.Sign;
import Core.Client.Client;
import Core.Session.Server.InterfaceServerSession;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.Data_message;

public class User extends Client implements InterfaceUser{

	private static final long serialVersionUID = 1L;
	
	protected Sign signInDetails;
	protected boolean authentificated;
	private InterfaceServerSession myServer;
		

	public User(InterfaceServerSession server, Sign details) throws RemoteException, SQLException  {
		super(server, details.getPseudo()); 
		
		this.myServer = (InterfaceServerSession) server;
		this.signInDetails = details;
		
		if(!this.myServer.authentication(this)) {
			throw new JbeeException("Authentification failed");
		}
		else this.authentificated = true;
	}
	
	@Override
	public Sign getDetails() throws RemoteException {
		return this.signInDetails;
	}

	@Override 
	public void send(Iterable<User> pool, Data_message data) throws RemoteException {
		this.myServer.sendToPool(pool, data);
	}
	
	@Override
	public void send(Sign user, Data_message data) throws RemoteException {
		this.myServer.sendToPool(user, data);
	}
	
	@Override
	public void signOut() throws RemoteException {
		this.disconnectClient();
		this.authentificated = false;
	}
	
	@Override
	public boolean isAuthentificated() throws RemoteException{
		return authentificated;
	}

//	@Override
//	public void joinPool(InterfaceGroupe pool)  throws RemoteException{} 

//	@Override
//	public void leavePool(InterfaceGroupe pool) throws RemoteException {}
	

	/*public static void register(InterfaceServerSession server , Sign details) throws RemoteException, SQLException {
		server.register(details);
	}*/
	
	@Override
	public String getPseudo() throws RemoteException {
		return this.signInDetails.getPseudo();
	}
	
}
