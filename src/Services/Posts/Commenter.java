package Services.Posts;

import java.io.Serializable;
import java.util.List;

public class Commenter implements Serializable {

	private PostInterface postInterface;
	private Post post;

	private CommentInterface commentInterface;
	private List<Comment> comments;

	private Comment commentaire;
	
	
    public void commenter() {

        commentaire.setDateComment(new java.util.Date());
        Post p = new Post();

        p.setIdPost(1);
        commentaire.setPost(p);
       // CommentInterface.creer(commentaire);  avec l'ajout de static dans l'interface Comment à la méthode creer
        commentInterface.creer(commentaire);
        commentaire = new Comment();

    }

	public String dateCommentToString(java.util.Date date2) {
		java.util.Date date1 = new java.util.Date();
		long time = ((date1.getTime() - date2.getTime()) / 60000);
		if (time < 60) {
			return time + " min";
		} else if (time >= 60 && time < 1440) {
			return (time / 60) + " h";
		} else {
			return date2.toString();
		}
	}
}
