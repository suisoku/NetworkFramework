package Core;

public class Sign {
	
	private String pseudo;
	private String password;
	
	public Sign(String pseudo , String password) {
		this.pseudo =  pseudo;
		this.password =  password;
	}
	
	public Sign(String pseudo) {
		this.pseudo =  pseudo;
	}

	
	
	public String getPseudo() {
		return pseudo;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
