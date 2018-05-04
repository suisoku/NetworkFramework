package Services.Posts;

import java.util.Date;

// on doit utiliser de la s�rialisation pour stokcer nos posts vu qu'on utilise le RMI
public class Post implements java.io.Serializable {

	private int idPost;
	// private Profile profile;
	private String sujet;
	private Date datePost;
	private String contenu; // on verra pour les images et les videos
	// private String status; // public ou priv�

	// pour les commentaires j'essaie �a:
	// stocke ses �l�ments dans une table de hachage � l'aide de Hashdet,
	// cela empeche la duplication.
	// private Set<Comment> comments = new HashSet<Comment>(0);

	
	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Post(String sujet, Date datePost, String contenu) {
		// d'autres attributs � ajouter une fois le truc marche
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

	public String getContenu() {
		return this.contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	@Override
	public String toString() {
		return "[id:" + this.idPost + ",sujet:" + this.sujet + "]";
	}
}
