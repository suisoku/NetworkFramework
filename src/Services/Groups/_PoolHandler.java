package Services.Groups;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import Core.Session.AccountInfo;
import Services.DataUtilities.DataMessage;
import Services.Posts.Comment;
import Services.Posts.Publication;

public interface _PoolHandler {

	/**
	 * ------------------ CALLABLE METHODS FOR THE SERVER(REMOTE)
	 * -----------------------
	 */

	void reloadEverything();

	void createChatGroup(String g_name);

	void createPubPool(String g_name);

	void addUserPool(AccountInfo u, String g_name) throws Exception;

	void removeUserPool(AccountInfo u, String g_name) throws Exception;

	void addPost(AccountInfo u, Publication post, String g_name) throws Exception;

	void removePost(AccountInfo u, Publication post, String g_name) throws Exception;

	void addComment(Comment c, UUID idpost, String g_name) throws Exception;

	void removeComment(AccountInfo u, UUID c, UUID idpost, String g_name) throws Exception;

	HashMap<UUID, Publication> getMapPosts(String g_name) throws Exception;

	void sendToGroup(AccountInfo u, DataMessage dt, String g_name) throws RemoteException, SQLException;

	void editPost(UUID idpost, String m) throws SQLException;

	void editComment(UUID idc, String m) throws SQLException;

	ArrayList<String> getChatGroups();

	ArrayList<String> getPostPool();
	
	/**
	 * ------------  INTERNAL UTILITY METHODS (NOT remoted)
	 * ----------------------
	 */

	AbstractGroup findGroup(String g_name);
	boolean lookUser(AccountInfo u, String g_name);


}