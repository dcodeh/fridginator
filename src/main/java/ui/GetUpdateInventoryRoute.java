// Copyright (c) 2018 David Cody Burrows...See LICENSE file for details
package ui;

import fridginator.DB;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import fridginator.WebServer;
import model.ItemResultObject;
import model.PurchasableQuantity;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Get the inventory update page
 * @author dcodeh
 */
public class GetUpdateInventoryRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "updateInventory.ftl";

    public static final String ITEM_ID = "itemID";
    public static final String EST_QTY = "estQty";
    public static final String UNIT = "unit";
    public static final String ITEM_NAME = "itemName";

    private static final String TITLE = "Update Inventory";

    private TemplateEngine templateEngine;
    private DB db;

    public GetUpdateInventoryRoute(DB db, TemplateEngine te) {
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

            Integer itemID = Integer.valueOf(request.queryParams(ITEM_ID));
            if(itemID == null) {
                halt(402, "Missing itemID");
            }

            ItemResultObject item = db.getItemInfoByID(itemID);

            if(item == null) {
                halt(402, "Bad itemID");
            }

            vm.put(ITEM_ID, item.getID());
            vm.put(UNIT, item.getUnit());
            vm.put(ITEM_NAME, item.getName());


            // get the qty of this item currently in the fridge
            float estQty = db.getCurrentEstimatedQty(itemID);
            vm.put(EST_QTY, estQty);

            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
        }

        return "";
    }

}