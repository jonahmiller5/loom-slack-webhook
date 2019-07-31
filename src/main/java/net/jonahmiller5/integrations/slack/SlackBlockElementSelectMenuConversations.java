package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

/** 
 * Class to represent Slack's Conversations List Menu Block Element
 * A kind of Menu Block Element
 * Part of an update to the GitHub repo at https://github.com/gpedro/slack-webhook
 * 
 * Find Slack's RESTful API documentation for Block Elements here: https://api.slack.com/reference/messaging/block-elements
 * 
 * @author Jonah Miller
 * @version 1.0
 * @since 2019-07-29
 * 
 * @notes Requires Gson to be configured to the build path
 *        Download the .jar file at https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.2/source-code
 *        
 *        Requires Lombok to be installed to your IDE and configured to the build path
 *        Download the .jar file at https://projectlombok.org/download and follow the installation instructions
 */

public class SlackBlockElementSelectMenuConversations extends SlackBlockElementSelectMenu {
    static final String INITIAL_CONVERSATION = "initial_conversation";

    String initial_conversation;
    
    public SlackBlockElementSelectMenuConversations(SlackMCOText placeholder, String action_id) {
        super("conversations_select", placeholder, action_id);

        this.initial_conversation = null;
    }

    public boolean updateInitialConversation(String newInitialConversation) {
        if (newInitialConversation == null || newInitialConversation.equals("")) {
            return false;
        }

        this.initial_conversation = newInitialConversation;

        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();

        result.addProperty(TYPE, this.type);
        result.add(PLACEHOLDER, this.placeholder.toJson());
        result.addProperty(ACTION_ID, this.action_id);

        if (this.initial_conversation != null) {
            result.addProperty(INITIAL_CONVERSATION, this.initial_conversation);
        }

        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }

        return result;
    }
}
