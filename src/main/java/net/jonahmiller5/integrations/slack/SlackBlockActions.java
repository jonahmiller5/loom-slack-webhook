package gpedrobuildout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Actions object, a kind of Slack Block object
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
public class SlackBlockActions extends SlackBlock {
    static final String ELEMENTS = "elements";
    
    static final int MAX_NUM_ELEMENTS = 5;
    
    SlackBlockElement[] elements;
    int numElements;
    
    public SlackBlockActions() {
        this.type = "actions";
        
        this.elements = new SlackBlockElement[MAX_NUM_ELEMENTS];
        this.numElements = 0;
    }
    
    public boolean addElement(SlackBlockElement newElement) {
        if (newElement == null || this.numElements == MAX_NUM_ELEMENTS ||
                (!(newElement instanceof SlackBlockElementButton) && 
                 !(newElement instanceof SlackBlockElementSelectMenu) && 
                 !(newElement instanceof SlackBlockElementOverflow) && 
                 !(newElement instanceof SlackBlockElementDatePicker))) {
            return false;
        }
        
        this.elements[this.numElements] = newElement;
        numElements++;
        
        return true;
    }
    
    public void clearElements() {
        this.elements = new SlackBlockElement[MAX_NUM_ELEMENTS];
        this.numElements = 0;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        
        JsonArray jArr = new JsonArray();
        for (SlackBlockElement element : this.elements) {
            if (element != null) {
                jArr.add(element.toJson());
            }
        }
        result.add(ELEMENTS, jArr);
        
        if (this.block_id != null) {
            result.addProperty(BLOCK_ID, this.block_id);
        }
        
        return result;
    }
}
