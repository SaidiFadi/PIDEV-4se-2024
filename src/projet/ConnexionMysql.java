/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/**
 *
 * @author FADI
 */
public class ConnexionMysql {
    public Connection cn=null;
      public static Connection connexionDB() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
      Connection cn= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/locationlogement","root","");
                 System.out.println("connexion reussite");

      return cn;
        } catch (ClassNotFoundException e) {
            System.out.println("connexion echouee");
            e.printStackTrace();
            return null;
        }
}
    
}
