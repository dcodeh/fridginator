// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package model;

/**
 * Stores information for a misc list item. It will be displayed by the
 * list page template.
 *
 * The information for this object can come from anywhere, but it should
 * probably come from the records in the database...hey it's kinda like
 * hibernate!
 *
 * @author dcodeh
 */
public class MiscItem {

    private boolean checked;
    private String line;
    private int id;

    public MiscItem(boolean checked, String line, int id) {
        this.checked = checked;
        this.line = line;
        this.id = id;
    }

    public String getLine() {
        return this.line;
    }

    public boolean getIsChecked() {
        return checked;
    }

    public int getID() {
        return id;
    }

}