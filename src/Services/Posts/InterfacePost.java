package Services.Posts;

import java.sql.SQLException;

import Services.Profile.InterfaceTuple;
import Services.Profile.Predicat;

public interface InterfacePost {
	
	public void creer(String table_name, InterfaceTuple tuple) throws SQLException;

	public void supprimer(String table_name, Predicat predicate) throws SQLException;
	
}
