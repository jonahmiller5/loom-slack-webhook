package gpedrobuildout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent Slack's Overflow Block Element
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
public class SlackBlockElementOverflow implements SlackBlockElement {
    static final String TYPE = "type";
    static final String OPTIONS = "options";
    static final String ACTION_ID = "action_id";
    static final String CONFIRM = "confirm";
    
    static final int MAX_ACTION_ID_LENGTH = 255;
    static final int MAX_NUM_OPTIONS = 5;
    static final int MIN_NUM_OPTIONS = 2;
    
    String type, action_id;
    SlackMCOOption[] options;
    int numOptions;
    
    SlackMCOConfirm confirm;
    
//    public static void main(String[] args) {
//        SlackMCOText text = new SlackMCOText("plain_text", "text");
//        SlackMCOOption o1 = new SlackMCOOption(text, "value1");
//        SlackMCOOption o2 = new SlackMCOOption(text, "value2");
//        
//        SlackBlockElementOverflow ov = new SlackBlockElementOverflow("id", o1, o2);
//        ov.addOption(new SlackMCOOption(text, "value3"));
//        ov.updateOption1(o2);
//        ov.updateOption2(new SlackMCOOption(text, "value1"));
//        ov.updateConfirm(new SlackMCOConfirm(text, text, text, text));
//        
//        System.out.println(ov.getAction_id());
//        for (int i = 0; i < ov.getNumOptions(); i++) {
//            System.out.println(ov.getOptions()[i].getValue());
//        }
//        System.out.println(ov.getConfirm());
//        
//        JsonObject j = ov.toJson();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(j.toString());
//        System.out.println(gson.toJson(je));
//    }
    
    public SlackBlockElementOverflow(String action_id, SlackMCOOption option1, SlackMCOOption option2) {
        this.type = "overflow";
        
        if (action_id == null || action_id.equals("")) {
            throw new IllegalArgumentException("SlackBlockElementOverflow: Parameter \"action_id\" cannot be null");
        }
        
        if (action_id.length() > MAX_ACTION_ID_LENGTH) {
            action_id = action_id.substring(0, MAX_ACTION_ID_LENGTH);
        }
        this.action_id = action_id;
        
        if (option1 == null) {
            throw new IllegalArgumentException("SlackBlockElementOverflow: Argument \"option1\" cannot be null");
        }
        
        if (option2 == null) {
            throw new IllegalArgumentException("SlackBlockElementOverflow: Argument \"option2\" cannot be null");
        }
        
        options = new SlackMCOOption[MAX_NUM_OPTIONS];
        options[0] = option1;
        options[1] = option2;
        this.numOptions = 2;
        
        this.confirm = null;
    }
    
    public boolean updateOption1(SlackMCOOption newOption1) {
        if (newOption1 == null) {
            return false;
        }
        
        this.options[0] = newOption1;
        
        return true;
    }
    
    public boolean updateOption2(SlackMCOOption newOption2) {
        if (newOption2 == null) {
            return false;
        }
        
        this.options[1] = newOption2;
        
        return true;
    }
    
    public boolean addOption(SlackMCOOption newOption) {
        if (newOption == null || this.numOptions == MAX_NUM_OPTIONS) {
            return false;
        }
        
        this.options[this.numOptions] = newOption;
        numOptions++;
        
        return true;
    }
    
    public void clearOptions() {
        SlackMCOOption option1 = options[0];
        SlackMCOOption option2 = options[1];
        
        this.options = new SlackMCOOption[MAX_NUM_OPTIONS];
        this.options[0] = option1;
        this.options[1] = option2;
        this.numOptions = 2;
    }
    
    public boolean updateConfirm(SlackMCOConfirm newConfirm) {
        if (newConfirm == null) {
            return false;
        }
        
        this.confirm = newConfirm;
        
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.addProperty(ACTION_ID, this.action_id);
        
        JsonArray jArr = new JsonArray();
        for (SlackMCOOption option : options) {
            System.out.println("Option " + option);
            if (option != null) {
                jArr.add(option.toJson());
            }
        }
        result.add(OPTIONS, jArr);
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
}
