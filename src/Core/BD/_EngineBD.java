package Core.BD;

import java.sql.SQLException;
import java.util.ArrayList;

public interface _EngineBD {
	
	public void addData( String table_Name, _Tuple Data) throws SQLException;
	public void  deleteData(String table_Name, Predicat... predicats) throws SQLException;
	public ArrayList<Tuple>  searchData(String table_Name, Object keyWord) throws SQLException;
	public ArrayList<Tuple> getData(String table_Name, Predicat... predicats ) throws SQLException;
	public void updateData(String table_Name, Predicat predicat) throws SQLException;
	

}
