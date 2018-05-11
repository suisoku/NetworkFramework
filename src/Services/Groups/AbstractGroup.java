package Services.Groups;

import java.util.HashMap;

import Core.Session.AccountInfo;

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
		this.listUsers.put(a.getPseudo(), a);
	}
	
	public void leaveGroup(AccountInfo a) {
		this.listUsers.remove(a.getPseudo());
	}

}
