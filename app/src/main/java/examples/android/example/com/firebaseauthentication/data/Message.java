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
    @PropertyName("type")
    private int type;

    private int senderOrReceiverType;

    public Message(){

    }

    @PropertyName("body")
    public String getMessageBody() {
        return messageBody;
    }


    public String getSender() {return sender;}

    public Timestamp getTime() {return time;}

    @PropertyName("type")
    public int getType() {
        return type;
    }

    @PropertyName("type")
    public void setType(int type) {
        this.type = type;
    }

    public int getSenderOrReceiverType() {return senderOrReceiverType;
    }

    public void setSenderOrReceiverType(int senderOrReceiverType) {
        this.senderOrReceiverType = senderOrReceiverType;
    }
}
