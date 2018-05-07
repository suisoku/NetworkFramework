package Services.Posts;

import java.util.Date;

public class Comment implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idComment ;
	// private Profile profile;
	private Post post;
	private String contenu;
	private Date dateComment;
	
	
	 public Comment(Post post, String contenu, Date dateComment) {
	       this.post = post;
	       this.contenu = contenu;
	       this.dateComment = dateComment;
	    }
	 
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public int getIdComment() {
		return idComment;
	}

	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDateComment() {
		return dateComment;
	}

	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}
}
