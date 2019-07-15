package examples.android.example.com.firebaseauthentication.presenters;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.interfaces.ChatInterface;
import examples.android.example.com.firebaseauthentication.models.ChatModel;

public class ChatPresenter implements ChatInterface.Presenter {

    private ChatInterface.View view;
    private ChatInterface.Model model;


    public ChatPresenter(ChatInterface.View view){
        this.view=view;
        model=new ChatModel(this);
    }


    @Override
    public void callGetChatMessages(String partnerID) {

        model.getChatMessages(partnerID);
    }

    @Override
    public void callAddMessageToDB(String messageBody,String partnerID) {

        model.addMessageToDB(messageBody,partnerID);
    }

    @Override
    public void onMessagesReceived(List<Message> chatMessages) {

        view.setChatMessages(chatMessages);
    }


}
