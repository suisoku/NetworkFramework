package Core.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Services.Profile.Predicat;
import Services.Profile.Tuple;

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
	
	
	
	public  boolean add(Sign details) throws SQLException {
		
		Statement stmt = conn.createStatement();
		
		int rs = stmt.executeUpdate(
				"INSERT INTO " +
				this.table_name + 
				"VALUES ('"
                + details.getPseudo() + "','" 
                + details.getPassword() + "','"
                + sem.getDejeuner() + "','"
                + sem.getAnimateur() + "')"); 
                
		
		ResultSetMetaData rsmd = rs.getMetaData();
		
		if(rsmd.getColumnCount() == 1) {return true;}
		else return false;
		
		
	}
	
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
}
