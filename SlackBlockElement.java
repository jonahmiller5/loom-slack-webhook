package gpedrobuildout;

import com.google.gson.JsonObject;

/** 
 * Interface to abstractly represent the Block Element object, a class of 
 * JSON object patterns which are commonly used when composing a Slack message
 * Interface is implemented by every kind of Block Element object to allow for
 * abstraction when using them in Slack Block objects
 * 
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

public interface SlackBlockElement {

    JsonObject toJson();
    
    // Note: SlackSender will have to maintain a map of "action_id"s, since each SlackBlockElement 
    // has to have a universally unique "action_id" field
    
}
