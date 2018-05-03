package Core.BD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import Services.Profile.Profile;
import Services.Profile.Predicat;
import Services.Profile.Tuple;

public class Connexionsgbd {

	private static final String configurationFile = "BD.properties";

	public static void main(String args[]) {
		try {
			String jdbcDriver, dbUrl, username, password;
			DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
			jdbcDriver = dap.getJdbcDriver();
			dbUrl = dap.getDatabaseUrl();
			username = dap.getUsername();
			password = dap.getPassword();
			// Load the database driver
			Class.forName(jdbcDriver);// Get a connection to the database
			Connection conn = DriverManager.getConnection(dbUrl, username, password);

			// *************Teste Profile *************************************************
			/*Tuple Profile_Infos;
			Predicat Id_user = new Predicat("EMPNO", 7876);
			Profile_Infos = Profile.getProfile(conn, "EMP", Id_user);
			for (Object o : Profile_Infos) {
				System.out.print(o + "\t ");
			}*/
			
			// *************Teste EditProfile *************************************************
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("JOB", "etudiant");
			map.put("ENAME", "abdou");
			map.put("SAL", 5000);
			Predicat Id_user2 = new Predicat("EMPNO", 7876,map);
			Profile.updateProfile(conn, "EMP", Id_user2);
			
			// Print information about connection warnings
			SQLWarningsExceptions.printWarnings(conn);
			conn.close();
		} catch (SQLException se) {
			// Print information about SQL exceptions
			SQLWarningsExceptions.printExceptions(se);
			return;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
