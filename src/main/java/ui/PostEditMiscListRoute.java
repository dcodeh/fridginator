// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import java.util.LinkedHashMap;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

/**
 * Updates the contents of a user's list
 * @author dcodeh 
 */
public class PostEditMiscListRoute implements Route {

    private static final String LIST_TEXT = "listText";

    private final TemplateEngine te;
    private DB db;

    public PostEditMiscListRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        
        int userID = session.attribute(WebServer.SESSION_USER);
        String listText = request.queryParams(LIST_TEXT);
        String[] lines = listText.split("\n");
        LinkedHashMap<String, Boolean> newList = new LinkedHashMap<>();
        
        for(String line : lines) {
            newList.put(line.trim(), db.getMiscItemChecked(userID, line.trim()));
        }
        
        db.wipeOutMiscList(userID);
        db.setMiscList(userID, newList);

        response.redirect(WebServer.GET_EDIT_MISC);
        return "";
    }

}
