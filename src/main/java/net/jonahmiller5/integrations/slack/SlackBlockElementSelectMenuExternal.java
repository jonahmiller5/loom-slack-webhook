package gpedrobuildout;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent Slack's External Data Source Menu Block Element
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
public class SlackBlockElementSelectMenuExternal extends SlackBlockElementSelectMenu {
    static final String INITIAL_OPTION = "initial_option";
    static final String MIN_QUERY_LENGTH = "min_query_length";
    
    SlackMCOOption initial_option;
    int min_query_length;
    
    public SlackBlockElementSelectMenuExternal(SlackMCOText placeholder, String action_id) {
        super("external_select", placeholder, action_id);
        this.initial_option = null;
        this.min_query_length = 0;
    }

    public boolean updateInitialOption(SlackMCOOption newInitialOption) {
        if (newInitialOption == null) {
            return false;
        }
        
        this.initial_option = newInitialOption;
        return true;
    }
    
    public void updateMinQueryLength(int newMinQueryLength) {
        this.min_query_length = Math.max(0, newMinQueryLength);
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.add(PLACEHOLDER, this.placeholder.toJson());
        result.addProperty(ACTION_ID, this.action_id);
        
        if (this.initial_option != null) {
            result.add(INITIAL_OPTION, this.initial_option.toJson());
        }
        
        if (this.min_query_length > 0) {
            result.addProperty(MIN_QUERY_LENGTH, this.min_query_length);
        }
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
}
