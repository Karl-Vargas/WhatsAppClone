package karl.vargas.whatsappclone.Models;

public class MessageModel {
    String userID, message, messageId;
    Long timestamp;
    //    Constructor
    public MessageModel(String userID, String message, Long timestamp) {
        this.userID = userID;
        this.message = message;
        this.timestamp = timestamp;
    }

    //    Constructor
    public MessageModel(String userID, String message) {
        this.userID = userID;
        this.message = message;
    }



    // Firebase constructor
    public MessageModel() {

    }

    // Getter and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
