package examples.android.example.com.firebaseauthentication.data;

import java.util.List;

public class ChatData {

    private String partnerName;

    private List<Message> mMessage;

    public ChatData() {
        // empty constructor
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public List<Message> getmMessage() {
        return mMessage;
    }

    public void setmMessage(List<Message> mMessage) {
        this.mMessage = mMessage;
    }
}
