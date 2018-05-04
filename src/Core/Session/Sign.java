package Core.Session;

public class Sign {
	
	private String pseudo;
	private String password;
	
	public Sign(String pseudo , String password) {
		this.pseudo =  pseudo;
		this.password =  password;
	}

	
	
	public String getPseudo() {
		return pseudo;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
