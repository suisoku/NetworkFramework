package Core.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class InteractionBD {

	
	private  Connection  conn;
	private String table_name;
	
	public InteractionBD(Connection conn , String table_name){
		this.conn = conn;
		this.table_name = table_name;
	}
	
	public  boolean look(Sign details) throws SQLException {
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from " + table_name + 
				" where PSEUDO = " + details.getPseudo() +
				"and PASSWORD =" + details.getPassword()
				);
		
		ResultSetMetaData rsmd = rs.getMetaData();
		
		if(rsmd.getColumnCount() == 1) {return true;}
		else return false;
	}
	
	
	
	public  void add(Sign details) throws SQLException {
		
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate(
				"INSERT INTO " +
				this.table_name + 
				"VALUES ('"
                + details.getPseudo() + "','" 
                + details.getPassword() +  "')"); 
		
		stmt.close();
	}
}
