
package Services.Profile;

import java.io.Serializable;
import java.util.ArrayList;

import Core.Session.AccountInfo;
import Services.DataUtilities.FileData;
import Services.Posts.Publication;

public class Profile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountInfo owner;
	private ArrayList<Publication> pubList;
	private ArrayList<Object> listElements;
	private FileData profilePic;
	
	
	public Profile(AccountInfo owner, ArrayList<Object> listElements) {
		this.owner = owner;
		this.listElements = listElements;

	}
	
	public Profile(AccountInfo owner,  ArrayList<Object> listElements, ArrayList<Publication> pubList) {
		this.owner = owner;
		this.pubList = pubList;
		this.listElements = listElements;
	}

	public FileData getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(FileData profilePic) {
		this.profilePic = profilePic;
	}

	public ArrayList<Publication> getPubList() {
		return pubList;
	}

	public void loadPubList(ArrayList<Publication> pubList) {
		this.pubList = pubList;
	}

	public AccountInfo getOwner() {
		return owner;
	}

	public ArrayList<Object> getListElements() {
		return listElements;
	}

	
	

	
}