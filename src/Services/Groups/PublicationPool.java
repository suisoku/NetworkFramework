package Services.Groups;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import Services.Posts.Publication;

public class PublicationPool extends AbstractGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<UUID, Publication> posts;
	
	public PublicationPool(String nameGroupe) {
		super(nameGroupe);
		this.posts = new HashMap<UUID, Publication>();
	}
	
	public void addPost(Publication p) {
		posts.put(p.getIdPost(), p);
	}

	public void deletePost(Publication p) {
		posts.remove(p.getIdPost());
	}
	
	public void loadPool(HashMap<UUID, Publication> p){
		this.posts = p;
	}
	public HashMap<UUID, Publication> getMapPosts(){
		return this.posts;
	}
	
}
