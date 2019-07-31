package net.jonahmiller5.integrations.slack;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Image object, which is a kind of Block Element object
 * (NOT TO BE CONFUSED WITH THE IMAGE BLOCK - THIS IS A BLOCK ELEMENT, NOT A BLOCK)
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
public class SlackBlockElementImage implements SlackBlockElement {
    static final String TYPE = "type";
    static final String IMAGE_URL = "image_url";
    static final String ALT_TEXT = "alt_text";
    
    String type, image_url, alt_text;
    
    public SlackBlockElementImage(String image_url, String alt_text) {
        this.type = "image";
        
        try {
            URL url = new URL(image_url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("SlackBlockElementImage: Image URL is bad");
        }
        this.image_url = image_url;
        
        this.alt_text = alt_text;
    }
    
    public boolean updateImageURL(String newImage_url) {
        if (newImage_url == null) {
            return false;
        }
        try {
            URL url = new URL(newImage_url);
            this.image_url = newImage_url;
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
    public boolean updateAltText(String newAlt_text) {
        if (newAlt_text == null) {
            return false;
        }
        this.alt_text = newAlt_text;
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(IMAGE_URL, this.image_url);
        result.addProperty(ALT_TEXT, this.alt_text);
        
        return result;
    }
}
