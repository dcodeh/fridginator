// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

/**
 * Share an item with a user
 * @author dcodeh 
 */
public class PostShareItemRoute implements Route {

    private static final String USAGE_FIELD = "usage";
    private static final String SAVE_ACTION = "save";
    private static final String ABORT_ACTION = "abort";
    private static final String ITEM_ID_PARAM = "itemID";

    private final TemplateEngine te;
    private DB db;

    public PostShareItemRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        
        int userID = session.attribute(WebServer.SESSION_USER);
        String itemID = request.queryParams(ITEM_ID_PARAM);
        String usageFloat = request.queryParams(USAGE_FIELD);

        float usage = 0;
        try {
            usage = Float.valueOf(usageFloat);
        } catch (NumberFormatException nfe) {
            // yummy
        }

        if(request.queryParams(SAVE_ACTION) != null) {
            // poke the DB
            boolean success = db.shareItemWithUser(userID, itemID, usage);
            if(success) {
                SessionMessageHelper.addSessionMessage(session, "You're sharing a new Item", MessageType.info);
            } else {
                SessionMessageHelper.addSessionMessage(session, "Item Sharing Failed.", MessageType.error);
            }
        } else if(request.queryParams(ABORT_ACTION) != null) {
            SessionMessageHelper.addSessionMessage(session, "Aborted: Item was not shared.", MessageType.info);
        }

        response.redirect(WebServer.ITEMS);
        return "";
    }

}
