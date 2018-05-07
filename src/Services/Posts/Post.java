package Services.Posts;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Core.Session.User.User;

// on doit utiliser de la sérialisation pour stokcer nos posts vu qu'on utilise le RMI
public class Post implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPost;
	private User user;
	// pour traiter chaque type de contenu apart on ajoute un type pour l'identifier
	// 1: pour les textes , 2: pour les images, 3: pour les videos.
	private int typePost; 
	private String sujet;
	private Date datePost;
	private Object contenu;

	// pour les commentaires j'essaie ça:
	// stocke ses éléments dans une table de hachage à l'aide de Hashdet,
	// cela empeche la duplication.
	// private Set<Comment> comments = new HashSet<Comment>(0);

	
	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Post(String sujet, User user, Date datePost, Object contenu) {
		// d'autres attributs à ajouter une fois le truc marche
		this.sujet = sujet;
		this.datePost = datePost;
		this.contenu = contenu;
	}

	public Integer getIdPost() {
		return this.idPost;
	}

	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
	}

	public String getSujet() {
		return this.sujet;
	}

	public void setdatePost(Date datePost) {
		this.datePost = datePost;
	}
	
	public Date getdatePost() {
		return this.datePost;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Object getContenu() {
		return this.contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String toString() {
		return "[id:" + this.idPost + ",sujet:" + this.sujet + "]";
	}
	
	
	
	// partage ===> idpost et iduser
	
	// comment ===> idpost et iduser li commenta et commentaire et date.
	
}
