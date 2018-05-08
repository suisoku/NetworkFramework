package Services.Posts;

import java.util.ArrayList;

import Core.UserInfo;

public class Liker extends AbstractInteractive {

	private ArrayList<String> likeList;
	private ArrayList<String> dislikeList;
	
	public Liker() {
		this.likeList = new ArrayList<String>();
		this.dislikeList = new ArrayList<String>();
	}
	
	public void like(UserInfo u) {
		this.likeList.add(u.getPseudo());
	}
	
	public void dislike(UserInfo u) {
		this.dislikeList.add(u.getPseudo());
	}
	
	public void unlike(UserInfo u) {
		this.likeList.remove(u.getPseudo());
	}
	
	public void undislike(UserInfo u) {
		this.dislikeList.remove(u.getPseudo());
	} 
	
	public int getCountLike() {
		return this.likeList.size();
	}
	
	public int getCountDislike() {
		return this.dislikeList.size();
	}

	@Override
	public String getFunctionName() {
		// TODO Auto-generated method stub
		return "Allow the liking and the unliking of an attachment";
	}

	@Override
	/** allows filtering by "hot" subjects**/
	public int numberOfAllInteractions() {
		// TODO Auto-generated method stub
		return this.getCountDislike() + this.getCountLike();
	}
}
