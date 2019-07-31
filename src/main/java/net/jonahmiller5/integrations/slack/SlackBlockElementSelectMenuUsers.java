package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent Slack's User List Menu Block Element
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

@Getter
public class SlackBlockElementSelectMenuUsers extends SlackBlockElementSelectMenu {

    static final String INITIAL_USER = "initial_user";
    
    String initial_user;
    
    public SlackBlockElementSelectMenuUsers(SlackMCOText placeholder, String action_id) {
        super("users_select", placeholder, action_id);
        
        this.initial_user = null;
    }
    
    public boolean updateInitialUser(String newInitialUser) {
        if (newInitialUser == null || newInitialUser.equals("")) {
            return false;
        }
        
        this.initial_user = newInitialUser;
        
        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.add(PLACEHOLDER, this.placeholder.toJson());
        result.addProperty(ACTION_ID, this.action_id);
        
        if (this.initial_user != null) {
            result.addProperty(INITIAL_USER, this.initial_user);
        }
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
}
