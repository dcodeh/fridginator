// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package model;

/**
 * Store information about a purchasable quantity in the DB
 *
 * Seems kinda like hibernate ;)
 * @author dcodeh
 */
public class PurchasableQuantity {
    private int id;
    private float qty;
    private float price;

    public PurchasableQuantity(int id, float qty, float price) {
        this.id = id;
        this.qty = qty;
        this.price = price;
    }

    public int getID() {
        return id;
    }

    public float getQty() {
        return qty;
    }

    public float getPrice() {
        return price;
    }
}
