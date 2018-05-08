package Test;

import Core.UserInfo;
import Services.Posts.Comment;
import Services.Posts.Liker;
import Services.Posts.Publication;

public class Houssem_test_publications {
	 public static void main(String[] args) {
		 
		 UserInfo user1 = new UserInfo("Houssaim");
		 Publication post1 =  new Publication(user1 ,"Le caffe" , "le caffe c bon pour la sante");
		 
		 Comment com = new Comment(user1, "jjjjjjaaaaajjja");
		 post1.addComment(com);
		 
		 /** another exemple attach new InteractiveRating */
		 post1.attachInteractive(new Liker());
		 
		 ((Liker) post1.getInteractor()).like(user1);
		 // GUI
		 System.out.println("Titre du post " + post1.getTitlePost());
		 System.out.println("Posteur " + post1.getUser().getPseudo());
		 System.out.println("Field " + post1.getTextField());
		 System.out.println("Date " + post1.getDatePost());
		 System.out.println("--------------- Commentaires ---------------");
		 
		 for(Comment c : post1.getCommentList()) {
			 System.out.println("idCom   : " + c.getIdPost());
			 System.out.println("Commentaire   : " + c.getTextField());
		 }
		 
		 
	 }
	 
}
