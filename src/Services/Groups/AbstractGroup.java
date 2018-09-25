package Services.Groups;

import java.util.HashMap;

import Core.Session.AccountInfo;
import JBeeExceptions.JbeeException;

// 
public abstract class AbstractGroup {
	
	protected final String groupName;
	protected HashMap<String , AccountInfo> listUsers;
	
	public AbstractGroup(String gn) {
		this.groupName = gn;
		this.listUsers = new HashMap<String , AccountInfo>();
	}
	
	
	
	public String getGroupName() {
		return groupName;
	}

	public void joinGroup(AccountInfo a) {
		if(!this.listUsers.containsKey(a.getPseudo()) ) {
				this.listUsers.put(a.getPseudo(), a);
		}else throw new JbeeException("User deja dans le groupe");
	}
	
	public void leaveGroup(AccountInfo a) {
		if(this.listUsers.containsKey(a.getPseudo()) ) {
			this.listUsers.remove(a.getPseudo());
		}else throw new JbeeException("User n'existe pas dans le groupe");
	}
	
	public HashMap<String , AccountInfo> getListUsers() {
		return this.listUsers;
	}


	public AccountInfo lookUser(AccountInfo a) {
		return this.listUsers.get(a.getPseudo());
	}
}
