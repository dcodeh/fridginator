package ui;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

public class GetSignInRoute implements Route {
    
    private static String VIEW_NAME = "hellow.ftl";
    
    private TemplateEngine templateEngine;
    
    public GetSignInRoute(TemplateEngine te) {
        templateEngine = te;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
