// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package model;

/**
 * Stores information needed by the Inventory thread to auto decrement
 * quantities
 *
 * @author dcodeh
 */
public class InventoryItemResultObject {

    private int id;
    private float actual;

    public InventoryItemResultObject(int id, float actual) {
        this.id = id;
        this.actual = actual;
    }

    public int getItemID() {
        return id;
    }

    public float getActualUsage() {
        return actual;
    }

}
