// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class abstracts all of the database operations
 * @author dcodeh 
 */
public class DB {
    
    private Connection conn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
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
     *
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

    /**
     * Get the fridge code stored in the database
     *
     * @return The fridge code. It is guaranteed to be non-null
     */
    public String getFridgeCode() {
        String code = "";
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select secret_key from fridge where id = 1;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                code = set.getString(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return code;
    }

    /**
     * Check to see if a certain username has already been taken or not.
     *
     * @param username The username to check
     * @return True if the username has not been taken. False otherwise.
     */
    public boolean usernameAvailable(String username) {
        int count = 0;
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("select count(*) from (select id from user where name='")
                .append(username)
                .append("');");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                count = set.getInt(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return count == 0;
    }

    /**
     * Create a new user in the database. It will set username and password, and will initialize all
     * of the fields to 0 or null.
     *
     * @param username The name of the user
     * @param password The password for the user
     * @return
     */
    public boolean createUser(String username, String password) {
        boolean success = true;
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("insert into user (name, password, joined, saved, spent, shop_day, shop_weeks) values")
                .append("('")
                .append(username)
                .append("', '")
                .append(password)
                .append("', '")
                .append(dateFormat.format(new Date()))
                .append("', ")
                .append("0.0, 0.0, ") // saved, spent
                .append("0, 1") // shop_day, shop_weeks
                .append(");");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            success = false;
        }

        return success;
    }
}
