// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.Constants;
import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import model.SharedItem;
import model.UnsharedItem;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Get the main menu page
 * @author dcodeh 
 */
public class GetItemsRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String RELEASE_ATTR = "release";
    public static final String VIEW_NAME = "items.ftl";

    private static final String TITLE = "Items";
    private static final String VERSION = Constants.VERSION_NUMBER;
    private static final String RELEASE = Constants.RELEASE_NAME;
    private static final String SHARED_ITEMS = "sharedItems";
    private static final String UNSHARED_ITEMS = "unsharedItems";

    private TemplateEngine templateEngine;
    private DB db;

    public GetItemsRoute(DB db, TemplateEngine te) {
        templateEngine = te;
        this.db = db;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();
        
        // populate the fields in the ftl template
        vm.put(TITLE_ATTR, TITLE);
        vm.put(VERSION_ATTR, VERSION);
        vm.put(RELEASE_ATTR, RELEASE);
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is already signed in

            int userID = session.attribute(WebServer.SESSION_USER);

            ArrayList<SharedItem> sharedItems = db.getSharedItems(userID);
            if(!sharedItems.isEmpty()) {
                vm.put(SHARED_ITEMS, sharedItems);
            }

            ArrayList<UnsharedItem> unsharedItems = db.getUnsharedItems(userID);
            if(!unsharedItems.isEmpty()) {
                vm.put(UNSHARED_ITEMS, unsharedItems);
            }

            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return "";
        }
    }

}