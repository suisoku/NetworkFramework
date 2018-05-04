package Services.Posts;

import java.io.Serializable;

public class Publication implements Serializable {

	private PostInterface postInterface;
	private Post post;

	public void init() {
		post = new Post();
	}

	public Publication() {
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String datePostToString(java.util.Date date2) {
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
	
    public void Poster() {
        
     // ici je dois trouver un moyen comment intéger l'identité du User qui va poster
        
        post.setdatePost(new java.util.Date());
        postInterface.creer(post);
        System.out.println(post);

    }
}
