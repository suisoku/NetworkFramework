package Services.Groups;
//
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import Core.BD.Connexionsgbd;
import Services.Profile.InterfaceTuple;
import Services.Profile.Predicat;

public class Groupe extends AbstractGroupe {
	protected final String groupCreator;

	public Groupe(String groupCreator, String nameGroupe) {
		super(nameGroupe);
		this.groupCreator = groupCreator;
	}

	public String PreparedStatementCreator( String table_Name, int nombrePI) throws SQLException {
		Statement stmt = Connexionsgbd.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_Name);
		ResultSetMetaData rsmd = rs.getMetaData();
		String champs = "INSERT INTO " + table_Name + "( ";
		String valeurs = " VALUES (";
		for (int i = 0; i < nombrePI - 1; i++) {
			champs += rsmd.getColumnName(i + 1);
			valeurs = valeurs + "?,";
		}

		return champs += rsmd.getColumnName(nombrePI) + valeurs.concat("?)");
	}

	@Override
	public void addMember( String table_Name, InterfaceTuple tuple) throws SQLException {
        Statement stmt = Connexionsgbd.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_Name);
		ResultSetMetaData rsmd = rs.getMetaData();
		String champs = "INSERT INTO " + table_Name + "(";
		String valeurs = " VALUES (";
		for (int i = 0; i < tuple.getSize() - 1; i++) {
			champs += rsmd.getColumnName(i + 1) + ",";
			valeurs = valeurs + "?,";
		}
		String req = champs += rsmd.getColumnName(tuple.getSize()) + ")" + valeurs.concat("?)");
        PreparedStatement preparedStatement = Connexionsgbd.getConnection().prepareStatement(req);
		for (int i = 0; i < tuple.getSize(); i++) {
			preparedStatement.setObject(i + 1, tuple.getValue(i));
			
		}
		preparedStatement.executeUpdate();
		Connexionsgbd.getConnection().commit();;

	}

	@Override
	public void deleteGroup(String table_Name, Predicat predicat)throws SQLException {
		Statement stmt =Connexionsgbd.getConnection().createStatement();	
		String Query="delete from " + table_Name + " where " + 
		predicat.Name + " = " + predicat.value;
		stmt.executeUpdate(Query);
		Connexionsgbd.getConnection().commit();
		stmt.close();
		
	}
//
	@Override
	public void deleteMember(String table_Name, Predicat predicat)throws SQLException {
		Statement stmt =Connexionsgbd.getConnection().createStatement();	
		String Query="delete from " + table_Name + " where " + 
		predicat.Name + " = " + predicat.value;
		stmt.executeUpdate(Query);
		Connexionsgbd.getConnection().commit();
		stmt.close();
		
	}

	

}
