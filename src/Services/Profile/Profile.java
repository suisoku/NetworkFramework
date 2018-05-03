
package Services.Profile;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Profile {
	public static Tuple getProfile(Connection conn, String table_Name, Predicat predicat) throws SQLException {
		Statement stmt = conn.createStatement();
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
	public static void updateProfile(Connection conn, String table_Name, Predicat predicat) throws SQLException {
		Statement stmt =conn.createStatement();
	    String Query;
		 for (Entry<String, Object> mapentry : predicat.map.entrySet()) {
			 Query="update " + table_Name + " set "+ mapentry.getKey() + "=" + "'"+mapentry.getValue()+"'"
					        + " where " + predicat.Name + "=" + predicat.value;
		    stmt.executeUpdate(Query);
			
		 
		 }
		 stmt.close();
		 conn.commit();
		 
	}
	public static void deleteProfile(Connection conn, String table_Name, Predicat predicat) throws SQLException {
		Statement stmt =conn.createStatement();	
		String Query="delete from " + table_Name + " where " + 
		predicat.Name + " = " + predicat.value;
		stmt.executeUpdate(Query);
		conn.commit();
		stmt.close();
		
	}
}