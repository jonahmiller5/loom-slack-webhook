package gpedrobuildout;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Button object, which is a kind of Block Element object
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
public class SlackBlockElementButton implements SlackBlockElement {
    static final String TYPE = "type";
    static final String TEXT = "text";
    static final String ACTION_ID = "action_id";
    static final String URL = "url";
    static final String VALUE = "value";
    static final String STYLE = "style";
    static final String CONFIRM = "confirm";
    
    static final int TEXT_MAX_LENGTH = 75;
    static final int ACTION_ID_MAX_LENGTH = 255;
    static final int URL_MAX_LENGTH = 3000;
    static final int VALUE_MAX_LENGTH = 2000;
    
    String type, action_id;
    SlackMCOText text;
    String url, value, style;
    SlackMCOConfirm confirm;
    
    public SlackBlockElementButton(SlackMCOText text, String action_id) {
        this.type = "button";
        
        if (!updateText(text)) {
            throw new IllegalArgumentException("SlackBlockElementButton: Invalid value for \"text\" field. Cannot be null and "
                    + "must be of type \"plain_text\"");
        }
        
        if (action_id == null) {
            throw new IllegalArgumentException("SlackBlockElementButton: \"action_id\" field cannot be null");
        } else if (action_id.length() > ACTION_ID_MAX_LENGTH) {
            action_id = action_id.substring(0, ACTION_ID_MAX_LENGTH);
        }
        this.action_id = action_id;
        
        this.style = "default";
        
        this.url = null;
        this.value = null;
        this.confirm = null;
    }
    
    public boolean updateText(SlackMCOText newText) {
        if (newText == null || !newText.getType().equals("plain_text")) {
            return false;
        }
        
        newText.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = newText;
        
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
    
    public boolean updateStyle(String newStyle) {
        if (newStyle == null) {
            this.style = "default";
        } else if (newStyle.equals("primary")) {
            this.style = "primary";
        } else if (newStyle.equals("danger")) {
            this.style = "danger";
        } else {
            this.style = "default";
        }
        
        return true;
    }
    
    public boolean updateConfirm(SlackMCOConfirm newConfirm) {
        if (newConfirm == null) {
            return false;
        }
        
        if (newConfirm.getTitle() != null && 
            newConfirm.getText() != null &&
            newConfirm.getConfirm() != null &&
            newConfirm.getDeny() != null)  {
            this.confirm = newConfirm;
        }
        
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.add(TEXT, this.text.toJson());
        result.addProperty(ACTION_ID, this.action_id);
        
        if (this.url != null) {
            result.addProperty(URL, this.url);
        }
        
        if (this.value != null) {
            result.addProperty(VALUE, this.value);
        }
        
        if (this.style != null) {
            result.addProperty(STYLE, this.style);
        }
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
}
