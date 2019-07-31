package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

/** 
 * Class to represent the Divider object, a kind of Slack Block object
 * Part of an update to the GitHub repo at https://github.com/gpedro/slack-webhook
 * 
 * Find Slack's RESTful API documentation for Blocks here: https://api.slack.com/reference/messaging/blocks
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

public class SlackBlockDivider extends SlackBlock {

    public SlackBlockDivider() {
        this.type = "divider";
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        
        if (this.block_id != null) {
            result.addProperty(BLOCK_ID, this.block_id);
        }
        
        return result;
    }

}
