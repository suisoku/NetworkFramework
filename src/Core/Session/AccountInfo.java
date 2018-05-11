package Core.Session;

import java.io.Serializable;

import Core.BD.Predicat;

public class AccountInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private String password;
	private Predicat p_pseudo;
	private Predicat p_password;
	
	public AccountInfo(Predicat p_pseudo, Predicat p_password) {
		this.p_pseudo = p_pseudo;
		this.p_password = p_password;
		this.pseudo =  (String) p_pseudo.value;
		this.password =  (String) p_password.value;
	}
	
	public AccountInfo(Predicat p_pseudo) {
		this.p_pseudo = p_pseudo;
		this.pseudo =  (String) p_pseudo.value;
	}

	
	
	public String getPseudo() {
		return pseudo;
	}

	public String getPassword() {
		return password;
	}

	public Predicat getP_pseudo() {
		return p_pseudo;
	}

	public Predicat getP_password() {
		return p_password;
	}
	
	
	
}
