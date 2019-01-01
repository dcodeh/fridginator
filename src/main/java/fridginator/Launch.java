// Copyright (c) 2018 David Cody Burrows
package fridginator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

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
        // gimmick alert:
        System.out.println(Constants.FRIDGINATOR_LOGO);
        
        Date date = new Date();
        
        System.out.println(date); // for nice logs
        System.out.print("Fridginator Version " + Constants.VERSION_NUMBER);
        System.out.println(" (" + Constants.RELEASE_NAME + ")");
        
        // Connect to the database
        System.out.print("Connecting to database...");
        Connection conn = initDB();
        if(conn == null) {
            System.out.println("FAIL");
            System.out.println("Error: Could not establish a database connection.\n" +
                               "Make sure there is a SQLite Database stored in " + Constants.DB_LOCATION + "\n" +
                               "and you have proper access to it.");
            System.exit(1);
        } else {
            System.out.println("DONE");
        }
        
        // Start the Webserver
        System.out.println("Starting WebServer...");
        final TemplateEngine templateEngine = new FreeMarkerEngine();
        final WebServer webServer = new WebServer(templateEngine);
        webServer.init();
        System.out.println("DONE");
        
        // do things
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // Be a good person and close resources even though the JVM
        // does it for me
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        webServer.halt();

    }

    /**
     * Try to connect to the database
     * @return A JDBC database connection. Null if something goes wrong.
     */
    private static Connection initDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try {
            String connString = "jdbc:sqlite:" + Constants.DB_LOCATION;
            Connection c = DriverManager.getConnection(connString);
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
