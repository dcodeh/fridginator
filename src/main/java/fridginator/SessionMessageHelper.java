/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package fridginator;

import java.util.Map;

import spark.Session;

/**
 * This class contains helpful methods pertaining to handling session messages
 * @author dcodeh
 */
public class SessionMessageHelper {
    
    public enum MessageType {
        info,  // helpful, but non critical user messages
        error; // something went terribly wrong!
    }
    
    /**
     * Call this method to add the session message attributes to the page.
     * If there is a message associated with the session, it will be added to the
     * HashMap, and will be removed from the session so it's only displayed once.
     * 
     * @param session The session to check for messages
     * @param vm The HashMap to add messages to (if any)
     */
    public static void displaySessionMessages(Session session, Map<String, Object> vm) {
        String sessionMessage = session.attribute(WebServer.SESSION_MESSAGE);
        String messageType = session.attribute(WebServer.MESSAGE_TYPE);
        if(sessionMessage != null) {
            vm.put(WebServer.SESSION_MESSAGE, sessionMessage);
            vm.put(WebServer.MESSAGE_TYPE, messageType);
            session.removeAttribute(WebServer.SESSION_MESSAGE);
            session.removeAttribute(WebServer.MESSAGE_TYPE);
        }
    }
    
    /**
     * Set the session message string and type.
     * This will overwrite the current Session message, if any.
     * 
     * @param session The session to add the message to
     * @param message I wonder...
     * @param type    The type of message, which determines the formatting.
     */
    public static void addSessionMessage(Session session, String message, MessageType type) {
        session.attribute(WebServer.SESSION_MESSAGE, message);
        session.attribute(WebServer.MESSAGE_TYPE, type.name());
    }

}
