package Services.Groups;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

import Services.DataUtilities.DataMessage;
import Services.Posts.Comment;
import Services.Posts.Publication;

public interface _GroupService {

	void createChatGroup(String g_name);

	void createPubPool(String g_name);

	void joinPool(String g_name) throws RemoteException;

	void addPost(Publication post, String g_name) throws RemoteException;

	void removePost(Publication post, String g_name) throws RemoteException;

	void addComment(Comment c, UUID idpost, String g_name) throws RemoteException;

	void removeComment(UUID c, UUID idpost, String g_name) throws RemoteException;

	HashMap<UUID, Publication> getMapPosts(String g_name) throws RemoteException;

	void editPost(UUID idpost, String m) throws RemoteException;

	void editCommment(UUID idc, String m) throws RemoteException;

	void sendToChatGroup(DataMessage dt, String g_name) throws RemoteException;

	void leavePool(String g_name) throws RemoteException;

}