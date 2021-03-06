// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package fridginator;

import spark.TemplateEngine;
import ui.*;

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
    
    // URL Patterns
    public static final String HOME_URL = "/";
    public static final String POST_SIGN_IN = "/signIn";
    public static final String MAIN_URL = "/main";
    public static final String SIGN_UP_URL = "/newUser";
    public static final String POST_SIGN_UP = "/signUp";
    public static final String GET_SIGN_OUT = "/signOut";
    public static final String GET_EDIT_MISC = "/editMisc";
    public static final String POST_UPDATE_MISC = "/updateMisc";
    public static final String GET_LIST = "/list";
    public static final String POST_LIST = "/updateList";
    public static final String ITEMS = "/items";
    public static final String SHARE_ITEM = "/shareItem";
    public static final String UNSHARE_ITEM = "/unshareItem";
    public static final String NEW_ITEM_1 = "/newItem1";
    public static final String NEW_ITEM_2 = "/newItem2";
    public static final String REMOVE_PQ = "/removePQ";
    public static final String CREATE_PQ ="/createPQ";
    public static final String UPDATE_INVENTORY = "/updateInventory";

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
        get(SIGN_UP_URL, new GetSignUpRoute(templateEngine));
        get(GET_SIGN_OUT, new GetSignOutRoute(templateEngine));
        get(GET_EDIT_MISC, new GetEditMiscListRoute(db, templateEngine));
        get(GET_LIST, new GetListRoute(db, templateEngine));
        get(ITEMS, new GetItemsRoute(db, templateEngine));
        get(SHARE_ITEM, new GetShareItemRoute(db, templateEngine));
        get(UNSHARE_ITEM, new GetUnshareItemRoute(db, templateEngine));
        get(NEW_ITEM_1, new GetNewItem1Route(db, templateEngine));
        get(NEW_ITEM_2, new GetNewItem2Route(db, templateEngine));
        get(REMOVE_PQ, new GetRemovePQRoute(db, templateEngine));
        get(CREATE_PQ, new GetCreatePQRoute(db, templateEngine));
        get(UPDATE_INVENTORY, new GetUpdateInventoryRoute(db, templateEngine));

        post(POST_SIGN_IN, new PostSignInRoute(db, templateEngine));
        post(POST_SIGN_UP, new PostSignUpRoute(db, templateEngine));
        post(POST_UPDATE_MISC, new PostEditMiscListRoute(db, templateEngine));
        post(POST_LIST, new PostListRoute(db, templateEngine));
        post(SHARE_ITEM, new PostShareItemRoute(db, templateEngine));
        post(UPDATE_INVENTORY, new PostUpdateInventoryRoute(db, templateEngine));
    }
    
    /**
     * Stop the WebServer
     */
    public void halt() {
        stop();
    }

}
