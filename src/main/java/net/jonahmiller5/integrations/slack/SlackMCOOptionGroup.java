package gpedrobuildout;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Option Group object, which is a kind of Message Composition Object
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
public class SlackMCOOptionGroup implements SlackMCO {
    static final String LABEL = "label";
    static final String OPTIONS = "options";
    
    static final int MAX_OPTIONS = 100;
    static final int LABEL_MAX_LENGTH = 75;
    
    SlackMCOText label;
    SlackMCOOption[] options;
    int numOptions;
    
    public SlackMCOOptionGroup(SlackMCOText label) {
        if (!label.getType().equals("plain_text")) {
            label.switchType();
        }
        label.cutToMaxLength(LABEL_MAX_LENGTH);
        this.label = label;
        
        options = new SlackMCOOption[MAX_OPTIONS];
    }
    
    public boolean addOption(SlackMCOOption newOption) {
        if (this.numOptions == MAX_OPTIONS) {
            return false;
        }
        
        this.options[numOptions] = newOption;
        numOptions++;
        
        return true;
    }
    
    public boolean updateLabel(SlackMCOText newLabel) {
        if (newLabel == null || !newLabel.getType().equals("plain_text")) {
            return false;
        }
        
        newLabel.cutToMaxLength(LABEL_MAX_LENGTH);
        this.label = newLabel;
        
        return true;
    }
    
    public void clearOptions() {
        this.options = new SlackMCOOption[MAX_OPTIONS];
        this.numOptions = 0;
    }
    
    private JsonArray prepareOptions() {
        JsonArray result = new JsonArray();
        
        for (SlackMCOOption option : options) {
            result.add(option.toJson());
        }
        
        return result;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.add(LABEL, this.label.toJson());
        result.add(OPTIONS, this.prepareOptions());
        
        return result;
    }
}
