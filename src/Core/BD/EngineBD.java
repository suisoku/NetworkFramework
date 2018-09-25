package Core.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;

public class EngineBD implements _EngineBD {

	private Connection con;

	public EngineBD(Connection con) {
		this.con = con;
	}

	@Override
	public void addData(String table_Name, _Tuple data) throws SQLException {

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_Name);
		ResultSetMetaData rsmd = rs.getMetaData();

		String champs = "INSERT INTO " + table_Name + "(";
		String valeurs = " VALUES (";

		for (int i = 0; i < data.getSize() - 1; i++) {
			champs += rsmd.getColumnName(i + 1) + ",";
			valeurs = valeurs + "?,";
		}

		String req = champs += rsmd.getColumnName(data.getSize()) + ")" + valeurs.concat("?)");
		
		PreparedStatement preparedStatement = con.prepareStatement(req);
		for (int i = 0; i < data.getSize(); i++) {
			preparedStatement.setObject(i + 1, data.getValue(i));

		}

		preparedStatement.executeUpdate();

		con.commit();
		
		rs.close();
		stmt.close();

	}

	@Override
	public ArrayList<Tuple> getData(String table_Name, Predicat... predicats) throws SQLException {

		Statement stmt = con.createStatement();
		
		String suite_predicats="";
		if(predicats.length > 0)suite_predicats = " where " ;
		
		for(int i = 0 ; i < predicats.length ; i ++) {
			suite_predicats += predicats[i].Name + " = '" + predicats[i].value + "'";
			if(i < predicats.length - 1 )suite_predicats += " AND ";
		}
		
		ResultSet rs = stmt.executeQuery("select * from " + table_Name + suite_predicats);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		ArrayList<Tuple> tuples = new ArrayList<Tuple>();
		Object[] values;
		
		while (rs.next()) {
			values = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				values[i - 1] = rs.getObject(i);
			}
			tuples.add(new Tuple(values));
		}
		
		rs.close();
		stmt.close();
		return tuples;
	}

	@Override
	public void deleteData(String table_Name, Predicat... predicats) throws SQLException {
		Statement stmt = con.createStatement();
		
		String suite_predicats="";
		if(predicats.length > 0)suite_predicats = " where " ;
		
		for(int i = 0 ; i < predicats.length ; i ++) {
			suite_predicats += predicats[i].Name + " = '" + predicats[i].value + "'";
			if(i < predicats.length - 1 )suite_predicats += " AND ";
		}
		
		String Query = "delete from " + table_Name + suite_predicats;

		stmt.executeUpdate(Query);
		con.commit();
		stmt.close();

	}

	@Override
	public void updateData( String table_Name, Predicat predicat) throws SQLException {
		Statement stmt =ConnectionBD.getConnection().createStatement();
	    String Query;
	    int lastElement=0;
		String ClauseWhere="";
	    for (Entry<String, Object> mapentry : predicat.mapSelect.entrySet()) {
	    	if (lastElement == (predicat.mapSelect.size()-1)) 
	    		ClauseWhere+=mapentry.getKey() + "=" + "'"+ mapentry.getValue()+"'";
	    	else	    		
	    	ClauseWhere+=mapentry.getKey() + "=" + mapentry.getValue() + " AND ";
	    	lastElement++;
	    }
		 for (Entry<String, Object> mapentry : predicat.mapUpdate.entrySet()) {
			 Query="update " + table_Name + " set "+ mapentry.getKey() + "=" + "'"+mapentry.getValue()+"'"
					        + " where " + ClauseWhere;
		    stmt.executeUpdate(Query);
			
		 
		 }
		 stmt.close();
		 ConnectionBD.getConnection().commit();
		 
	}

	@Override
	public ArrayList<Tuple> searchData(String table_Name, Object keyWord) throws SQLException {
		ArrayList<Tuple> ListeTuple = new ArrayList<Tuple>();

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_Name);
		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();
		String champs = "", Query;

		for (int i = 0; i < columnCount - 1; i++) {
			champs += rsmd.getColumnName(i + 1) + "||','||";
		}

		champs += rsmd.getColumnName(columnCount);
		Query = "SELECT * FROM " + table_Name + " WHERE (" + champs + ") LIKE '%" + keyWord + "%'";
		ResultSet rs1 = stmt.executeQuery(Query);

		while (rs1.next()) {
			Object[] values = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				values[i - 1] = rs1.getObject(i);
			}
			ListeTuple.add(new Tuple(values));
		}

		return ListeTuple;

	}

	public ArrayList<String> getSchema(String table) throws SQLException{
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table );
		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();
		ArrayList<String>  champs = new ArrayList<String>();

		for (int i = 0; i < columnCount; i++) {
			champs.add( rsmd.getColumnName(i + 1) );
		}
		return champs;
	}
}
