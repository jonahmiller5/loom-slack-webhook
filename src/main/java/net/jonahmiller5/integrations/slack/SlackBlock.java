package gpedrobuildout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

/** 
 * Abstract class to represent the Slack Block object
 * To be extended by every different kind of Slack Block object
 * 
 * Part of an update to the GitHub repo at https://github.com/gpedro/slack-webhook
 * 
 * Find Slack's RESTful API documentation for Blocks here: https://api.slack.com/reference/messaging/blocks
 * 
 * @author Jonah Miller
 * @version 1.0
 * @since 2019-07-31
 * 
 * @notes Requires Gson to be configured to the build path
 *        Download the .jar file at https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.2/source-code
 *        
 *        Requires Lombok to be installed to your IDE and configured to the build path
 *        Download the .jar file at https://projectlombok.org/download and follow the installation instructions
 */

@Getter
public abstract class SlackBlock {
    
    static final String TYPE = "type";
    static final String BLOCK_ID = "block_id";
    
    static final int BLOCK_ID_MAX_LENGTH = 255;
    
    String type;
    String block_id;
    
    public boolean updateBlockId(String newBlockId) {
        if (newBlockId == null || newBlockId.equals("")) {
            return false;
        }
        
        if (newBlockId.length() > BLOCK_ID_MAX_LENGTH) {
            newBlockId = newBlockId.substring(0, BLOCK_ID_MAX_LENGTH);
        }
        this.block_id = newBlockId;
        
        return true;
    }
    
    public abstract JsonObject toJson();
    
    public void testJson() {
        JsonObject j = this.toJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(j.toString());
        System.out.println(gson.toJson(je));
    }
}
