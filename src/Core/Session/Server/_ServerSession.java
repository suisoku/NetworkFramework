package Core.Session.Server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import Core.BD.Predicat;
import Core.BD.Tuple;
import Core.BD._Tuple;
import Core.Serveur._Server;
import Core.Session.AccountInfo;
import Core.Session.User._User;
import Services.DataUtilities.DataMessage;
import Services.Posts.Comment;
import Services.Posts.Publication;
import Services.Profile.Profile;

public interface _ServerSession extends _Server{

	// Core tasks
	public boolean authentication(_User user) throws RemoteException;
	public boolean register(AccountInfo details) throws RemoteException, SQLException;
	public void sendToPool(AccountInfo user , DataMessage data) throws RemoteException ;
	public _User lookupUser(AccountInfo details) throws RemoteException;
	
	
	// Group service oriented tasks 
	public boolean poolNameAvailable(String g_name) throws RemoteException;
	public void createPubPool(String g_name) throws RemoteException;
	public void createChatGroup(String g_name) throws RemoteException;
	public void joinPool(AccountInfo u, String g_name) throws RemoteException;
	public void leavePool(AccountInfo u, String g_name) throws RemoteException;
	public void addPost(AccountInfo u, Publication post, String g_name) throws RemoteException;
	public void removePost(AccountInfo u, Publication post, String g_name) throws RemoteException;
	public void addComment(AccountInfo u, Comment c, UUID idpost, String g_name) throws RemoteException;
	public void removeComment(AccountInfo u, UUID c, UUID idpost, String g_name) throws RemoteException;
	public  HashMap<UUID, Publication>  getMapPosts(String g_name) throws RemoteException;
	public void editPost(UUID idpost, String m) throws RemoteException;
	public void editComment(UUID idc, String m) throws RemoteException;
	public void sendToChatGroup(AccountInfo u, DataMessage dt, String g_name) throws RemoteException;
	
	// Profile service tasks
	public Profile getProfile(AccountInfo a) throws RemoteException, SQLException;
	public void createProfile(Profile p) throws RemoteException, SQLException;
	public void editProfile(Profile p) throws RemoteException, SQLException;
	
	//Search data task

	public ArrayList<AccountInfo> getAllUsers() throws RemoteException;
	public ArrayList<String> getAllChatGroups() throws RemoteException;
	public ArrayList<String> getPostPools() throws RemoteException;
	
	// Engine BD calls Task
	public void addData(String table_Name, _Tuple data) throws SQLException, RemoteException;
	public  ArrayList<ArrayList<Object>> searchData(String table, String keyword) throws RemoteException, SQLException;
	public void deleteData(String table_Name, Predicat... predicats) throws SQLException, RemoteException;
	public ArrayList<Tuple> getData(String table_Name, Predicat... predicats) throws SQLException, RemoteException;
	public void updateData(String table_Name, Predicat predicat) throws SQLException, RemoteException;

	
}
