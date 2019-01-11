// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

/**
 * Update the quantity in inventory for a certain item
 * @author dcodeh
 */
public class PostUpdateInventoryRoute implements Route {

    private static final String QTY_FIELD = "qty";
    private static final String SAVE_ACTION = "save";
    private static final String ABORT_ACTION = "abort";
    private static final String ITEM_ID_PARAM = "itemID";

    private final TemplateEngine te;
    private DB db;

    public PostUpdateInventoryRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        Float qty = null;
        Integer itemID = null;
        Integer userID = null;
        try {
            qty = Float.valueOf(request.queryParams(QTY_FIELD));
            itemID = Integer.valueOf(request.queryParams(ITEM_ID_PARAM));
            userID = session.attribute(WebServer.SESSION_USER);
        } catch(NumberFormatException nfe) {
            // eat it up
        }

        if(request.queryParams(SAVE_ACTION) != null) {
            // poke the DB
            if(qty != null && itemID != null && userID != null) {
                db.updateItemQty(itemID, qty, userID);
            } else {
                SessionMessageHelper.addSessionMessage(session, "Inventory could not be updated.", MessageType.info);
            }
        } else if(request.queryParams(ABORT_ACTION) != null) {
            SessionMessageHelper.addSessionMessage(session, "Aborted: Inventory not updated", MessageType.info);
        }

        response.redirect(WebServer.ITEMS);
        return "";
    }

}
