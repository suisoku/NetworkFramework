package Services.Groups;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

import Core.Session.AccountInfo;
import Core.Session.Server._ServerSession;
import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.Posts.Comment;
import Services.Posts.Publication;

public class GroupService implements Serializable, _GroupService {


	private static final long serialVersionUID = 1L;
	private _ServerSession serv;
	private AccountInfo me ; 
	public GroupService(AccountInfo me , _ServerSession s) {
		this.serv = s;
		this.me = me;
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#createChatGroup(java.lang.String)
	 */
	@Override
	public void createChatGroup(String g_name) {
		try {
			if(this.serv.poolNameAvailable(g_name)) {
				this.serv.createChatGroup(g_name);
			}
			else throw new JbeeException("Pool already exists");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#createPubPool(java.lang.String)
	 */
	@Override
	public void createPubPool(String g_name) {
		try {
			if(this.serv.poolNameAvailable(g_name)) {
				this.serv.createPubPool(g_name);
			}
			else throw new JbeeException("Pool already exists");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#joinPool(java.lang.String)
	 */
	@Override
	public void joinPool(String g_name) throws RemoteException {
		this.serv.joinPool(me,  g_name);
	}
	
	@Override
	public void leavePool(String g_name) throws RemoteException {
		this.serv.leavePool(me,  g_name);
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#addPost(Services.Posts.Publication, java.lang.String)
	 */
	@Override
	public void addPost(Publication post, String g_name) throws RemoteException {
		this.serv.addPost(me,  post,  g_name);
	}
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#removePost(Services.Posts.Publication, java.lang.String)
	 */
	@Override
	public void removePost(Publication post, String g_name) throws RemoteException {
		this.serv.removePost(me ,  post,  g_name);
	}
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#addComment(Services.Posts.Comment, java.util.UUID, java.lang.String)
	 */
	@Override
	public void addComment(Comment c, UUID idpost, String g_name) throws RemoteException {
		this.serv.addComment(me ,  c,  idpost,  g_name) ;
	}
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#removeComment(java.util.UUID, java.util.UUID, java.lang.String)
	 */
	@Override
	public void removeComment(UUID c, UUID idpost, String g_name) throws RemoteException{
		this.serv.removeComment(me ,  c,  idpost,  g_name);
	}
	
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#getMapPosts(java.lang.String)
	 */
	@Override
	public HashMap<UUID, Publication> getMapPosts(String g_name) throws RemoteException {
		return this.serv.getMapPosts(g_name);
	}
	
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#editPost(java.util.UUID, java.lang.String)
	 */
	@Override
	public void editPost(UUID idpost, String m) throws RemoteException {
		this.serv.editPost(idpost, m);
	}
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#editCommment(java.util.UUID, java.lang.String)
	 */
	@Override
	public void editCommment(UUID idc, String m) throws RemoteException {
		this.serv.editComment(idc, m);
	}
	/* (non-Javadoc)
	 * @see Services.Groups._GroupService#sendToChatGroup(Services.DataUtilities.DataMessage, java.lang.String)
	 */
	@Override
	public void sendToChatGroup(DataMessage dt, String g_name) throws RemoteException {
		this.serv.sendToChatGroup(me, dt, g_name);
	}
	
}
