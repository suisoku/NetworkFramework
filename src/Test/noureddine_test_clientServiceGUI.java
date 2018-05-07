package Test;

import java.sql.SQLException;

import Core.UserInfo;
import Core.BD.InteractionBD;
import Core.BD.connection;

public class noureddine_test_clientServiceGUI {

    /**
     *  ****************** MAIN METHOD ***********************
     */
    
    public static void main(String[] args) {
    	
    	InteractionBD bd = new InteractionBD(connection.getConnection() , "USERS", "DATAMESSAGE");
    	
    	UserInfo details =  new UserInfo("baba","azerty");
        try {
			bd.add(details);
		} catch (SQLException e) {
			System.out.println("add didnt work");
			e.printStackTrace();
		}
    }
    
    
}
