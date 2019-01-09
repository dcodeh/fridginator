// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Unshare an item
 * @author dcodeh 
 */
public class GetUnshareItemRoute implements Route {

    // parameters for this page
    private static final String ITEM_ID_PARAM = "itemID";

    private TemplateEngine templateEngine;
    private DB db;

    public GetUnshareItemRoute(DB db, TemplateEngine te) {
        templateEngine = te;
        this.db = db;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is already signed in

            String itemID = request.queryParams(ITEM_ID_PARAM);

            if(itemID == null) {
                halt(422, "Missing itemID! Don't worry, it's not your fault.");
            }

            int userID = session.attribute(WebServer.SESSION_USER);
            
            boolean success = db.unshareItem(userID, itemID);
            
            if(success) {
                SessionMessageHelper.addSessionMessage(session, "Item successfully unshared", MessageType.info);
            } else {
                SessionMessageHelper.addSessionMessage(session, "Failed to unshare item.", MessageType.error);
            }
            
            response.redirect(WebServer.ITEMS);
            return "";
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return "";
        }
    }

}