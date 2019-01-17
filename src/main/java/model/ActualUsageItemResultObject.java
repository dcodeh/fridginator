// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package model;

import java.text.ParseException;
import java.util.Date;

import fridginator.DB;

/**
 * Stores information needed by the Inventory thread to calculate actual usage
 *
 * @author dcodeh
 */
public class ActualUsageItemResultObject {

    private int id;
    private float actual;
    private float currentQty;
    private boolean weekly;
    private Date inventoryDate;

    public ActualUsageItemResultObject(int id, float actual, float currentQty, boolean weekly, String inventoryDate) {
        this.id = id;
        this.actual = actual;
        this.currentQty = currentQty;
        this.weekly = weekly;

        try {
            this.inventoryDate = DB.timestampFormat.parse(inventoryDate);
        } catch (ParseException e) {
            System.out.println("Uh oh...couldn't parse a date!");
            this.inventoryDate = new Date();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getActual() {
        return actual;
    }

    public void setActual(float actual) {
        this.actual = actual;
    }

    public float getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(float currentQty) {
        this.currentQty = currentQty;
    }
    
    public Date getInventoryDate() {
        return this.inventoryDate;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

}
