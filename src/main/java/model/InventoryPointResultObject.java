// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package model;

/**
 * Stores information for an inventory point.
 * 
 * @author dcodeh 
 */
public class InventoryPointResultObject {
    
    private String inventoryDate;
    private float qty;

    public InventoryPointResultObject(String inventoryDate, float qty) {
        super();
        this.inventoryDate = inventoryDate;
        this.qty = qty;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }


}
