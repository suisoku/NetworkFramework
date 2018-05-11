package Core.BD;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionBD {

    private static final String configurationFile = "BD.properties";
    private static Connection conn;
    
    public static Connection getConnection() {
        try {
            String jdbcDriver, dbUrl, username, password;
            DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
            jdbcDriver = dap.getJdbcDriver();
            dbUrl = dap.getDatabaseUrl();
            username = dap.getUsername();
            password = dap.getPassword();

            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbUrl, username, password);
            SQLWarningsExceptions.printWarnings(conn);
            
            return conn;
            
        } catch (SQLException se) {
            SQLWarningsExceptions.printExceptions(se);
            return null;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
    }
}

}
