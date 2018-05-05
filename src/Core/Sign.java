package Core;

import java.io.Serializable;

public class Sign implements Serializable {
	
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
