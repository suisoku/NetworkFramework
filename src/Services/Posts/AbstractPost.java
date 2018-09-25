package Services.Posts;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import Core.Session.AccountInfo;

public abstract class AbstractPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountInfo userPoster;
	private Date datePost;
	private UUID idPost; 
	private String textField;
	

	public AbstractPost(String textfield , AccountInfo user, UUID id_param) {
		this.datePost = new Date();
		this.textField = textfield;
		idPost = id_param;
		this.userPoster = user;
	}
	

	
	public String getTextField() {
		return this.textField;
	}
	public void editTextField(String contenu) {
		this.textField = contenu;
	}
	public AccountInfo getUser() {
		return this.userPoster;
	}
	public Date getDatePost() {
		return this.datePost;
	}
	public UUID getIdPost() {
		return this.idPost;
	}

	public void setDate(Date d) {
		this.datePost = d;
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
