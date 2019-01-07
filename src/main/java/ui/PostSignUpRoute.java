// Copyright (c) 2018 David Cody Burrows...see LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

/**
 * Signs a new user up
 * @author dcodeh 
 */
public class PostSignUpRoute implements Route {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PASSWORD2 = "password2";
    private static final String CODE = "code";

    private static final String FAILURE_MESSAGE = "YOU SHALL NOT PASS! Unknown username or password.";
    private final TemplateEngine te;
    private DB db;

    public PostSignUpRoute(DB db, TemplateEngine te ) {
        this.db = db;
        this.te = te;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        String enteredCode = request.queryParams(CODE);

        // check if the code is valid
        if(!db.getFridgeCode().equals(enteredCode)) {
            // get outta here
            SessionMessageHelper.addSessionMessage(session, "Invalid Fridge Code", MessageType.error);
            response.redirect(WebServer.SIGN_UP_URL);
            return "";
        }

        // check that the username is available
        String enteredUsername = request.queryParams(USERNAME);
        if(!db.usernameAvailable(enteredUsername)) {
            // pick a different one
            SessionMessageHelper.addSessionMessage(session,
                    "The username '" + enteredUsername + "' is already taken.", MessageType.error);
            response.redirect(WebServer.SIGN_UP_URL);
            return "";
        }

        // check that the passwords match
        String enteredPassword = request.queryParams(PASSWORD);
        String enteredPassword2 = request.queryParams(PASSWORD2);
        if(!enteredPassword.equals(enteredPassword2)) {
            SessionMessageHelper.addSessionMessage(session, "Your passwords do not match.", MessageType.error);
            response.redirect(WebServer.SIGN_UP_URL);
            return "";
        }

        // all looks good
        boolean success = db.createUser(enteredUsername, enteredPassword);

        if(success) {
            SessionMessageHelper.addSessionMessage(session,
                    "New user created.",
                    MessageType.info);
            System.out.println("WARNING: Created new user " + enteredUsername);
        } else {
            SessionMessageHelper.addSessionMessage(session,
                    "Error creating a new user! Please try again later.",
                    MessageType.error);
        }

        response.redirect(WebServer.HOME_URL);
        return "";
    }

}
