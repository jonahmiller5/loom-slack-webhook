package net.jonahmiller5.integrations.slack;

import com.google.gson.JsonObject;

import lombok.Getter;

/** 
 * Class to represent the Slack Confirmaton Dialog object, which is a kind of Message Composition Object
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
public class SlackMCOConfirm implements SlackMCO {
    static final String TITLE = "title";
    static final String TEXT = "text";
    static final String CONFIRM = "confirm";
    static final String DENY = "deny";
    
    static final int TITLE_MAX_LENGTH = 100;
    static final int TEXT_MAX_LENGTH = 300;
    static final int CONFIRM_MAX_LENGTH = 30;
    static final int DENY_MAX_LENGTH = 30;
    
    SlackMCOText title, text, confirm, deny;
    
    public SlackMCOConfirm(SlackMCOText title, SlackMCOText text, SlackMCOText confirm, SlackMCOText deny) {
        if (!updateTitle(title)) {
            throw new IllegalArgumentException("SlackMCOConfirmationDialog: \"title\" field cannot be null");
        }
        
        if (!updateText(text)) {
            throw new IllegalArgumentException("SlackMCOConfirmationDialog: \"text\" field cannot be null");
        }
        
        if (!updateConfirm(confirm)) {
            throw new IllegalArgumentException("SlackMCOConfirmationDialog: \"confirm\" field cannot be null");
        }
        
        if (!updateDeny(deny)) {
            throw new IllegalArgumentException("SlackMCOConfirmationDialog: \"deny\" field cannot be null");
        }
    }
    
    public boolean updateTitle(SlackMCOText newTitle) {
        if (newTitle == null) {
            return false;
        }
        
        if (!newTitle.getType().equals("plain_text")) {
            newTitle.switchType();
        }
        
        newTitle.cutToMaxLength(TITLE_MAX_LENGTH);
        this.title = newTitle;
        
        return true;
    }
    
    public boolean updateText(SlackMCOText newText) {
        if (newText == null) {
            return false;
        }
        
        newText.cutToMaxLength(TEXT_MAX_LENGTH);
        this.text = newText;
        
        return true;
    }
    
    public boolean updateConfirm(SlackMCOText newConfirm) {
        if (newConfirm == null) {
            return false;
        }
        
        if (!newConfirm.getType().equals("plain_text")) {
            newConfirm.switchType();
        }
        
        newConfirm.cutToMaxLength(CONFIRM_MAX_LENGTH);
        this.confirm = newConfirm;
        
        return true;
    }
    
    public boolean updateDeny(SlackMCOText newDeny) {
        if (newDeny == null) {
            return false;
        }
        
        if (!newDeny.getType().equals("plain_text")) {
            newDeny.switchType();
        }
        
        newDeny.cutToMaxLength(DENY_MAX_LENGTH);
        this.deny = newDeny;
        
        return true;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        
        result.add(TITLE, this.title.toJson());
        result.add(TEXT, this.text.toJson());
        result.add(CONFIRM, this.confirm.toJson());
        result.add(DENY, this.deny.toJson());
        
        return result;
    }
}
