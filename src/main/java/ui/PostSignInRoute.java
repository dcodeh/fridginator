// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.WebServer;
import fridginator.SessionMessageHelper.MessageType;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Signs a user in
 * @author dcodeh 
 */
public class PostSignInRoute implements Route {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FAILURE_MESSAGE = "YOU SHALL NOT PASS! Unknown username or password.";
    private final TemplateEngine te;
    private DB db;
    
    public PostSignInRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        String enteredUsername = request.queryParams(USERNAME);
        String enteredPassword = request.queryParams(PASSWORD);
        
        String userPassword = db.queryUsername(enteredUsername);
        
        if(userPassword == null) {
            // no such user!
            SessionMessageHelper.addSessionMessage(session, FAILURE_MESSAGE, MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return "";
        } else {
            // welcome back, amigo
            if(userPassword.equals(enteredPassword)) {

            } else {
                // messed up your password!
                SessionMessageHelper.addSessionMessage(session, FAILURE_MESSAGE, MessageType.error);
                response.redirect(WebServer.HOME_URL);
                return "";
            }
        }

        return null;
    }

}
