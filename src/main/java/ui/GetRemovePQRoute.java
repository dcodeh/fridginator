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
 * Remove a purchasable quantity from an item
 * @author dcodeh 
 */
public class GetRemovePQRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "newItem2.ftl";
    public static final String PQ_ID = "pqID";
    public static final String ITEM_ID = "itemID";

    public static final String FTL_ITEM_NAME = "itemName";
    public static final String FTL_UNIT = "unit";
    public static final String FTL_PQ_LIST = "PQList";
    public static final String FTL_ITEM_ID = "itemID";

    private static final String TITLE = "New Item";

    private TemplateEngine templateEngine;
    private DB db;

    public GetRemovePQRoute(DB db, TemplateEngine te) {
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

            String pqID = request.queryParams(PQ_ID);
            String itemID = request.queryParams(ITEM_ID);

            if(pqID != null) {
                db.deactivatePurchasableQuantity(pqID);
            } else {
                SessionMessageHelper.addSessionMessage(session, "Purchasable Quantity couldn't be deleted.", MessageType.error);
            }

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
        } else {
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
        }

        return "";
    }

}