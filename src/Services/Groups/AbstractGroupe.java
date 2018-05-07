package Services.Groups;
// 
public abstract class AbstractGroupe implements InterfaceGroupe {
	protected final String NameGroupe;
	//protected final ArrayList<InterfaceUser> Members;
	
	public AbstractGroupe(String nameGroupe) {
		NameGroupe = nameGroupe;
	}

}
