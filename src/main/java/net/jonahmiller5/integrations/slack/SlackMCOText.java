package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Text object, which is a kind of Message Composition Object
 * Part of an update to the GitHub repo at https://github.com/gpedro/slack-webhook
 * 
 * Find Slack's RESTful API documentation for Message Composition Elements here: https://api.slack.com/reference/messaging/composition-objects
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
public class SlackMCOText implements SlackMCO {
    static final String TYPE = "type";
    static final String TEXT = "text";
    static final String EMOJI = "emoji";
    static final String VERBATIM = "verbatim";
    
    String type;
    String text;
    boolean emoji;
    boolean verbatim;
    
    SlackMCOText(String type, String text) {
        if ((type.equals("plain_text") || type.equals("mrkdwn"))) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("SlackMCOText: Invalid value for \"type\" field");
        }
        
        this.text = text;
        this.emoji = false;
        this.verbatim = false;
    }

    SlackMCOText(String type, String text, boolean emoji, boolean verbatim) {
        if ((type.equals("mrkdwn") || type.equals("plain_text"))) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type");
        }

        this.text = text;
        
        if (emoji && type.equals("plain_text")) {
            this.emoji = true;
        }

        if (verbatim && type.equals("mrkdwn")) {
            this.verbatim = true;
        }
    }
    
    public void cutToMaxLength(int max) {
        if (this.text.length() > max) {
            this.text = this.text.substring(0, max);
        }
    }
    
    public void switchType() {
        if (this.type.equals("mrkdwn")) {
            this.type = "plain_text";
            this.disableVerbatim();
        } else {
            this.type = "mrkdwn";
            this.disableEmoji();
        }
    }
    
    public boolean updateText(String newText) {
        if (newText == null) {
            return false;
        }
        
        this.text = newText;
        return true;
    }
    
    public boolean enableEmoji() {
        if (!getType().equals("plain_text")) {
            return false;
        }
        this.emoji = true;
        return true;
    }
    
    public boolean disableEmoji() {
        this.emoji = false;
        return true;
    }
    
    public boolean enableVerbatim() {
        if (!getType().equals("mrkdwn")) {
            return false;
        }
        this.verbatim = true;
        return true;
    }
    
    public boolean disableVerbatim() {
        this.verbatim = false;
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(TEXT, this.text);
        
        if (this.emoji) {
            result.addProperty(EMOJI, this.emoji);
        }
        
        if (this.verbatim) {
            result.addProperty(VERBATIM, this.verbatim);
        }
        
        return result;
    }
}
