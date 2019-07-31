package gpedrobuildout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Section object, a kind of Slack Block object
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
public class SlackBlockSection extends SlackBlock {
    static final String TEXT = "text";
    static final String ACCESSORY = "accessory";
    static final String FIELDS = "fields";
    
    static final int TEXT_MAX_LENGTH = 3000;
    static final int FIELD_TEXT_MAX_LENGTH = 2000;
    static final int MAX_FIELDS = 10;
    
    SlackMCOText text;
    SlackBlockElement accessory;
    
    SlackMCOText[] fields;
    int numFields;
    
    public SlackBlockSection(SlackMCOText text) {
        this.type = "section";
        
        if (!updateText(text)) {
            throw new IllegalArgumentException("SlackBlockSection: Illegal value for \"text\" field");
        }
        
        this.block_id = null;
        this.fields = new SlackMCOText[MAX_FIELDS];
        this.numFields = 0;
        this.accessory = null;
    }
    
    public boolean addField(SlackMCOText newField) {
        if (newField == null || this.numFields == MAX_FIELDS) {
            return false;
        }
        
        newField.cutToMaxLength(FIELD_TEXT_MAX_LENGTH);
        
        fields[numFields] = newField;
        numFields++;
        
        return true;
    }
    
    public void clearFields() {
        this.fields = new SlackMCOText[MAX_FIELDS];
        this.numFields = 0;
    }
    
    public boolean updateText(SlackMCOText newText) {
        if (newText == null || !newText.getType().equals("plain_text")) {
            return false;
        }
        
        newText.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = newText;
        
        return true;
    }
    
    public boolean updateAccessory(SlackBlockElement newAccessory) {
        if (newAccessory == null) {
            return false;
        }
        
        this.accessory = newAccessory;
        
        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.add(TEXT, this.text.toJson());
        
        if (this.block_id != null) {
            result.addProperty(BLOCK_ID, this.block_id);
        }
        
        if (this.fields != null && this.numFields != 0) {
            JsonArray jArr = new JsonArray();
            for (SlackMCOText field : fields) {
                if (field != null) {
                    jArr.add(field.toJson());
                }
            }
            result.add(FIELDS, jArr);
        }
        
        if (this.accessory != null) {
            result.add(ACCESSORY, this.accessory.toJson());
        }
        
        return result;
    }
    
}
