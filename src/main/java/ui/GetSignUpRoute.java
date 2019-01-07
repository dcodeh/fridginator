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
 * Get the sign up page
 * @author dcodeh 
 */
public class GetSignUpRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String RELEASE_ATTR = "release";
    public static final String VIEW_NAME = "signUp.ftl";

    private static final String TITLE = "Menu";
    private static final String VERSION = Constants.VERSION_NUMBER;
    private static final String RELEASE = Constants.RELEASE_NAME;

    private TemplateEngine templateEngine;

    public GetSignUpRoute(TemplateEngine te) {
        templateEngine = te;
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
            SessionMessageHelper.addSessionMessage(session, "You must sign out before you can create a new user!", MessageType.error);
            response.redirect(WebServer.MAIN_URL);
        } else {
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        return "";
    }

}