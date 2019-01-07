// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import java.util.HashMap;
import java.util.Map;

import fridginator.Constants;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Get the signin page
 * @author dcodeh 
 */
public class GetSignInRoute implements Route {
    
    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String RELEASE_ATTR = "release";
    public static final String VIEW_NAME = "signIn.ftl";

    private static final String TITLE = "Sign In";
    private static final String VERSION = Constants.VERSION_NUMBER;
    private static final String RELEASE = Constants.RELEASE_NAME;
    
    private TemplateEngine templateEngine;
    
    public GetSignInRoute(TemplateEngine te) {
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
           
            // TODO dcodeh redirect to main page
            SessionMessageHelper.addSessionMessage(session, "Welcome Back", MessageType.info);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }

}