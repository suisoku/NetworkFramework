package Test;

import java.sql.SQLException;

import Core.BD.InteractionBD;
import Core.BD.ConnectionBD;
import Core.Session.AccountInfo;

public class noureddine_test_clientServiceGUI {

    /**
     *  ****************** MAIN METHOD ***********************
     */
    
    public static void main(String[] args) {
    	
    	InteractionBD bd = new InteractionBD(ConnectionBD.getConnection() , "USERS", "DATAMESSAGE");
    	
    	AccountInfo details =  new AccountInfo("baba","azerty");
        try {
			bd.add(details);
		} catch (SQLException e) {
			System.out.println("add didnt work");
			e.printStackTrace();
		}
    }
    
    
}
