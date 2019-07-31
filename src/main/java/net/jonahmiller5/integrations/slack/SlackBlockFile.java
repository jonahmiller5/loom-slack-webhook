package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the File object, a kind of Slack Block object
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

@Getter
public class SlackBlockFile extends SlackBlock {
    static final String EXTERNAL_ID = "external_id";
    static final String SOURCE = "source";
    
    String external_id, source;
    
    public SlackBlockFile(String external_id) {
        this.type = "file";
        
        if (!updateExternalId(external_id)) {
            throw new IllegalArgumentException("SlackBlockFile: Illegal input for \"external_id\" parameter");
        }
        
        this.source = "remote";
    }
    
    public boolean updateExternalId(String newExternalId) {
        if (newExternalId == null || newExternalId.equals("")) {
            return false;
        }
        
        this.external_id = newExternalId;
        
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(EXTERNAL_ID, this.external_id);
        result.addProperty(SOURCE, this.source);
        
        if (this.block_id != null) {
            result.addProperty(BLOCK_ID, this.block_id);
        }
        
        return result;
    }

}
