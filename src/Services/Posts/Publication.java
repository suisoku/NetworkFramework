package Services.Posts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import Core.Session.AccountInfo;
import Services.DataUtilities.FileData;

// on doit utiliser de la sérialisation pour stokcer nos posts vu qu'on utilise le RMI
public class Publication extends AbstractPost implements Serializable, InterfaceInteractible {

	private static final long serialVersionUID = 1L;
	private String titlePost;
	private HashMap<Integer, FileData> imageList = new HashMap<Integer, FileData>() ;
	private HashMap<UUID, Comment> commentList = new HashMap<UUID, Comment>() ;
	
	private AbstractInteractive interactor ;
	// pour traiter chaque type de contenu apart on ajoute un type pour l'identifier
	// 1: pour les textes , 2: pour les images, 3: pour les videos.
	/** private int typePost; interface ? + image */

	/**public Publication(AccountInfo user , String textField) {
		this.userPoster = user;
		this.textField = textField;
		this.datePost = new Date();
	}**/

	
	public Publication(AccountInfo user, String title, String textField) {
		this.userPoster = user;
		this.titlePost = title;
		this.textField = textField;
		this.datePost = new Date();
	}
	
	public void loadComments(HashMap<UUID, Comment> commentList) {
		this.commentList = commentList;
	}


	
	public String getTitlePost() {
		return titlePost;
	}


	public void addImage(FileData image) {
		this.imageList.put(image.hashCode(), image); /** ID il faut le generer */
	}
	
	public void addComment(Comment c) {
		 this.commentList.put(c.getIdPost(), c);
	}

	public void editComment(UUID id , String contenu) {
		 this.commentList.get(id).editTextField(contenu);
	}
	
	public void deleteComment(UUID id) {
		 this.commentList.remove(id);
	}
	

	
	@Override
	public AbstractInteractive getInteractor() {
		return interactor;
	}

	@Override
	public void attachInteractive(AbstractInteractive reaction) {
		this.interactor = reaction;
	}

	public Collection<Comment> getCommentList(){
	  return this.commentList.values();
  }

	// partage ===> idpost et iduser

	// comment ===> idpost et iduser li commenta et commentaire et date.

}
