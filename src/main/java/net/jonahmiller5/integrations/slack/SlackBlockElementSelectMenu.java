package net.jonahmiller5.integrations.slack;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

/** 
 * Abstract class to represent Slack's Menu Block Element
 * Extended by each kind of Menu Block Element
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
public abstract class SlackBlockElementSelectMenu implements SlackBlockElement {
    static final String TYPE = "type";
    static final String PLACEHOLDER = "placeholder";
    static final String ACTION_ID = "action_id";
    static final String CONFIRM = "confirm";
    
    static final int MAX_ACTION_ID_LENGTH = 255;
    static final int MAX_PLACEHOLDER_TEXT_LENGTH = 150;
    static final int ACTION_ID_MAX_LENGTH = 255;
    static final String[] TYPES = {"static_select", "external_select", "users_select", "conversations_select", "channels_select"};
    
    String type, action_id;
    SlackMCOText placeholder;
    
    SlackMCOConfirm confirm;
    
    public SlackBlockElementSelectMenu(String type, SlackMCOText placeholder, String action_id) {
        if (!Arrays.asList(TYPES).contains(type)) {
            throw new IllegalArgumentException("SlackBlockElementSelectMenu: Illegal value for \"type\" field");
        }
        this.type = type;
        
        if (!updatePlaceholder(placeholder)) {
            throw new IllegalArgumentException("SlackBlockElementSelectMenu: Invalid value for \"placeholder\" field. Cannot be null and "
                    + "must be of type \"plain_text\"");
        }
        
        if (action_id == null) {
            throw new IllegalArgumentException("SlackBlockElementButton: \"action_id\" field cannot be null");
        } else if (action_id.length() > ACTION_ID_MAX_LENGTH) {
            action_id = action_id.substring(0, ACTION_ID_MAX_LENGTH);
        }
        this.action_id = action_id;
        
        this.confirm = null;
    }

    public boolean updatePlaceholder(SlackMCOText newPlaceholder) {
        if (newPlaceholder == null || !newPlaceholder.getType().equals("plain_text")) {
            return false;
        }
        
        newPlaceholder.cutToMaxLength(MAX_PLACEHOLDER_TEXT_LENGTH);
        this.placeholder = newPlaceholder;
        
        return true;
    }
    
    public boolean updateConfirm(SlackMCOConfirm newConfirm) {
        if (newConfirm == null) {
            return false;
        }
        
        this.confirm = newConfirm;
        
        return true;
    }
    
    @Override
    public abstract JsonObject toJson();
    
    public void testJson() {
        JsonObject j = this.toJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(j.toString());
        System.out.println(gson.toJson(je));
    }
}
