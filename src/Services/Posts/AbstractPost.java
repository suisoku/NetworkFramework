package Services.Posts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Core.BD.Connexionsgbd;
import Core.BD.connection;
import Services.Profile.InterfaceTuple;
import Services.Profile.Predicat;

public abstract class AbstractPost implements InterfacePost {
	
	private static final long serialVersionUID = 1L;
	private InterfacePost interfacePost;
	private Post post;

	public void init() {
		post = new Post();
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

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
	
    public void Poster() {
        
     // ici je dois trouver un moyen comment intéger l'identité du User qui va poster
        
        post.setdatePost(new java.util.Date());
        interfacePost.creer(post);
        System.out.println(post);

    }

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
}

// blob: type dans pour fichier , oracle.