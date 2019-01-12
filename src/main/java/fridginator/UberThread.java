// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package fridginator;

import java.util.Calendar;
import java.util.TimerTask;

/**
 * THIS IS ACTUALLY A RUNNABLE OBJECT, NOT A THREAD OBJECT!
 * UberThread just sounds much cooler than UberRunnable.
 *
 * When this runs as a thread, it will calculate and update
 * actual item usage, determine how much of each item is needed, and
 * generate shopping lists.
 *
 * @author dcodeh
 */
public class UberThread extends TimerTask {

    private DB db;

    public UberThread(DB db) {
        this.db = db;
    }

    @Override
    public void run() {
        if(db.getUberThreadLock()) {
            print("uberthread running...");

            Calendar carl = Calendar.getInstance(); // I didn't mean to type that r, but I am glad that I did
            
            // if it's friday, check to see if other users have things on their shared lists
            if(carl.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                db.auditBums();
            }

            calculateActualUsage();

            generateLists();

        } else {
            print("Another uberthread is already running. Exiting.");
        }

    }

    private void print(String s) {
        System.out.println("[UBER] " + s);
    }

    /**
     * Calculate and update the actual amounts of items that are 
     * being used, and decide how much of each item should be kept around.
     */
    private void calculateActualUsage() {
        // for each item in the db
            // calculate actual usage
                // find the average usage between last hard point (or restock) and now (must be at least 48 hours wide)
                // take average of the old and new actual usage, and store that to smooth out the curve a little
            // scale back each user's actual usage to that it adds up to the actual actual

            // if weekly, update PQs for how much we'll need to get through a full week (9 days)
            // if monthly, update PQ to largest (or a month's supply)
    }

    private void generateLists() {
        // clear everyone's shared list

        // for each item
            // Add it to the list if it's empty
            // Otherwise, calculate the actual quantity at the end of the week (9 days)
                // if it's below 0, add it to the list
                // if it's weekly, add it so it'll get topped up
                // if it's monthly check if it will make it for another 2 weeks at current usage. If it will run out before
                    // the next week, add it.

        // VENN SCHEDULING
            // For each user (a special for each that will loop for
                // U1, U2, U3, U1&U2, U1&U3, U2&U3, U1&U1&U3, ETC.
                // get all of the items that group is sharing
                    // for each item
                        // assign a single unit of the largest PQ without going over by more than 1 of the smallest PQ
                        // to the user with the lowest contribution of those being considered
    }

}
