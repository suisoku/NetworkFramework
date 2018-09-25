package Services.Groups;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import Core.BD.InteractionBD;
import Core.Session.AccountInfo;
import Services.DataUtilities.DataMessage;
import Services.Posts.Comment;
import Services.Posts.Publication;

public class PoolHandler implements _PoolHandler {

	private HashMap<String, ChatGroup> chatMap;
	private HashMap<String, PublicationPool> postMap;

	private InteractionBD bd;

	public PoolHandler(InteractionBD intbd) {
		chatMap = new HashMap<String, ChatGroup>();
		postMap = new HashMap<String, PublicationPool>();

		this.bd = intbd;
	}

	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#reloadEverything()
	 */

	@Override
	public void reloadEverything() {
		this.reloadGroups(); // Reload groups , reload users , and redistribute them in the given hashmaps
		this.reloadPosts();
		this.reloadMessages(); // reload message stack of chatGroup
		this.reloadComments(); // reload comments for each post of pubpools

	}

	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#createChatGroup(java.lang.String)
	 */
	@Override
	public void createChatGroup(String g_name) {
		this.chatMap.put(g_name, new ChatGroup(g_name));
		try {
			this.bd.addPool(g_name, "CHAT");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#createPubPool(java.lang.String)
	 */
	@Override
	public void createPubPool(String g_name) {
		this.postMap.put(g_name, new PublicationPool(g_name));
		try {
			this.bd.addPool(g_name, "POST");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#addUserPool(Core.Session.AccountInfo, java.lang.String)
	 */
	@Override
	public void addUserPool(AccountInfo u , String g_name) throws Exception {
		AbstractGroup ag = this.findGroup(g_name);
		
		if(ag != null) {
			ag.joinGroup(u);
			this.bd.addUserPool(u, g_name);
		}else throw new Exception("Error group not found");
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#removeUserPool(Core.Session.AccountInfo, java.lang.String)
	 */
	@Override
	public void removeUserPool(AccountInfo u , String g_name) throws Exception {
		AbstractGroup ag = this.findGroup(g_name);
		
		if(ag != null) {
			ag.leaveGroup(u);
			this.bd.removeUserPool(u, g_name);
		}else throw new Exception("Error group not found");
	}

	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#addPost(Core.Session.AccountInfo, Services.Posts.Publication, java.lang.String)
	 */
	@Override
	public void addPost(AccountInfo u , Publication post , String g_name) throws Exception {
		this.postMap.get(g_name).addPost(post);		
		this.bd.addPost(post, g_name);
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#removePost(Core.Session.AccountInfo, Services.Posts.Publication, java.lang.String)
	 */
	@Override
	public void removePost(AccountInfo u , Publication post , String g_name) throws Exception {
		this.postMap.get(g_name).deletePost(post);
		this.bd.removePost(post.getIdPost());
	}

	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#addComment(Core.Session.AccountInfo, Services.Posts.Comment, java.util.UUID, java.lang.String)
	 */
	@Override
	public void addComment(Comment c , UUID idpost, String g_name) throws Exception {
	//	System.out.println(this.postMap.get(g_name).getMapPosts().get(key));
		this.postMap.get(g_name).getMapPosts().get(idpost).addComment(c);
		this.bd.addComment(c, idpost);
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#removeComment(Core.Session.AccountInfo, java.util.UUID, java.util.UUID, java.lang.String)
	 */
	@Override
	public void removeComment(AccountInfo u , UUID c , UUID idpost, String g_name) throws Exception {
		this.postMap.get(g_name).getMapPosts().get(idpost).deleteComment(c);
		this.bd.removeComment(c);
	}
	
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#getMapPosts(java.lang.String)
	 */
	@Override
	public HashMap<UUID, Publication> getMapPosts(String g_name) throws Exception {
		return this.postMap.get(g_name).getMapPosts();
	}
	
	
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#sendToGroup(Core.Session.AccountInfo, Services.DataUtilities.DataMessage, java.lang.String)
	 */
	@Override
	public void sendToGroup(AccountInfo u , DataMessage dt , String g_name) throws RemoteException, SQLException {
		if(this.chatMap.get(g_name).listUsers.containsKey(u.getPseudo())) {
			this.chatMap.get(g_name).update(dt);
			this.bd.addmessGroup(dt, g_name);
		}
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#editPost(java.util.UUID, java.lang.String)
	 */
	@Override
	public void editPost(UUID idpost , String m) throws SQLException {
		this.bd.editPost(idpost, m);
	}
	/* (non-Javadoc)
	 * @see Services.Groups._PoolHandler#editComment(java.util.UUID, java.lang.String)
	 */
	@Override
	public void editComment(UUID idc , String m) throws SQLException {
		this.bd.editComment(idc, m);;
	}
	
	
	
	/** INTERNAL METHODS (NO REMOTED) **/
	@Override
	public AbstractGroup findGroup(String g_name) {
		if (this.chatMap.containsKey(g_name))
			return this.chatMap.get(g_name);
		else if (this.postMap.containsKey(g_name))
			return this.postMap.get(g_name);
		else
			return null;
	}
	
	
	
	@Override
	public boolean lookUser(AccountInfo u, String g_name) {
		if(this.chatMap.get(g_name) != null) return this.chatMap.get(g_name).lookUser(u) != null;
		else if (this.postMap.get(g_name) != null)return this.postMap.get(g_name).lookUser(u) != null;
		return false;
	}
	
	@Override
	public ArrayList<String> getChatGroups(){
		return new ArrayList<String>(this.chatMap.keySet());
	}
	
	@Override
	public ArrayList<String> getPostPool(){
		return new ArrayList<String>(this.postMap.keySet());
	}
	
	/**
	 * ------------ PRIVATE METHODS ALLOWS RELOADING OF GIVEN POOLS
	 * ----------------------
	 */

	private void reloadGroups() {

		HashMap<String, AbstractGroup> groups = null;
		try {
			groups = this.bd.loadGroups();
		} catch (SQLException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HashMap<String, AbstractGroup> groupsWithUsers = null;
		groupsWithUsers = reloadUsers(groups);

		groupsWithUsers.forEach((k, v) -> {
			if (v instanceof ChatGroup)
				this.chatMap.put(k, (ChatGroup) v);
			else if (v instanceof PublicationPool)
				this.postMap.put(k, (PublicationPool) v);
		});
	}

	private HashMap<String, AbstractGroup> reloadUsers(HashMap<String, AbstractGroup> g_map) {
		g_map.forEach((k, v) -> {
			try {
				for (AccountInfo a : this.bd.loadUsersGroup(k))
					v.joinGroup(a);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		});

		return g_map;
	}

	private void reloadMessages() {
		this.chatMap.forEach((k, v) -> {
			try {
				v.initializeStorage(this.bd.loadGroupMess(k));
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		});
	}

	private void reloadPosts() {
		this.postMap.forEach((k, v) -> {
			try {
				v.loadPool(this.bd.loadPostsPool(k));
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		});
	}

	private void reloadComments() {
		this.postMap.forEach((k, pool) -> {

			pool.getMapPosts().forEach((keyPost, post) -> {
				try {
					post.loadComments(this.bd.loadComments(keyPost));
				} catch (SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
	}

}
