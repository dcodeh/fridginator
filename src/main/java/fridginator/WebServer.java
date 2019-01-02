// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import spark.TemplateEngine;
import ui.GetSignInRoute;

import static spark.Spark.staticFiles;
import static spark.Spark.port;
import static spark.Spark.get;
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
    
    // TemplateEngine for rendering pages
    private final TemplateEngine templateEngine;
    
    /**
     * Create a new WebServer object
     * @param te The template engine to use to render FTL pages
     */
    public WebServer(TemplateEngine te) {
        templateEngine = te;
    }
    
    /**
     * Set up routes, and other important Web-related stuff
     */
    public void init() {
        staticFiles.location("/public");
        port(Constants.PORT);
        
        get(HOME_URL, new GetSignInRoute(templateEngine));
    }
    
    /**
     * Stop the WebServer
     */
    public void halt() {
        stop();
    }

}
