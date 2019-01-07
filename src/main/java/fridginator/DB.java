// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class abstracts all of the database operations
 * @author dcodeh 
 */
public class DB {
    
    private Connection conn;
    
    public DB(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method will return a user's password if it exists.
     * 
     * @param enteredUsername the user to search for
     * @return The user's password, or null if the user is invalid
     */
    public String queryUsername(String enteredUsername) {
        String password = null;
        
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select password from user where name = '")
                .append(enteredUsername)
                .append("'")
                .append(" limit 1;");
            
            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    /**
     * Return a user's id from their username.
     * @param username The user to get the ID for
     * @return The user's db id, or null if the user doesn't exist.
     */
    public Integer queryUserID(String username) {
        Integer uid = null;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select id from user where name = '")
                    .append(username)
                    .append("'")
                    .append(" limit 1;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                uid = set.getInt(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return uid;
    }

}
