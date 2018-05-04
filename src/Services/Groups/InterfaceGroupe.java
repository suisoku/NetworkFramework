package Services.Groups;

import Core.Session.User.User.InterfaceUser;

public interface InterfaceGroupe {
  
	public void  createGroupe(InterfaceUser Admin,String groupeName,InterfaceUser[] Members );
	public void  deleteGroupe(InterfaceUser Admin, Object idGroupe ); 
	public void  addMember(InterfaceUser I_User, InterfaceGroupe Groupe);
	public void  deleteMember(InterfaceUser I_User, InterfaceGroupe Groupe);
	
}
