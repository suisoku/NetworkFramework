package Services.Posts;

import java.io.Serializable;
import java.util.UUID;

import Core.Session.AccountInfo;

public class Comment extends AbstractPost implements Serializable {



	private static final long serialVersionUID = 1L;

	public Comment(AccountInfo user , String contenu) {
		   super(contenu, user , UUID.randomUUID());
    }
	
	public Comment(AccountInfo user , String contenu , UUID idbd) {
		   super(contenu, user , idbd);
	}
	 

}
