package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import Core.BD.InteractionBD;
import Core.BD.Predicat;
import Core.BD.Tuple;
import Core.BD._Tuple;
import Core.Client._Client;
import Core.Serveur.Server;
import Core.Session.AccountInfo;
import Core.Session.User._User;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.Groups.ChatGroup;
import Services.Groups.PoolHandler;
import Services.Groups._PoolHandler;
import Services.Posts.Comment;
import Services.Posts.Publication;
import Services.Profile.Profile;

public class ServerSession extends Server implements _ServerSession{
	
	private static final long serialVersionUID = 1L;
	private InteractionBD bd ;
	
	private HashMap<String, _User> users;
	private _PoolHandler poolhandler;
	
	
	public ServerSession(InteractionBD bd_con) throws RemoteException {
		super();
		users = new HashMap<String, _User>();
		
		this.bd = bd_con;
		this.poolhandler = new PoolHandler(this.bd);
	}
	
	
	@Override
	public boolean authentication(_User user) throws RemoteException {
			try {
				return bd.identify(user.getDetails());
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}	
	}
	
	@Override	
	public _User lookupUser(AccountInfo details) throws RemoteException {
		for(String key: users.keySet()) {
			if(key.equals(details.getPseudo())){
				return users.get(key);
			}
		}
		return null;
	}
	
	@Override
	public boolean register(AccountInfo details) throws RemoteException, SQLException {
		if(!bd.identify(details)) {
			bd.addAccount(details);
			return true;
		}
		else return false;
		
	}
	

	
	@Override
	public synchronized void sendToPool(AccountInfo user_details , DataMessage data) throws RemoteException {			
		try {
			if(bd.lookUser(user_details)) {
				
				_User right_user = this.lookupUser(user_details);
				if(right_user != null) {	right_user.update(data);	}
				
				bd.storeIntoBD(user_details.getPseudo(), data);
			}
			else  throw new JbeeException("user not found in DB");
		}
		catch (SQLException | ParseException e) {e.printStackTrace();}
	}	
	
	
 
    /** synchronized allow safe resources accessing  **/
	@Override
    public synchronized void connectClient(_Client u) throws RemoteException {
		
		_User user =  (_User)u;
		if(authentication(user)) {
			
			this.users.put(user.getPseudo(), user);
		
			try {
				user.reloadData(bd.loadDataMessages(user.getDetails()));
			} catch (SQLException | ParseException e) { 	e.printStackTrace();}
		
			user.setAuthentificated(true);
		}
		else user.setAuthentificated(false);
	}
	
	@Override
	public synchronized void disconnectClient(_Client u) throws RemoteException {
		 this.users.remove(u.getIdClient());
	}
	
	@Override 
	public synchronized int getNbClients() throws RemoteException {
		return this.users.size();
	}


	
	
	
	// POOL HANDLER RELATED TASKS
	
	@Override
	public synchronized boolean poolNameAvailable(String g_name) throws RemoteException {
		if(this.poolhandler.findGroup(g_name) == null)return true;
		else return false;
	}
	
	@Override
	public  synchronized void createPubPool(String g_name) throws RemoteException{
		this.poolhandler.createPubPool(g_name);
	}
	
	@Override
	public synchronized void createChatGroup(String g_name) throws RemoteException {
		this.poolhandler.createChatGroup(g_name);
	}
	
	@Override
	public synchronized void joinPool(AccountInfo u , String g_name) throws RemoteException {
		if(!this.poolhandler.lookUser(u, g_name)){
			try {
				this.poolhandler.addUserPool(u, g_name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else throw new JbeeException("Already in the group");

	}
	
	@Override
	public synchronized void leavePool(AccountInfo u , String g_name) throws RemoteException {
		try {
			this.poolhandler.removeUserPool(u, g_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addPost(AccountInfo u , Publication post , String g_name) throws RemoteException {
		System.out.println(post.getTitlePost());
		if(this.poolhandler.lookUser(u, g_name)) {
			try {
				System.out.println(post.getTitlePost() +"dans lookuser");
				this.poolhandler.addPost( u ,  post ,  g_name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else throw new JbeeException("N'a pas le droit , car n'est pas dans le groupe");
	}
	
	@Override
	public void removePost(AccountInfo u , Publication post , String g_name) throws RemoteException {
		try {
			this.poolhandler.removePost( u ,  post ,  g_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addComment(AccountInfo u , Comment c , UUID idpost, String g_name) throws RemoteException {
		if(this.poolhandler.lookUser(u, g_name)) {
			try {
				this.poolhandler.addComment(c ,  idpost,  g_name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else throw new JbeeException("N'a pas le droit , car n'est pas dans le groupe");
	}
	
	@Override
	public void removeComment(AccountInfo u , UUID c , UUID idpost, String g_name) throws RemoteException {
		try {
			this.poolhandler.removeComment( u ,  c ,  idpost,  g_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public HashMap<UUID, Publication> getMapPosts(String g_name) throws RemoteException {
		try {
			return this.poolhandler.getMapPosts(g_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void sendToChatGroup(AccountInfo u , DataMessage dt , String g_name) throws RemoteException {
		if(this.poolhandler.lookUser(u, g_name)) {
			try {
				this.poolhandler.sendToGroup(u, dt, g_name);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			for(AccountInfo us : ((ChatGroup)this.poolhandler.findGroup(g_name)).getListUsers().values()) {
				this.sendToPool(us, dt);
			}
		}
	}
	
	@Override
	public void editPost(UUID idpost , String m) throws RemoteException  {
		try {
			this.poolhandler.editPost(idpost, m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void editComment(UUID idc , String m)throws RemoteException  {
		try {
			this.poolhandler.editComment(idc, m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// get profile
	@Override
	public Profile getProfile(AccountInfo a) throws RemoteException, SQLException{
		return this.bd.getProfile(a);
	}
	@Override
	public void createProfile(Profile p) throws RemoteException, SQLException {
		this.bd.addProfile(p);
	}
	@Override
	public void editProfile(Profile p) throws RemoteException , SQLException {
		this.bd.editProfile(p);
	}
	
	@Override
	public  ArrayList<ArrayList<Object>> searchData(String table , String keyword) throws RemoteException, SQLException {
		ArrayList<ArrayList<Object>> biglist =  new ArrayList<ArrayList<Object>>();
		ArrayList<Object> temp = new ArrayList<Object>();
		ArrayList<Tuple> at = this.bd.getEngine().searchData(table, keyword);
		
		for(int i = 0  ; i < at.size() ; i++) {
			for(Object o : at.get(i))temp.add(o);
			biglist.add(temp);
		}
			
		return biglist;
	}
	

	// GUI SPECIAL TASKS
	@Override
	public ArrayList<AccountInfo> getAllUsers() {
		try {
			return this.bd.getAllUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<String> getAllChatGroups() {
		return this.poolhandler.getChatGroups();
	}
	
	@Override
	public ArrayList<String> getPostPools() throws RemoteException{
		return this.poolhandler.getPostPool();
	}
	
	// GUI ENGINE NEEDS FOR MORE ADVANCED :
	@Override
	public void addData( String table_Name, _Tuple data) throws SQLException, RemoteException{
		 this.bd.getEngine().addData(table_Name, data);
	}
	@Override
	public void  deleteData(String table_Name, Predicat... predicats) throws SQLException, RemoteException{
		this.bd.getEngine().deleteData(table_Name, predicats);
	}
	@Override
	public ArrayList<Tuple> getData(String table_Name, Predicat... predicats ) throws SQLException, RemoteException{
		return this.bd.getEngine().getData(table_Name, predicats);
	}
	@Override
	public void updateData(String table_Name, Predicat predicat) throws SQLException, RemoteException{
		this.bd.getEngine().updateData(table_Name, predicat);
	}
	
	
	
	// Server side utilities
	public _PoolHandler poolHandler() {
		return this.poolhandler;
	}
	
	
}
