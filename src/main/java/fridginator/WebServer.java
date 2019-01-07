// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import spark.TemplateEngine;
import ui.GetMainRoute;
import ui.GetSignInRoute;
import ui.PostSignInRoute;

import static spark.Spark.staticFiles;
import static spark.Spark.port;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.stop;

/**
 * Set up the routes for the webserver
 * @author dcodeh
 */
public class WebServer {
    
    // Session attributes
    public static final String SESSION_USER = "user";
    public static final String SESSION_MESSAGE = "message";
    public static final String MESSAGE_TYPE = "messageType";
    
    // GET URL Patterns
    public static final String HOME_URL = "/";
    public static final String POST_SIGN_IN = "/signIn";
    public static final String MAIN_URL = "/main";

    // TemplateEngine for rendering pages
    private final TemplateEngine templateEngine;
    private final DB db;
    
    /**
     * Create a new WebServer object
     * @param db The DB helper object to use
     * @param te The template engine to use to render FTL pages
     */
    public WebServer(DB db, TemplateEngine te) {
        templateEngine = te;
        this.db = db;
    }
    
    /**
     * Set up routes, and other important Web-related stuff
     */
    public void init() {
        staticFiles.location("/public");
        port(Constants.PORT);
        
        get(HOME_URL, new GetSignInRoute(templateEngine));
        get(MAIN_URL, new GetMainRoute(db, templateEngine));

        post(POST_SIGN_IN, new PostSignInRoute(db, templateEngine));
    }
    
    /**
     * Stop the WebServer
     */
    public void halt() {
        stop();
    }

}
