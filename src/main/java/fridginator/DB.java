// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * This class abstracts all of the database operations
 * @author dcodeh 
 */
public class DB {
    
    private Connection conn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS"); // S is millisecond
    private boolean uberThreadRunning;
    
    public DB(Connection conn) {
        this.conn = conn;
        uberThreadRunning = false;
    }

    /**
     * This method will return a user's password if it exists.
     * 
     * @param enteredUsername the user to search for
     * @return The user's password, or null if the user is invalid
     */
    public String queryUsername(String enteredUsername) {
        String password = null;
        
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select password from user where name = '")
                .append(enteredUsername)
                .append("'")
                .append(" limit 1;");
            
            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    /**
     * Return a user's id from their username.
     *
     * @param username The user to get the ID for
     * @return The user's db id, or null if the user doesn't exist.
     */
    public Integer queryUserID(String username) {
        Integer uid = null;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select id from user where name = '")
                    .append(username)
                    .append("'")
                    .append(" limit 1;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                uid = set.getInt(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return uid;
    }

    /**
     * Get the fridge code stored in the database
     *
     * @return The fridge code. It is guaranteed to be non-null
     */
    public String getFridgeCode() {
        String code = "";
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select secret_key from fridge where id = 1;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                code = set.getString(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return code;
    }

    /**
     * Check to see if a certain username has already been taken or not.
     *
     * @param username The username to check
     * @return True if the username has not been taken. False otherwise.
     */
    public boolean usernameAvailable(String username) {
        int count = 0;
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("select count(*) from (select id from user where name='")
                .append(username)
                .append("');");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                count = set.getInt(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return count == 0;
    }

    /**
     * Create a new user in the database. It will set username and password, and will initialize all
     * of the fields to 0 or null.
     *
     * @param username The name of the user
     * @param password The password for the user
     * @return
     */
    public boolean createUser(String username, String password) {
        boolean success = true;
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("insert into user (name, password, joined, saved, spent, shop_day, shop_weeks) values")
                .append("('")
                .append(username)
                .append("', '")
                .append(password)
                .append("', '")
                .append(dateFormat.format(new Date()))
                .append("', ")
                .append("0.0, 0.0, ") // saved, spent
                .append("0, 1") // shop_day, shop_weeks
                .append(");");

            s.executeUpdate(builder.toString());

            // keep track of the number of users
            s.executeUpdate("update fridge set users = users + 1");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * Gets a users' misc list items and puts them in a single string, with newlines 
     * separating list items.
     * 
     * @param userID The user that owns the list 
     * @return A user's misc list as a string
     */
    public String getMiscListContents(int userID) {
        String list = "";

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("select line from misc_list where user_id=")
                .append(userID)
                .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                list += set.getString(1) + "\n";
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return list;
    }
    
    /**
     * Will return whether or not a user's line item will be checked or not.
     * 
     * @param userID The user's list to check
     * @param lineItem The item to check 
     * @return True if the item is checked, false if it is not, or is null
     */
    public boolean getMiscItemChecked(int userID, String lineItem) {
        boolean checked = false;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("select count(*) from misc_list where user_id=")
                .append(userID)
                .append(" and line = '")
                .append(lineItem)
                .append("'")
                .append("and checked = true;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                checked = set.getInt(1) > 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return checked;
    }

    /**
     * Removes all items from a user's misc list
     * @param userID the user to remove records for.
     */
    public void wipeOutMiscList(int userID) {
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("delete from misc_list where user_id = ")
                .append(userID)
                .append(";");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Adds an item to a user's misc list.
     * @param userID The user who owns the list
     */
    public void setMiscList(int userID, LinkedHashMap<String, Boolean> newList) {
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("insert into misc_list (user_id, checked, line) values");
            
            boolean first = true;
            for(String line : newList.keySet()) {
                
                if(!first) {
                    builder.append(",");
                } else {
                    first = false;
                }
                
                builder
                    .append("(")
                    .append(userID)
                    .append(",")
                    .append(newList.get(line))
                    .append(",'")
                    .append(line)
                    .append("')");
            }
            builder.append(";");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Returns all of the misc items in a user's list as a convenient ArrayList
     * of MiscListItem objects.
     *
     * @param userID The ID of the user that owns the list to return.
     * @return An ArrayList containing MiscListItem objects based on the ones in the user's list.
     */
    public ArrayList<MiscListItem> getMiscItemsList(int userID) {
        ArrayList<MiscListItem> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                .append("select checked, line, id from misc_list where user_id=")
                .append(userID)
                .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                items.add(new MiscListItem(set.getBoolean(1), set.getString(2), set.getInt(3)));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    public ArrayList<SharedListItem> getSharedItemsList(int userID) {
        ArrayList<SharedListItem> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                .append("select i.name, checked, num, pq.qty, i.unit, pq.price from shared_list s")
                .append(" join purchasable_quantity pq on pq.id=s.pq_id")
                .append(" join item i on pq.item_id=i.id")
                .append(" where s.user_id=")
                .append(userID)
                .append(" and s.confirmed=true;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                String name = set.getString(1);
                boolean checked = set.getBoolean(2);
                int num = set.getInt(3);
                float qty = set.getFloat(4);
                String unit = set.getString(5);
                float price = set.getFloat(6);
                int id = set.getInt(7);

                String line = String.format("%.2f %s %s ~$%.2f",
                                            num * qty, // total qty for this line...condense 2x 0.5 gal milk into 1x 1 gal
                                            unit,
                                            name,
                                            num * price); // total price...see above

                items.add(new SharedListItem(checked, name, line, id));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    /**
     * Updates the checked status of a Misc List item.
     *
     * @param id The ID of the misc list item as a String
     * @param checked True to set checked. False otherwise.
     */
    public void setMiscCheckStatus(String id, boolean checked) {

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                .append("update misc_list set checked = ")
                .append(checked)
                .append(" where id = ")
                .append(id)
                .append(";");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    /**
     * Updates the checked status of a Shared List item.
     *
     * @param id The ID of the shared item (as a String)
     * @param checked True to set checked. False otherwise.
     */
    public void setSharedCheckStatus(String id, boolean checked) {

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            /*
           update shared_list set checked = true where id = (select id from shared_list join purchasable_quantity pq on pq.id=pq_id join item i on i.id=pq.id where i.name='lasagna');
             */
            builder
                .append("update shared_list set checked = ")
                .append(checked)
                .append("where id = ")
                .append(id)
                .append(";");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    /**
     * Get the IDs of all of the shared items that belong to a user
     * @param userID The user in question
     * @return A list of IDs as strings
     */
    public ArrayList<String> getSharedItemIDs(int userID) {
        ArrayList<String> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                .append("select id from shared_list where user_id = ")
                .append(userID)
                .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                items.add(set.getString(1));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    /**
     * Get the IDs of all of the misc items that belong to a user
     * @param userID The user in question
     * @return A list of IDs as strings
     */
    public ArrayList<String> getMiscItemIDs(int userID) {
        ArrayList<String> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                    .append("select id from misc_list where user_id = ")
                    .append(userID)
                    .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                items.add(set.getString(1));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }
    
    /**
     * Returns all of the items a user is sharing, and information
     * enough to fill out the items page
     *  
     * @param userID The user to return items for
     * @return A list of SharedItem objects for a given user
     */
    public ArrayList<SharedItem> getSharedItems(int userID) {
        ArrayList<SharedItem> items = new ArrayList<>();
        
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();

            builder
                .append("select i.name, i.id, ")
                .append("(select qty from inventory where item_id=i.id order by time desc limit 1)")
                .append(", i.num_pq, pq.qty, ")
                .append("(select sum(actual) from sharing where item_id=i.id)")
                .append(", i.unit from sharing s")
                .append(" join item i on i.id=s.item_id")
                .append(" left join purchasable_quantity pq on i.full_pq_id=pq.id")
                .append(" where s.user_id =")
                .append(userID)
                .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                String name = set.getString(1);
                int id = set.getInt(2);
                float qty = set.getFloat(3);
                int numPQ = set.getInt(4);
                float fullPQQty = set.getFloat(5);
                float weeklyUsage = set.getFloat(6);
                String unit = set.getString(7);
                
                items.add(new SharedItem(name, id, qty, numPQ, fullPQQty, weeklyUsage, unit));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    public ArrayList<UnsharedItem> getUnsharedItems(int userID) {
        ArrayList<UnsharedItem> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();


            builder
                .append("select i.name, i.id from item i where i.id not in")
                .append("(select item_id from sharing where user_id = ")
                .append(userID)
                .append(")")
                .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                String name = set.getString(1);
                int id = set.getInt(2);

                items.add(new UnsharedItem(id, name));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    /**
     * Get an item's name by ID
     * @param itemID The item's ID as a string
     * @return The item's name, if a valid ID is passed in. null otherwise.
     */
    public String getItemName(String itemID) {
        String name = null;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("select name from item ")
                .append("where id=")
                .append(itemID);

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                name = set.getString(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return name;
    }

    /**
     * Get an item's unit
     * @param itemID an Item ID as a string
     * @return The unit string of an item, if the Id is valid. Otherwise null;
     */
    public String getUnit(String itemID) {
        String unit = null;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select unit from item ")
                    .append("where id=")
                    .append(itemID);

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                unit = set.getString(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return unit;
    }

    /**
     * Shares an item with a user
     * @param userID The user to add to the item
     * @param itemID The item to share 
     * @param usage The user's expected weekly usage
     * @return true if successful. False otherwise.
     */
    public boolean shareItemWithUser(int userID, String itemID, float usage) {
        boolean success = true;
        try {
            Statement s = conn.createStatement();
            StringBuilder insertBuilder = new StringBuilder();
            insertBuilder
                .append("insert into sharing (expected, actual, item_id, user_id) values")
                .append("(")
                .append(usage)
                .append(",")
                .append(usage) // init actual to expected...this will be updated by the system over time
                .append(",")
                .append(itemID)
                .append(",")
                .append(userID)
                .append(");");
            
            // TODO dcodeh recalulate usages and things!

            s.executeUpdate(insertBuilder.toString());

            // keep track of the number of users
            StringBuilder auditBuilder = new StringBuilder();
            auditBuilder
                .append("insert into audit (date, message, user_id) values")
                .append("(")
                .append("'" + timestampFormat.format(new Date()) + "'")
                .append(",")
                .append("'Sharing item " + itemID + "'")
                .append(",")
                .append(userID)
                .append(");");

            s.executeUpdate(auditBuilder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * Unshare an item for a specific user.
     * 
     * @param userID The user to remove from the item
     * @param itemID The item in question
     * @return True if successful, false otherwise.
     */
    public boolean unshareItem(int userID, String itemID) {
        boolean success = true;
        try {
            Statement s = conn.createStatement();
            StringBuilder insertBuilder = new StringBuilder();
            insertBuilder
                .append("delete from sharing where item_id=")
                .append(itemID)
                .append(" and user_id=")
                .append(userID)
                .append(";");
            
            // TODO dcodeh recalculate things!

            s.executeUpdate(insertBuilder.toString());

            // keep track of the number of users
            StringBuilder auditBuilder = new StringBuilder();
            auditBuilder
                .append("insert into audit (date, message, user_id) values")
                .append("(")
                .append("'" + timestampFormat.format(new Date()) + "'")
                .append(",")
                .append("'Unsharing item " + itemID + "'")
                .append(",")
                .append(userID)
                .append(");");

            s.executeUpdate(auditBuilder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * Add a record to the audit table.
     * TODO dcodeh make the other methods that manually do this use this method
     *
     * @param userID the user ID the message pertains to
     * @param message The message to store
     */
    public void audit(int userID, String message) {
        try {
            Statement s = conn.createStatement();
            StringBuilder auditBuilder = new StringBuilder();
            auditBuilder
                    .append("insert into audit (date, message, user_id) values")
                    .append("(")
                    .append("'" + timestampFormat.format(new Date()) + "'")
                    .append(",'")
                    .append(message)
                    .append("',")
                    .append(userID)
                    .append(");");

            s.executeUpdate(auditBuilder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Insert a new item in the item table
     *
     * A new item will only be inserted if another one with the same name doesn't already
     * exist. Once created, the new item's id will be returned.
     * @param itemName The name of the new item
     * @param itemUnit The unit for the new item
     * @param weekly True to refill the item weekly
     * @return The new item's ID, or null if something goes wrong.
     */
    public Integer createItem(String itemName, String itemUnit, boolean weekly) {
        Integer newID = null;
        boolean newItem = true;
        try {
            Statement s = conn.createStatement();

            // make sure that item doesn't already exist!
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select id from item where name = '")
                    .append(itemName)
                    .append("';");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                System.out.println("Item already exists, skipping");
                newItem = false;
            }

            if(!newItem) {
                return null;
            }

            // insert the new item, since it doesn't exist already
            StringBuilder insertBuilder = new StringBuilder();
            insertBuilder
                .append("insert into item (name, weekly, unit) values ")
                .append("('")
                .append(itemName)
                .append("','")
                .append(itemUnit)
                .append("',")
                .append(weekly)
                .append(");");

            s.executeUpdate(insertBuilder.toString());

            // get the id of the freshly inserted item
            ResultSet newItemSet = s.executeQuery(builder.toString());
            while(newItemSet.next()) {
                newID = newItemSet.getInt(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        return newID;
    }

    /**
     * Returns an object full of some basic item info.
     *
     * @param itemID The item to query
     * @return An object full of juicy information, or a cold hard null if the item ID is null
     */
    public ItemResultObject getItemInfoByID(int itemID) {
        ItemResultObject result = null;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select name, unit from item where id = ")
                    .append(itemID)
                    .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                result = new ItemResultObject(itemID,
                                              set.getString(1),
                                              set.getString(2));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return result;
    }

    /**
     * get all of an item's purchasable quantities as objects
     * @param itemID The item to fetch purchasable quantities for.
     * @return An ArrayList full of purchasable quantities (potentially empty)
     */
    public ArrayList<PurchasableQuantity> getPurchasableQuantitiesForItem(int itemID) {
        ArrayList<PurchasableQuantity> pqList = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select id, qty, price from purchasable_quantity where item_id=")
                    .append(itemID)
                    .append(" and active='true';");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                pqList.add(new PurchasableQuantity(set.getInt(1),
                                                   set.getFloat(2),
                                                   set.getFloat(3)));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return pqList;
    }

    /**
     * Set a PQ to inactive.
     * @param pqID the PQ to update.
     */
    public void deactivatePurchasableQuantity(String pqID) {
        try {
            Statement s = conn.createStatement();
            StringBuilder auditBuilder = new StringBuilder();
            auditBuilder
                .append("update purchasable_quantity set active = 'false'")
                .append(" where id=")
                .append(pqID)
                .append(";");

            s.executeUpdate(auditBuilder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Inserts a new Purchasable Quantity for an item
     * @param itemID The item this PQ is for
     * @param price The price
     * @param qty What you get when you pay that price
     */
    public void createPQ(String itemID, float price, float qty) {
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                .append("insert into purchasable_quantity (price, qty, item_id) values")
                .append("(")
                .append(price)
                .append(",")
                .append(qty)
                .append(",")
                .append(itemID)
                .append(");");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Returns how much of something we currently have in stock.
     * @param itemID The item to check
     * @return The amount in the fridge (non-null)
     */
    public float getCurrentEstimatedQty(int itemID) {
        float qty = 0;

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select qty from inventory where item_id=")
                    .append(itemID)
                    .append(" order by time desc limit 1")
                    .append(";");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                qty = set.getFloat(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return qty;
    }

    /**
     * Add a hard inventory point to the database.
     * @param itemID The Item this inventory update is for
     * @param qty The new qty
     * @param userID the user making this change
     */
    public void updateItemQty(int itemID, Float qty, int userID) {
        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("insert into inventory ")
                    .append("(qty, point, restock, auto, time, user_id, item_id) values ")
                    .append("(")
                    .append(qty)
                    .append(",")
                    .append("true,") // point
                    .append("false,") // restock
                    .append("false,") // auto
                    .append("'" + timestampFormat.format(new Date()) + "',")
                    .append(userID)
                    .append(",")
                    .append(itemID)
                    .append(");");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Returns an array list of all of the items that need to have their quantities reduced.
     * @return An ArrayList (non-null) containing all of the items that need updating.
     */
    public ArrayList<InventoryItemResultObject> getAllItemsForInventoryUpdate() {
        ArrayList<InventoryItemResultObject> items = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("select i.id, s.actual from item i join sharing s on s.item_id=i.id;");

            ResultSet set = s.executeQuery(builder.toString());
            while(set.next()) {
                items.add(new InventoryItemResultObject(set.getInt(1), set.getFloat(2)));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return items;
    }

    /**
     * Add an inventory entry representing the inventory update.
     *
     * @param itemID The item to update inventory for
     * @param decrementQty The amount to decrement by
     */
    public void autoDecrementInventory(int itemID, float decrementQty) {
        float finalQty = getCurrentEstimatedQty(itemID) - decrementQty;

        if(finalQty < 0) {
            finalQty = 0;
            System.out.println("WARNING: item " + itemID + " is gonezo");
        }

        try {
            Statement s = conn.createStatement();
            StringBuilder builder = new StringBuilder();
            builder
                    .append("insert into inventory ")
                    .append("(qty, point, restock, auto, time, item_id) values ")
                    .append("(")
                    .append(finalQty)
                    .append(",")
                    .append("false,") // point
                    .append("false,") // restock
                    .append("true,") // auto
                    .append("'" + timestampFormat.format(new Date()) + "',")
                    .append(itemID)
                    .append(");");

            s.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * See if the almighty uberthread is doing anything at the moment.
     * @return True if it is, false otherwise.
     */
    public boolean isUberThreadRunning() {
        return uberThreadRunning;
    }

    /**
     * Lock out other uberthreads from trying to run for any reason
     * @return True if the calling thread can continue. False otherwise
     */
    public synchronized boolean getUberThreadLock() {
        if(uberThreadRunning) {
            return false;
        } else {
            uberThreadRunning = true;
            return true;
        }
    }

    /**
     * Release the uberthread, and allow one other to run (if there are any)
     */
    public synchronized void releaseUberThreadLock() {
        uberThreadRunning = false;
    }
}