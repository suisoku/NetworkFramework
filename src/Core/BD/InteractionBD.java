package Core.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import Core.UserInfo;
import Services.DataUtilities.Data_message;

/**   FOR TESTING PURPOSES TEMPORARY DB TABLE
 * CREATE TABLE DATAMESSAGE
(
  PSEUDO varchar(50),
  IDSENDER varchar(50),
  DATESEND varchar(50),
  MESSAGE varchar(300),
  foreign key (PSEUDO) references USERS(PSEUDO)
);

 */
public class InteractionBD {

	
	private  Connection  conn;
	private String user_table;
	private String data_table;
	
	
	
	
	public InteractionBD(Connection conn , String user_table , String data_table ){
		this.conn = conn;
		this.user_table = user_table;
		this.data_table = data_table;
	}
	
	public  boolean identify(UserInfo details) throws SQLException {
		
		int count = 0;
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from " + this.user_table + 
				" where PSEUDO = '" + details.getPseudo() +
				"' and PASSWORD = '" + details.getPassword() +"'"
				);
				
		 while (rs.next())count++;
		 rs.close();
		 stmt.close();
		 
		if(count == 1)return true; 
		else return false;
	}
	
	public  boolean lookUser(UserInfo details) throws SQLException {
		
		int count = 0;
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from " + this.user_table + 
				" where PSEUDO = '" + details.getPseudo() + "'");
		
		while (rs.next())count++;
		rs.close();
		stmt.close();
		 
		if(count == 1)return true; 
		else return false;
	}
	
	
	
	
	public void add(UserInfo details) throws SQLException {
		
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate(
				"INSERT INTO " +
				this.user_table + 
				" VALUES ('"
                + details.getPseudo() + "','" 
                + details.getPassword() +  "')"); 
		
		stmt.close();
	}
	
	
	public ArrayList<Data_message> loadDataMessages(UserInfo details)  throws SQLException, ParseException {
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from " + data_table + 
				" where PSEUDO = '" + details.getPseudo() + "'"
				);
		
		
		
		ArrayList<Data_message> data = new ArrayList<Data_message>();
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		
		while (rs.next()) {
			 
			data.add(new Data_message(
				 			rs.getString("IDSENDER") , 
					 		df.parse(rs.getString("DATESEND")),
					 		rs.getString("MESSAGE")
				 		)
			);
		}
		rs.close();
		stmt.close();
		
		return data;
		
	}
	
	
	public void storeIntoBD(String pseudo , Data_message dt )  throws SQLException, ParseException {
	
		Statement stmt = conn.createStatement();
		
		
		stmt.executeUpdate(
				"INSERT INTO " + this.data_table + " VALUES ('"
                + pseudo + "','" 
        		+ dt.getId_sender() + "','" 
        		+ dt.getDate().toString() + "','" 
                + dt.getData() +  "')"); 
		
		stmt.close();
	}
	
}
