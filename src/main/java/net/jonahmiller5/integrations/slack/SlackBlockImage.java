package net.jonahmiller5.integrations.slack;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Image object, a kind of Slack Block object
 * (NOT TO BE CONFUSED WITH THE IMAGE BLOCK ELEMENT OBJECT - THIS IS A BLOCK OBJECT)
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
public class SlackBlockImage extends SlackBlock {

    static final String IMAGE_URL = "image_url";
    static final String ALT_TEXT = "alt_text";
    static final String TITLE = "title";
    
    static final int URL_MAX_LENGTH = 3000;
    static final int ALT_TEXT_MAX_LENGTH = 2000;
    static final int TITLE_MAX_LENGTH = 2000;
    
    String image_url, alt_text;
    
    SlackMCOText title;
    
    public SlackBlockImage(String image_url, String alt_text) {
        this.type = "image";
        
        if (!updateImageUrl(image_url)) {
            throw new IllegalArgumentException("SlackBlockImage: Invalid value for \"image_url\" parameter");
        }
        this.image_url = image_url;
        
        if (!updateAltText(alt_text)) {
            throw new IllegalArgumentException("SlackBlockImage: Invalid value for \"alt_text\" parameter");
        }
        
        this.title = null;
    }
    
    public boolean updateImageUrl(String newImageUrl) {
        if (newImageUrl == null) {
            return false;
        }
        
        if (newImageUrl.length() > URL_MAX_LENGTH) {
            newImageUrl = newImageUrl.substring(0, URL_MAX_LENGTH);
        }
        
        try {
            URL checker = new URL(newImageUrl);
        } catch (MalformedURLException e) {
            return false;
        }
        
        this.image_url = newImageUrl;
        
        return true;
    }
    
    public boolean updateAltText(String newAltText) {
        if (newAltText == null) {
            return false;
        }
        
        if (newAltText.length() > ALT_TEXT_MAX_LENGTH) {
            newAltText = newAltText.substring(0, ALT_TEXT_MAX_LENGTH);
        }
        
        this.alt_text = newAltText;
        
        return true;
    }
    
    public boolean updateTitle(SlackMCOText newTitle) {
        if (newTitle == null || !newTitle.getType().equals("plain_text")) {
            return false;
        }
        
        this.title = newTitle;
        
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(IMAGE_URL, this.image_url);
        result.addProperty(ALT_TEXT, this.alt_text);
        
        if (this.title != null) {
            result.add(TITLE, this.title.toJson());
        }
        
        if (this.block_id != null) {
            result.addProperty(BLOCK_ID, this.block_id);
        }
        
        return result;
    }

}
