// Copyright (c) 2018 David Cody Burrows
package fridginator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Starts up threads and other services required to make 
 * Fridginator run properly.
 * 
 * @author dcodeh 
 */
public class Launch {

    /**
     * TODO Docs, after this actually does what it should do
     * @param args
     */
    public static void main(String [] args) {
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:/home/cody/projects/fridginator/db/fridginator.db");
            
            Statement s = c.createStatement();
            s.executeUpdate("create table dave (id int primary key);");
            s.close();
            c.close();
            System.out.println("Create table foo");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
