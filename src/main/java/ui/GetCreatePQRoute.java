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
 * Add a purchasable quantity to an item
 * @author dcodeh 
 */
public class GetCreatePQRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "newItem2.ftl";
    public static final String ITEM_ID = "itemID";

    public static final String FTL_ITEM_NAME = "itemName";
    public static final String FTL_UNIT = "unit";
    public static final String FTL_PQ_LIST = "PQList";
    public static final String FTL_ITEM_ID = "itemID";

    public static final String QTY_FIELD = "qty";
    public static final String PRICE_FIELD = "price";
    public static final String ADD_ACTION = "add";
    public static final String DONE_ACTION = "done";

    private static final String TITLE = "New Item";

    private TemplateEngine templateEngine;
    private DB db;

    public GetCreatePQRoute(DB db, TemplateEngine te) {
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

            String itemID = request.queryParams(ITEM_ID);
            Float price = null;
            Float qty = null;

            try {
                price = Float.valueOf(request.queryParams(PRICE_FIELD));
                qty = Float.valueOf(request.queryParams(QTY_FIELD));
            } catch(NumberFormatException nfe) {
                
            }

            if(price != null && qty != null) {
                db.createPQ(itemID, price, qty);
            }

            if(request.queryParams(ADD_ACTION) != null) {
                ItemResultObject item = db.getItemInfoByID(Integer.valueOf(itemID));

                if(item == null) {
                    halt(402, "Bad itemID");
                }

                vm.put(FTL_ITEM_NAME, item.getName());
                vm.put(FTL_UNIT, item.getUnit());
                vm.put(FTL_ITEM_ID, itemID);

                // get PQs for item id;
                // TODO dcodeh make this safer...
                ArrayList<PurchasableQuantity> pqList = db.getPurchasableQuantitiesForItem(Integer.valueOf(itemID));
                if(!pqList.isEmpty()) {
                    vm.put(FTL_PQ_LIST, pqList);
                }
                SessionMessageHelper.displaySessionMessages(session, vm);
                return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
            } else if(request.queryParams(DONE_ACTION) != null) {
                response.redirect(WebServer.ITEMS);
            }

        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
        }

        return "";
    }

}