// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package fridginator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import model.ActualUsageItemResultObject;
import model.InventoryPointResultObject;
import model.PurchasableQuantity;

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

            restockCheckedItems();
            
            calculateActualUsage();

            // For release 1.0 I'll do this manually
            // to supervise all of the other parts of this application
            // before it starts asking people to spend money
            // generateLists();
            db.releaseUberThreadLock();

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
        ArrayList<ActualUsageItemResultObject> items = db.getItemsForActualCalculation();
        
        for(ActualUsageItemResultObject item : items) {
            InventoryPointResultObject pointA = db.getLastHardPoint(item.getId());
            
            if(pointA == null) {
                pointA = db.getLastRestock(item.getId());
            }
            
            if(pointA != null) {
                Date pointADate = null;
                Date now = new Date();
                try {
                    pointADate = DB.timestampFormat.parse(pointA.getInventoryDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                long diffMillis = pointADate.getTime() - now.getTime();
                long diffHours = TimeUnit.HOURS.convert(diffMillis, TimeUnit.MILLISECONDS);
                
                float actualUsage = item.getActual();
                if(diffHours > Constants.MIN_ACTUAL_USAGE_WINDOW) {
                    float oldActual = actualUsage;
                    
                    float qtyUsed = pointA.getQty() - item.getCurrentQty();
                    float usagePerHour = qtyUsed / diffHours;
                    float newActual = usagePerHour * Constants.HOURS_PER_WEEK;
                    
                    actualUsage = (oldActual + newActual) / 2;
                    db.updateActualUsage(item.getId(), actualUsage / oldActual);
                } else {
                    print("Couldn't calculate actual usage for item " + item.getId() + ": time window too short");
                }
                
                if(item.isWeekly()) {
                    // for 9 days
                    float qtyNeeded = actualUsage * (Constants.HOURS_PER_9_DAYS / Constants.HOURS_PER_WEEK);
                    
                    if(qtyNeeded > 0) {
                        ArrayList<PurchasableQuantity> pqs = db.getPurchasableQuantitiesForItem(item.getId());
                        
                        PurchasableQuantity smallestPQ = db.getSmallestPQ(item.getId());
                        float smallestQty = smallestPQ.getQty();

                        int newMultiplicity = 0;
                        PurchasableQuantity newPQ = null;
                        for(PurchasableQuantity pq : pqs) {
                            
                            float diff = qtyNeeded - pq.getQty();
                            if(diff < 0) {
                                newMultiplicity = findMult(pq, qtyNeeded);
                                newPQ = pq;
                            } else if(diff < smallestQty) {
                                newMultiplicity = 1;
                                newPQ = pq;
                            }
                            
                        }
                        
                        if(newPQ == null) {
                            print("Error finding new PQ for item " + item.getId());
                        } else {
                            db.setFullPQ(item.getId(), newPQ, newMultiplicity);
                        }
                        
                    } else {
                        print("skipping item " + item.getId() + " because it demands qty 0 or less");
                    }
                } else {
                    // monthly item...pick a month's supply
                    PurchasableQuantity newPQ = db.getLargestPQ(item.getId());

                    float qtyNeeded = actualUsage * (Constants.HOURS_PER_9_DAYS / Constants.HOURS_PER_WEEK);
                    int newMultiplicity = findMult(newPQ, qtyNeeded);
                    db.setFullPQ(item.getId(), newPQ, newMultiplicity);
                }
            } else {
                print("Skipping item " + item.getId() + ": couldn't establish time window");
            }
            
        }

        // for each item in the db
            // calculate actual usage
                // find the average usage between last hard point (or restock) and now (must be at least 48 hours wide)
                // take average of the old and new actual usage, and store that to smooth out the curve a little
            // scale back each user's actual usage to that it adds up to the actual actual

            // if weekly, update PQs for how much we'll need to get through a full week (9 days)
            // if monthly, update PQ to largest (or a month's supply)
    }

    private int findMult(PurchasableQuantity pq, float qtyNeeded) {
        float totalQty = 0;
        int mult = 1;
        while(totalQty < qtyNeeded) {
            mult++;
            totalQty = mult * pq.getQty();
        }
        return mult;
    }

    private void generateLists() {
        // clear everyone's shared list
        db.clearSharedLists();

        ArrayList<ActualUsageItemResultObject> items = db.getItemsForActualCalculation();
        HashMap<ActualUsageItemResultObject, Float> neededItems = new HashMap<>();
        for(ActualUsageItemResultObject item : items) {
            if(item.getCurrentQty() <= 0) {
                neededItems.put(item, item.getCurrentQty());
            } else {
                if(item.isWeekly()) {
                    // TODO dcodeh if not friday, adjust qtyNeeded for friday
                } else {
                    
                }
            }
        }
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
    
    private void restockCheckedItems() {
        db.restockCheckedItems();
    }

}
