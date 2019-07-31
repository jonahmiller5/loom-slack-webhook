package net.jonahmiller5.integrations.slack;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Option object, which is a kind of Message Composition Object
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
public class SlackMCOOption implements SlackMCO {
    private static final String TEXT = "text";
    private static final String VALUE = "value";
    private static final String URL = "url";
    
    private static final int TEXT_MAX_LENGTH = 75;
    private static final int VALUE_MAX_LENGTH = 75;
    private static final int URL_MAX_LENGTH = 3000;
    
    SlackMCOText text;
    String value, url;
    
    public SlackMCOOption(SlackMCOText text, String value) {
        if (!text.getType().equals("plain_text")) {
            text.switchType();
        }
        
        text.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = text;
        
        updateValue(value);
        
        this.url = null;
    }
    
    public SlackMCOOption(SlackMCOText text, String value, String url) {
        if (!text.getType().equals("plain_text")) {
            text.switchType();
        }
        
        text.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = text;
        
        updateValue(value);
        
        updateUrl(url);
    }
    
    public boolean updateText(SlackMCOText newText) {
        if (!newText.getType().equals("plain_text")) {
            return false;
        }
        
        newText.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = newText;
        
        return true;
    }
    
    public boolean updateValue(String newValue) {
        if (newValue == null || newValue.equals("")) {
            return false;
        }
        
        if (newValue.length() > VALUE_MAX_LENGTH) {
            newValue = newValue.substring(0, VALUE_MAX_LENGTH);
        }
        this.value = newValue;
        
        return true;
    }
    
    public boolean updateUrl(String newUrl) {
        if (newUrl == null) {
            return false;
        }
        
        if (newUrl.length() > URL_MAX_LENGTH) {
            newUrl = newUrl.substring(0, URL_MAX_LENGTH);
        }
        try {
            URL validURL = new URL(newUrl);
            this.url = newUrl;
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.add(TEXT, this.text.toJson());
        result.addProperty(VALUE, this.value);
        if (this.url != null) {
            result.addProperty(URL, this.url);
        }
        
        return result;
    }
}
