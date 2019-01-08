// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.Constants;
import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import model.MiscItem;
import model.SharedItem;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Display the list page
 * @author dcodeh 
 */
public class GetListRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String RELEASE_ATTR = "release";
    public static final String SHARED_LIST = "sharedItems";
    public static final String MISC_LIST = "miscItems";
    public static final String VIEW_NAME = "list.ftl";

    private static final String TITLE = "My List";
    private static final String VERSION = Constants.VERSION_NUMBER;
    private static final String RELEASE = Constants.RELEASE_NAME;

    private TemplateEngine templateEngine;
    private DB db;

    public GetListRoute(DB db, TemplateEngine te) {
        templateEngine = te;
        this.db = db;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();

        // to prevent browser from caching this page.
        response.header("Cache-Control", "no-cache, no-store, must-revalidate");
        response.header("Pragma", "no-cache");
        response.header("Expires", "0");

        // populate the fields in the ftl template
        vm.put(TITLE_ATTR, TITLE);
        vm.put(VERSION_ATTR, VERSION);
        vm.put(RELEASE_ATTR, RELEASE);
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is already signed in

            int userID = session.attribute(WebServer.SESSION_USER);

            // get the user's misc list
            ArrayList<MiscItem> miscItems = db.getMiscItemsList(userID);
            if(!miscItems.isEmpty()) {
                vm.put(MISC_LIST, miscItems);
            }

            // get the user's shared list
            ArrayList<SharedItem> sharedItems = db.getSharedItemsList(userID);
            if(!sharedItems.isEmpty()) {
                vm.put(SHARED_LIST, sharedItems);
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