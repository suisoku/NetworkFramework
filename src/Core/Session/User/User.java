package Core.Session.User;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.BD.Predicat;
import Core.BD.Tuple;
import Core.BD._Tuple;
import Core.Client.Client;
import Core.Session.AccountInfo;
import Core.Session.Server._ServerSession;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.DataUtilities.DataStorage;
import Services.Groups.GroupService;
import Services.Groups._GroupService;
import Services.Profile.Profile;

public class User extends Client implements _User{

	private static final long serialVersionUID = 1L;
	
	protected AccountInfo signInDetails;
	protected boolean authentificated = false;
	private _ServerSession myServer;
	private DataStorage messageStorage;
	private _GroupService gs;
	private Profile profil;
	
	public User(_ServerSession server, AccountInfo details) throws RemoteException, SQLException  {
		super(server, details.getPseudo()); 
		
		this.myServer = (_ServerSession) server;
		this.signInDetails = details;
		this.gs = new GroupService(details, server);
	}
	
	@Override
	public AccountInfo getDetails() throws RemoteException {
		return this.signInDetails;
	}

	
	@Override
	public void send(AccountInfo user, DataMessage data) throws RemoteException {
		this.myServer.sendToPool(user, data);
		this.myServer.sendToPool(this.signInDetails, data);
	}
	
	@Override
	public void signOut() throws RemoteException {
		this.disconnectClient();
		this.myServer.disconnectClient(this);
		this.authentificated = false;
	}
	
	@Override
	public boolean isAuthentificated() throws RemoteException{
		return authentificated;
	}
	
	@Override
	public void setAuthentificated(boolean t) throws RemoteException{
		this.authentificated = t;
	}
	
	@Override
	public  void register() throws RemoteException, SQLException {
		this.myServer.register(this.signInDetails);
	}
	
	@Override
	public String getPseudo() throws RemoteException {
		return this.signInDetails.getPseudo();
	}
	
	@Override
	public ArrayList<DataMessage> getMessageStack() throws RemoteException{
		if(this.messageStorage == null ) throw new JbeeException("Reloading Data failed or empty");
		else return (ArrayList<DataMessage>)this.messageStorage;
	}

	@Override
	public void reloadData(ArrayList<DataMessage> bdDataMessages) throws RemoteException {
		this.messageStorage = new DataStorage(this.getPseudo(), bdDataMessages);
		
	}
	
	
	/** Client side related tasks :   --------------- **/
	// get profile
	
	
	public void loadProfile() {
		try {
			this.profil = this.myServer.getProfile(this.signInDetails);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void createProfile(Profile p) {
		this.profil = p;
		try {
			this.myServer.createProfile(p);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editProfile(Profile p) {
		this.profil = p;
		try {
			this.myServer.editProfile(p);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Profile getProfile() {
		return this.profil;
	}
	
	// Group Service
	public _GroupService groupService()  {
		return this.gs;
	}
	
	public ArrayList<ArrayList<Object>> searchInBD(String table , String keyword) {
		try {
			return this.myServer.searchData(table, keyword);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}
	
	public ArrayList<String> getAllChatGroups() throws RemoteException{
		return this.myServer.getAllChatGroups();
	}
	
	public ArrayList<String> getAllPostpools() throws RemoteException{
		return this.myServer.getPostPools();
	}

	public ArrayList<AccountInfo> getAllUsers() throws RemoteException{
		return this.myServer.getAllUsers();
	}
	
	// ENGINE BD RELATED TASKS
	public void addData( String table_Name, _Tuple data) throws SQLException, RemoteException{
		 this.myServer.addData(table_Name, data);
	}
	
	public void  deleteData(String table_Name, Predicat... predicats) throws SQLException, RemoteException{
		this.deleteData(table_Name, predicats);
	}
	
	public ArrayList<Tuple> getData(String table_Name, Predicat... predicats ) throws SQLException, RemoteException{
		return this.myServer.getData(table_Name, predicats);
	}
	
	public void updateData(String table_Name, Predicat predicat) throws SQLException, RemoteException{
		this.myServer.updateData(table_Name, predicat);
	}
}

