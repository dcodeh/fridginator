// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.WebServer;
import spark.*;

import java.util.ArrayList;

/**
 * Updates the checked/unchecked status of a user's list
 * @author dcodeh 
 */
public class PostListRoute implements Route {

    private static final String SAVE_ACTION = "save";
    private static final String EDIT_ACTION = "edit";
    private static final String MENU_ACTION = "menu";

    private final TemplateEngine te;
    private DB db;

    public PostListRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        
        int userID = session.attribute(WebServer.SESSION_USER);

        // figure out where the user goes next
        if(request.queryParams(SAVE_ACTION) != null) {
            response.redirect(WebServer.GET_LIST);
        } else if(request.queryParams(EDIT_ACTION) != null) {
            response.redirect(WebServer.GET_EDIT_MISC);
        } else if(request.queryParams(MENU_ACTION) != null) {
            response.redirect(WebServer.MAIN_URL);
        }

        // update check status of misc items
        ArrayList<String> miscIDs = db.getMiscItemIDs(userID);
        for(String id : miscIDs) {
            db.setMiscCheckStatus(id, request.queryParams("m_" + id) != null);
        }

        ArrayList<String> sharedIDs = db.getSharedItemIDs(userID);
        for(String id : sharedIDs) {
            db.setSharedCheckStatus(id, request.queryParams("s_" + id) != null);
        }

        return "";
    }

}
