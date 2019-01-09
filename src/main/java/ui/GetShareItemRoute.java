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
 * Share an item with a user
 * @author dcodeh 
 */
public class GetShareItemRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String ITEM_ATTR= "itemName";
    public static final String UNIT_ATTR = "unit";
    public static final String VIEW_NAME = "shareItem.ftl";

    // parameters for this page
    private static final String ITEM_ID_PARAM = "itemID";

    // page constants
    private static final String TITLE = "Share Item";

    private TemplateEngine templateEngine;
    private DB db;

    public GetShareItemRoute(DB db, TemplateEngine te) {
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

            String itemID = request.queryParams(ITEM_ID_PARAM);

            if(itemID == null) {
                halt(422, "Missing itemID! Don't worry, it's not your fault.");
            }

            String itemName = db.getItemName(itemID);
            if(itemName == null) {
                halt(500, "Invalid itemID! If you see Cody, tell him 0x3484ff636cbb10"); // hah
            }

            String unit = db.getUnit(itemID);
            if(unit == null) {
                halt(500, "The impossible happened. If you see Cody, tell him to check the unit of item " + itemID);
            }

            vm.put(ITEM_ATTR, itemName);
            vm.put(UNIT_ATTR, unit);
            vm.put(ITEM_ID_PARAM, itemID); // pass it through to the next page

            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return "";
        }
    }

}