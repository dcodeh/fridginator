// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details.
package model;

/**
 * Stores an item's id, name, unit
 *
 * @author dcodeh
 */
public class ItemResultObject {

    private int id;
    private String name;
    private String unit;

    public ItemResultObject(int id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
