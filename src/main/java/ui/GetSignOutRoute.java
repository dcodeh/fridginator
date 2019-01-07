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
 * Sign a user out, then redirect to the login page
 * @author dcodeh 
 */
public class GetSignOutRoute implements Route {

    private TemplateEngine templateEngine;

    public GetSignOutRoute(TemplateEngine te) {
        templateEngine = te;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        // populate the fields in the ftl template
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is already signed in
            session.removeAttribute(WebServer.SESSION_USER);
            SessionMessageHelper.addSessionMessage(session, "You've been logged out.", MessageType.info);
            response.redirect(WebServer.HOME_URL);
        } else {
            SessionMessageHelper.addSessionMessage(session, "Something is fishy here...", MessageType.error);
            System.out.println("ERROR: Someone who wasn't logged in tried to log out!");
            response.redirect(WebServer.HOME_URL);
        }

        return "";
    }

}