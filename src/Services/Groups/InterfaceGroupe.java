package Services.Groups;
//
import java.sql.SQLException;

import Services.Profile.InterfaceTuple;
import Services.Profile.Predicat;

public interface InterfaceGroupe {
  
	public void  deleteGroup(String table_Name, Predicat predicate ) throws SQLException; 
	public void  addMember(String table_Name,InterfaceTuple tuple ) throws SQLException;
	public void  deleteMember(String table_Name, Predicat predicate) throws SQLException;
	
}
