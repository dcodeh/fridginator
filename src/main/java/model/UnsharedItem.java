// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package model;

/**
 * Stores information for an item that a user does not share. It will be displayed by the
 * items page.
 *
 * The information for this object can come from anywhere, but it should
 * probably come from the records in the database...hey it's kinda like
 * hibernate!
 *
 * @author dcodeh
 */
public class UnsharedItem {

    private int id;
    private String name;

    public UnsharedItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getID() {
        return String.valueOf(id);
    }

    public String getName() {
        return name;
    }
}