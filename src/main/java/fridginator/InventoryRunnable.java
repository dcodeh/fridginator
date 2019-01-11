// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package fridginator;

import model.InventoryItemResultObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class defines a runnable object that automatically decrements item quantities
 * according to their actual usage.
 *
 * @author dcodeh
 */
public class InventoryRunnable implements Runnable {

    private DB db;
    private long hoursBetweenRunning;

    private TimeUnit HOUR = TimeUnit.HOURS;

    public InventoryRunnable(DB db, long runFrequency) {
        this.db = db;
        this.hoursBetweenRunning = runFrequency;
    }

    @Override
    public void run() {
        System.out.println("Inventory Thread starting up...");
        ArrayList<InventoryItemResultObject> items = db.getAllItemsForInventoryUpdate();

        if(items.isEmpty()) {
            print("There is nothing to do -- Exiting"); // feels like python. heh
            return;
        }

        print(items.size() + " items to update");
        print("Run every " + hoursBetweenRunning + " hours");

        for(InventoryItemResultObject i : items) {
            // figure out how much was used since the last run
            // convert units/week to num * (units/Hour)
            float actual = i.getActualUsage();
            float actualPerHour = actual / 168; // 168 = 7 * 24
            float actualPerPeriod = actualPerHour * hoursBetweenRunning;

            print(i.getItemID() + ": decreasing by " + actualPerPeriod);

            db.autoDecrementInventory(i.getItemID(), actualPerPeriod);
        }
    }

    private void print(String s) {
        System.out.println("[INV] " + s);
    }

}
