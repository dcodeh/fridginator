// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package model;

/**
 * Stores information for a shared list item. It will be displayed by the
 * list page template.
 *
 * The information for this object can come from anywhere, but it should
 * probably come from the records in the database...hey it's kinda like
 * hibernate!
 *
 * @author dcodeh
 */
public class SharedItem {

    private boolean checked;
    private String name;
    private String line;

    public SharedItem(boolean checked, String name, String line) {
        this.checked = checked;
        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public boolean getIsChecked() {
        return checked;
    }

}