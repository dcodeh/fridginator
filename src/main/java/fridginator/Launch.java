// Copyright (c) 2018 David Cody Burrows
package fridginator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        DB db = new DB(conn);
        
//        // Start the Webserver
//        System.out.println("Starting WebServer...");
//        final TemplateEngine templateEngine = new FreeMarkerEngine();
//        final WebServer webServer = new WebServer(db, templateEngine);
//        webServer.init();
//        System.out.println("DONE");
//
//        InventoryRunnable inventoryRunnable = new InventoryRunnable(db, Constants.INVENTORY_THREAD_SLEEP);
//        ScheduledExecutorService inventoryExecutor = Executors.newScheduledThreadPool(1 /* only 1 thread*/);
//        inventoryExecutor.scheduleAtFixedRate(inventoryRunnable,
//                                              1l /* initialDelay*/,
//                                              Constants.INVENTORY_THREAD_SLEEP,
//                                              TimeUnit.HOURS);

//        TimerTask uberThread = new UberThread(db);
//        Timer timer = new Timer();
//
//        // make it run at 11:50 PM today
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 23);
//        cal.set(Calendar.MINUTE, 50);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//
//        Date initialRun = cal.getTime();
//        timer.scheduleAtFixedRate(uberThread, initialRun, Constants.UBERTHREAD_SLEEP_MILLIS);
        UberThread uber = new UberThread(db);
        Thread t = new Thread(uber);
        t.start();

        // I want to be a good person. I really do, but I'm not closing the resources here
        // because the only way this application is going down is if it's killed.
        // And the JVM does it for me anyway. What a guy.
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
