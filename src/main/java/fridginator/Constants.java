// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package fridginator;

/**
 * This class contains a bunch of magic numbers that would otherwise 
 * be scattered around the codebase making it difficult to go in and
 * change simple things, like whatever I'm calling this version.
 * 
 * @author dcodeh 
 */
public class Constants {
    
    public static final String VERSION_NUMBER = "1.0"; // woo hoo!
    
    public static final String RELEASE_NAME = "Al Dente";
    
    public static final String FRIDGINATOR_LOGO =
            "\n" + 
            "\n" + 
            "\n" + 
            "   /)  ,_   .  __/   __   .  ,__,  __,  -/- _,_ ,_  \n" + 
            " _//__/ (__/__(_/(__(_/__/__/ / (_(_/(__/__(_/_/ (_\n" + 
            "_/                  _/_    \n" + 
            "/)                 (/    \n" + 
            "`    \n" + 
            "\n";
    
    public static final String DB_LOCATION = "/opt/fridginator/fridginator.db";

    public static final int PORT = 56932;

    public static final long INVENTORY_THREAD_SLEEP = 6; // hours

    private static final long UBERTHREAD_SLEEP_HOURS = 24; // hours
    public static final long UBERTHREAD_SLEEP_MILLIS = UBERTHREAD_SLEEP_HOURS * 3 * (10^6);

    public static final long MIN_ACTUAL_USAGE_WINDOW = 48; // hours
    public static final long HOURS_PER_WEEK = 168;
    public static final long HOURS_PER_9_DAYS = 9 * 24;

}
