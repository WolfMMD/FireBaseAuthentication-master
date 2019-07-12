package examples.android.example.com.firebaseauthentication.data;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

public class Message {


    @PropertyName("body")
    private String messageBody;
    @PropertyName("sender")
    private String sender;
    @PropertyName("time")
    private Timestamp time;

    private int type;

    public Message(){

    }

    @PropertyName("body")
    public String getMessageBody() {
        return messageBody;
    }

    @PropertyName("body")
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
