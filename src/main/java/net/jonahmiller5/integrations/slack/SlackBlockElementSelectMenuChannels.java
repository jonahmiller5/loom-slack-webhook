package gpedrobuildout;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent Slack's Channels List Menu Block Element
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
public class SlackBlockElementSelectMenuChannels extends SlackBlockElementSelectMenu {
    static final String INITIAL_CHANNEL = "initial_channel";

    String initial_channel;

    public SlackBlockElementSelectMenuChannels(SlackMCOText placeholder, String action_id) {
        super("channels_select", placeholder, action_id);

        this.initial_channel = null;
    }

    public boolean updateInitialChannel(String newInitialChannel) {
        if (newInitialChannel == null || newInitialChannel.equals("")) {
            return false;
        }

        this.initial_channel = newInitialChannel;

        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();

        result.addProperty(TYPE, this.type);
        result.add(PLACEHOLDER, this.placeholder.toJson());
        result.addProperty(ACTION_ID, this.action_id);

        if (this.initial_channel != null) {
            result.addProperty(INITIAL_CHANNEL, this.initial_channel);
        }

        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }

        return result;
    }
}
