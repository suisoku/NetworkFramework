package Services.Posts;

import java.io.Serializable;
import java.util.Date;

import Core.UserInfo;

public class Comment extends AbstractPost implements Serializable {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Comment(UserInfo user , String contenu) {
		   this.userPoster = user;
	       this.textField = contenu;
	       this.datePost = new Date();
    }
	 

	public int getIdComment() {
		return this.idPost;
	}

	public void setContenu(String contenu) {
		this.textField = contenu;
	}

	public Date getDateComment() {
		return this.datePost;
	}

}
