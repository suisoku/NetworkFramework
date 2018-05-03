package Services.Groups;

public abstract class Members implements InterfaceGroupe {
    protected  String groupeName;
    protected  Object[] Members;
    
    
	
	
	public Members(String groupeName, Object[] members) {
		this.groupeName = groupeName;
		this.Members = members;
	}

	@Override
	public void addMember() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMember() {
		// TODO Auto-generated method stub

	}

}
