package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;
import examples.android.example.com.firebaseauthentication.data.Message;

public interface ChatInterface {

    interface View{
        void setChatMessages(List<Message> chatMessages);

    }
    interface Model{
       void getChatMessages(String partnerID);
       void addMessageToDB(String messageBody,String partnerID);
    }
    interface Presenter{
        void callGetChatMessages(String partnerID);
        void callAddMessageToDB(String messageBody,String partnerID);
        void onMessagesReceived(List<Message> chatMessages);
    }



}
