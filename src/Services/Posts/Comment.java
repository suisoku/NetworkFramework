package Services.Posts;

import java.io.Serializable;
import java.util.Date;

import Core.Session.AccountInfo;

public class Comment extends AbstractPost implements Serializable {



	private static final long serialVersionUID = 1L;

	public Comment(AccountInfo user , String contenu) {
		   this.userPoster = user;
	       this.textField = contenu;
	       this.datePost = new Date();
    }
	 

}
