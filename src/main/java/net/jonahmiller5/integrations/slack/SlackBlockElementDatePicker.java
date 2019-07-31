package net.jonahmiller5.integrations.slack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

/** 
 * Class to represent Slack's Date Picker Block Element
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
public class SlackBlockElementDatePicker implements SlackBlockElement {
    static final String TYPE = "type";
    static final String PLACEHOLDER = "placeholder";
    static final String INITIAL_DATE = "initial_date";
    static final String ACTION_ID = "action_id";
    static final String CONFIRM = "confirm";
    
    static final int ACTION_ID_MAX_LENGTH = 255;
    static final int PLACEHOLDER_MAX_LENGTH = 150;
    
    String type, action_id;
    
    SlackMCOText placeholder;
    String initial_date;
    SlackMCOConfirm confirm;
    
    public SlackBlockElementDatePicker(String action_id) {
        this.type = "datepicker";
        
        if (action_id == null) {
            throw new IllegalArgumentException("SlackBlockElementDatePicker: Parameter \"action_id\" cannot be null");
        }
        if (action_id.length() > ACTION_ID_MAX_LENGTH) {
            action_id = action_id.substring(0, ACTION_ID_MAX_LENGTH);
        }
        this.action_id = action_id;
        
        this.placeholder = null;
        this.initial_date = null;
        this.confirm = null;
    }
    
    public boolean updatePlaceholder(SlackMCOText newPlaceholder) {
        if (newPlaceholder == null || !newPlaceholder.getType().equals("plain_text")) {
            return false;
        }
        
        newPlaceholder.cutToMaxLength(PLACEHOLDER_MAX_LENGTH);
        this.placeholder = newPlaceholder;
        
        return true;
    }
    
    public boolean updateInitialDate(String newInitialDate) {
        if (newInitialDate == null || newInitialDate.split("\\-").length != 3) {
            return false;
        }
        
        String[] pieces = newInitialDate.split("\\-");
        if (Integer.parseInt(pieces[0]) < 1900 || Integer.parseInt(pieces[0]) > 2019 ||
            Integer.parseInt(pieces[1]) < 1 || Integer.parseInt(pieces[1]) > 12 ||
            Integer.parseInt(pieces[2]) < 1 || Integer.parseInt(pieces[2]) > 31) {
            return false;
        }
        
        this.initial_date = newInitialDate;
        
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
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(ACTION_ID, this.action_id);
        
        if (this.placeholder != null) {
            result.add(PLACEHOLDER, this.placeholder.toJson());
        }
        
        if (this.initial_date != null) {
            result.addProperty(INITIAL_DATE, this.initial_date);
        }
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
    
    public void testJson() {
        JsonObject j = this.toJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(j.toString());
        System.out.println(gson.toJson(je));
    }
    
}
