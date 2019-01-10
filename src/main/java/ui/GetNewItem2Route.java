// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Insert a new item, and move on to the next step of the item creation process
 * @author dcodeh
 */
public class GetNewItem2Route implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "newItem2.ftl";

    // from newItem1
    public static final String NEXT_ACTION = "next";
    public static final String ABORT_ACTION = "abort";
    public static final String NAME_ATTR = "name";
    public static final String UNIT_ATTR = "unit";
    public static final String WEEKLY_ATTR = "weekly";

    // ftl template goodies
    public static final String FTL_ITEM_NAME = "itemName";
    public static final String FTL_UNIT = "unit";

    // page constants
    private static final String TITLE = "New Item";

    private TemplateEngine templateEngine;
    private DB db;

    public GetNewItem2Route(DB db, TemplateEngine te) {
        templateEngine = te;
        this.db = db;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();
        
        // populate the fields in the ftl template
        vm.put(TITLE_ATTR, TITLE);

        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is already signed in
            if(request.queryParams(NEXT_ACTION) != null) {
                String itemName = request.queryParams(NAME_ATTR);
                String itemUnit = request.queryParams(UNIT_ATTR);
                boolean weekly = Boolean.valueOf(request.queryParams(WEEKLY_ATTR));

                Integer itemID = db.createItem(itemName, itemUnit, weekly);
                if(itemID != null) {
                    vm.put(FTL_ITEM_NAME, itemName);
                    vm.put(FTL_UNIT, itemUnit);

                    SessionMessageHelper.addSessionMessage(session, "Created new Item with ID " + itemID, MessageType.info);
                    SessionMessageHelper.displaySessionMessages(session, vm);
                    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
                } else {
                    SessionMessageHelper.addSessionMessage(session, "Item Creation Failed.", MessageType.error);
                    response.redirect(WebServer.ITEMS);
                }
            } else if(request.queryParams(ABORT_ACTION) != null) {
                SessionMessageHelper.addSessionMessage(session, "Item Creation Aborted.", MessageType.info);
                response.redirect(WebServer.ITEMS);
            }
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
        }

        return "";
    }

}