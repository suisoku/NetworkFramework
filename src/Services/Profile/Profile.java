
package Services.Profile;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;

import Core.BD.ConnectionSgbd;
import Core.BD.Predicat;
import Core.BD.Tuple;

public class Profile {
	public static Tuple getProfile( String table_Name, Predicat predicat) throws SQLException {
		Statement stmt = ConnectionSgbd.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_Name + " where " + predicat.Name + " = " + predicat.value);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Object[] values = new Object[columnCount];
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				values[i-1] = rs.getObject(i);
			}
		}
		rs.close();
		stmt.close();
		return new Tuple(values);
	}
	
	public static void updateProfile( String table_Name, Predicat predicat) throws SQLException {
		Statement stmt =ConnectionSgbd.getConnection().createStatement();
	    String Query;
		 for (Entry<String, Object> mapentry : predicat.map.entrySet()) {
			 Query="update " + table_Name + " set "+ mapentry.getKey() + "=" + "'"+mapentry.getValue()+"'"
					        + " where " + predicat.Name + "=" + predicat.value;
		    stmt.executeUpdate(Query);
			
		 
		 }
		 stmt.close();
		 ConnectionSgbd.getConnection().commit();
		 
	}
	
	public static void deleteProfile( String table_Name, Predicat predicat) throws SQLException {
		Statement stmt =ConnectionSgbd.getConnection().createStatement();	
		String Query="delete from " + table_Name + " where " + 
		predicat.Name + " = " + predicat.value;
		stmt.executeUpdate(Query);
		ConnectionSgbd.getConnection().commit();
		stmt.close();
		
	}
}