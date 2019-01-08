// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package model;

/**
 * Stores detailed information for an item the user shares. It will be displayed by the
 * items page.
 *
 * The information for this object can come from anywhere, but it should
 * probably come from the records in the database...hey it's kinda like
 * hibernate!
 *
 * @author dcodeh
 */
public class SharedItem {

    private String name;
    private int id;
    private float quantity;
    private float fullQuantity;
    private float weeklyUsage;
    private String unit;
    private boolean hasWarning;
    private String warningMessage;

    public SharedItem(String name, int id, float quantity, float fullQuantity, float weeklyUsage, String unit) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.fullQuantity = fullQuantity;
        this.weeklyUsage = weeklyUsage;
        this.unit = unit;
        this.hasWarning = false;
        this.warningMessage = null;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return String.valueOf(id);
    }

    public String getWeeklyUsageString() {
        return String.format("%.2f %s per week", weeklyUsage, unit);
    }

    public String getQuantityForImage() {
        float percentage = quantity/fullQuantity;

        if(percentage > 0.85) {
            return "4"; // basically full
        } else if(percentage > 0.63) {
            return "3"; // roughly 75% full
        } else if(percentage > 0.38) {
            return "2"; // roughly half full
        } else if(percentage > 0.12) {
            return "1"; // roughly 25% full
        } else {
            return "0"; // basically empty, or something went wrong
        }
    }

    public String getQuantityForText() {
        return String.format("%.2f", quantity);
    }

    public String getUnit() {
        return unit;
    }

    public void setWarning(String message) {
        hasWarning = true;
        warningMessage = message;
    }

    public void clearWarning() {
        hasWarning = false;
        warningMessage = null;
    }

}