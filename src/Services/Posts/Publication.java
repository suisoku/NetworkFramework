package Services.Posts;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import Core.Session.AccountInfo;
import Services.DataUtilities.FileData;

// on doit utiliser de la sérialisation pour stokcer nos posts vu qu'on utilise le RMI
public class Publication extends AbstractPost implements Serializable, _Interactible {

	private static final long serialVersionUID = 1L;
	private String titlePost;
	private HashMap<Integer, FileData> imageList = new HashMap<Integer, FileData>() ;
	private HashMap<UUID, Comment> commentMap ;
	
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
		super(textField,user, UUID.randomUUID());
		this.titlePost = title;
	}
	
	public Publication(AccountInfo user, String title, String textField , UUID idbd) {
		super(textField,user, idbd);
		this.titlePost = title;
	}
	
	public void loadComments(HashMap<UUID, Comment> commentList) {
		this.commentMap = commentList;
	}


	
	public String getTitlePost() {
		return titlePost;
	}


	public void addImage(FileData image) {
		this.imageList.put(image.hashCode(), image); /** ID il faut le generer */
	}
	
	public void addComment(Comment c) {
		 this.commentMap.put(c.getIdPost(), c);
	}

	public void editComment(UUID id , String contenu) {
		 this.commentMap.get(id).editTextField(contenu);
	}
	
	public void deleteComment(UUID id) {
		 this.commentMap.remove(id);
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
	  return this.commentMap.values();
  }

	// partage ===> idpost et iduser

	// comment ===> idpost et iduser li commenta et commentaire et date.

}
