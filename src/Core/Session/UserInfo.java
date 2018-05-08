package Core;

import java.io.Serializable;

public class UserInfo implements Serializable {
	
	private String pseudo;
	private String password;
	
	public UserInfo(String pseudo , String password) {
		this.pseudo =  pseudo;
		this.password =  password;
	}
	
	public UserInfo(String pseudo) {
		this.pseudo =  pseudo;
	}

	
	
	public String getPseudo() {
		return pseudo;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
