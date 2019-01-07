// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.Constants;
import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Load the edit misc list page
 * @author dcodeh 
 */
public class GetEditMiscListRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String RELEASE_ATTR = "release";
    public static final String VIEW_NAME = "edit.ftl";
    public static final String LIST_CONTENTS = "listContents";

    private static final String TITLE = "Edit Misc. List";
    private static final String VERSION = Constants.VERSION_NUMBER;
    private static final String RELEASE = Constants.RELEASE_NAME;

    private TemplateEngine templateEngine;
    private DB db;

    public GetEditMiscListRoute(DB db, TemplateEngine te) {
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
            String contents = db.getMiscListContents(userID);
            vm.put(LIST_CONTENTS, contents);
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return "";
        }
    }

}