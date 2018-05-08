package Services.Posts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import Core.UserInfo;
import Core.BD.Connexionsgbd;
import Core.BD.connection;
import Services.Profile.InterfaceTuple;
import Services.Profile.Predicat;

public abstract class AbstractPost  {

	protected UserInfo userPoster;
	protected Date datePost;
	protected int idPost;
	protected String textField;
	
	public String datePostToString(java.util.Date date2) {
		java.util.Date date1 = new java.util.Date();
		long time = ((date1.getTime() - date2.getTime()) / 60000);
		if (time < 60) {
			return time + " min";
		} else if (time >= 60 && time < 1440) {
			return (time / 60) + " h";
		} else {
			return date2.toString();
		}
	}


	public String getTextField() {
		return this.textField;
	}
	public void editTextField(String contenu) {
		this.textField = contenu;
	}
	public UserInfo getUser() {
		return this.userPoster;
	}
	public Date getDatePost() {
		return this.datePost;
	}
	public Integer getIdPost() {
		return this.idPost;
	}

/**
	@Override
	public void creer(String table_name, InterfaceTuple tuple) throws SQLException {

		Statement stmt = connection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + table_name);
		ResultSetMetaData rsmd = rs.getMetaData();
		String champs = "INSERT INTO " + table_name + "(";
		String valeurs = " VALUES (";
		for (int i = 0; i < tuple.getSize() - 1; i++) {
			champs += rsmd.getColumnName(i + 1) + ",";
			valeurs = valeurs + "?,";
		}
		String req = champs += rsmd.getColumnName(tuple.getSize()) + ")" + valeurs.concat("?)");
		PreparedStatement preparedStatement = connection.getConnection().prepareStatement(req);
		for (int i = 0; i < tuple.getSize(); i++) {
			preparedStatement.setObject(i + 1, tuple.getValue(i));

		}
		preparedStatement.executeUpdate();
		connection.getConnection().commit();
		;
	}

	@Override
	public void supprimer(String table_name, Predicat predicate) throws SQLException {

		Statement stmt = connection.getConnection().createStatement();
		String Query = "delete from " + table_name + " where " + Predicat.Name + " = " + Predicat.value;
		stmt.executeUpdate(Query);
		connection.getConnection().commit();
		stmt.close();
	}
	*/
}
