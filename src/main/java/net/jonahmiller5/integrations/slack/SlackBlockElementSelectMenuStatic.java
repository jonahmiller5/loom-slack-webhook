package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import lombok.Getter;

/** 
 * Class to represent Slack's Static Menu Block Element
 * A kind of Menu Block Element
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
public class SlackBlockElementSelectMenuStatic extends SlackBlockElementSelectMenu {
    static final String OPTIONS = "options";
    static final String OPTION_GROUPS = "option_groups";
    static final String INITIAL_OPTION = "initial_option";
    
    static final int MAX_NUM_OPTIONS = 100;
    static final int MAX_NUM_OPTION_GROUPS = 100;
    
    SlackMCOOption[] options;
    int numOptions;
    
    SlackMCOConfirm confirm;
    SlackMCOOptionGroup[] option_groups;
    int numOptionGroups;
    SlackMCOOption initial_option;
    
    public SlackBlockElementSelectMenuStatic(SlackMCOText placeholder, String action_id) {
        super("static_select", placeholder, action_id);
        
        this.options = new SlackMCOOption[MAX_NUM_OPTIONS];
        this.numOptions = 0;
        this.numOptionGroups = 0;
    }
    
    public boolean addOption(SlackMCOOption newOption) {
        if (numOptions == MAX_NUM_OPTIONS || newOption == null) {
            return false;
        }
        
        this.option_groups = null;
        this.numOptionGroups = 0;
        
        if (this.options == null) {
            this.options = new SlackMCOOption[MAX_NUM_OPTIONS];
            this.numOptions = 0;
        }
        this.options[numOptions] = newOption;
        numOptions++;
        return true;
    }
    
    public boolean clearOptions() {
        if (this.options == null || this.option_groups != null) {
            return false;
        }
        this.options = new SlackMCOOption[MAX_NUM_OPTIONS];
        this.numOptions = 0;
        return true;
    }
    
    public boolean addOptionGroup(SlackMCOOptionGroup newOptionGroup) {
        if (numOptionGroups == MAX_NUM_OPTION_GROUPS || newOptionGroup == null) {
            return false;
        }
        
        this.options = null;
        this.numOptions = 0;
        
        if (this.option_groups == null) {
            this.option_groups = new SlackMCOOptionGroup[MAX_NUM_OPTION_GROUPS];
            this.numOptionGroups = 0;
        }
        this.option_groups[numOptionGroups] = newOptionGroup;
        numOptionGroups++;
        return true;
    }

    public boolean clearOptionGroups() {
        if (this.option_groups == null || this.options != null) {
            return false;
        }
        this.option_groups = new SlackMCOOptionGroup[MAX_NUM_OPTION_GROUPS];
        this.numOptionGroups = 0;
        return true;
    }
    
    public boolean updateInitialOption(SlackMCOOption newInitialOption) {
        if (newInitialOption == null) {
            return false;
        }
        
        this.initial_option = newInitialOption;
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.addProperty(TYPE, this.type);
        result.add(PLACEHOLDER, this.placeholder.toJson());
        result.addProperty(ACTION_ID, this.action_id);
        
        if (this.options != null && this.option_groups == null) {
            JsonArray jArr = new JsonArray();
            for (SlackMCOOption option : this.options) {
                if (option != null) {
                    jArr.add(option.toJson());
                }
            }
            result.add(OPTIONS, jArr);
        }
        
        if (this.option_groups != null && this.options == null) {
            JsonArray jArr = new JsonArray();
            for (SlackMCOOptionGroup option_group : this.option_groups) {
                if (option_group != null) {
                    jArr.add(option_group.toJson());
                }
            }
            result.add(OPTION_GROUPS, jArr);
        }
        
        if (this.initial_option != null) {
            result.add(INITIAL_OPTION, initial_option.toJson());
        }
        
        if (this.confirm != null) {
            result.add(CONFIRM, this.confirm.toJson());
        }
        
        return result;
    }
}
