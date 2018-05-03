package Core.BD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in
 Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 * @author catherineberrut
 */
public class requetesbd {

    /**
     * Afficher toutes les informations sur tous les spectacles.
     *     
* @param conn connexion � la base de donn�es
     * @throws SQLException en cas d'erreur d'acc�s � la base de donn�es
     */
    public static void nbemp(Connection conn) throws
            SQLException {

// Get a statement from the connection
        Statement stmt = conn.createStatement();

// Execute the query
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM EMP");
        while (rs.next()) {
            System.out.println("Nombre d'employés : " + rs.getInt(1));
        }
// Close the result set, statement and the connection 
        rs.close();
        stmt.close();
        System.out.println("********************************************************\n");

    }

    public static void employes(Connection conn) throws
            SQLException {

// Get a statement from the connection
        Statement stmt = conn.createStatement();

        System.out.print("N° Employé " + "\t");
        System.out.print("Salaire : " + "\t");;
        System.out.print("Commission :\t" + "\t");
        System.out.println("Date d'embauche :");
// Execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
        while (rs.next()) {
            System.out.print(rs.getInt(1) + "\t\t");
            System.out.print(rs.getString(6) + "\t\t");;
            System.out.print(rs.getString(7) + "\t\t");
            System.out.println("\t" + rs.getString(5));
        }
        System.out.println("***********************************************************************************\n");
// Close the result set, statement and the connection 
        rs.close();
        stmt.close();
    }

    public static void supEmployes(Connection conn) throws SQLException {
        // Execute the query
        try ( // Get a statement from the connection
                Statement stmt = conn.createStatement(); // Execute the query
                ResultSet rs = stmt.executeQuery("SELECT A.ename, B.ename FROM emp A JOIN emp B ON (B.mgr = A.empno)")) {
            while (rs.next()) {
                System.out.print("ename : " + rs.getString(2) + " ");
                System.out.println("supname : " + rs.getString(1) + " ");
            }
            System.out.println("***********************************************************************************\n");
            // Close the result set, statement and the connection 
        rs.close();
        stmt.close();
        }
        
        
    }

    public static void revenus(Connection conn) throws SQLException {
        try ( // Get a statement from the connection
                Statement stmt = conn.createStatement(); // Execute the query
                ResultSet rs = stmt.executeQuery("SELECT deptno, sum(sal+NVL(comm,0)) FROM emp GROUP BY deptno")) {
            while (rs.next()) {
                System.out.print("deptno : " + rs.getInt(1) + " ");
                System.out.println("revenu : " + rs.getInt(2) + " ");
            }
             System.out.println("***********************************************************************************\n");
            // Close the result set, statement and the connection 
        rs.close();
        stmt.close();

        }
    }
}
