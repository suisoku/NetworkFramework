package Services.Posts;

import java.util.HashSet;
import java.util.Set;

// on doit utiliser de la sérialisation pour stokcer nos posts vu qu'on utilise le RMI
public class Post implements java.io.Serializable {

	private int idPost;
	// private Profile profile;
	private String sujet;
	private String contenu; // on verra pour les images et les videos
	// private Date datePost;
	// private String status; // public ou privé
	// private Set<Comment> comments = new HashSet<Comment>(0);

	public Post(String sujet, String contenu) {
		// d'autres attributs à ajouter une fois le truc marche
		this.sujet = sujet;
		this.contenu = contenu;
	}
	
	
}
