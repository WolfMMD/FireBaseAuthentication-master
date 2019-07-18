package examples.android.example.com.firebaseauthentication.interfaces;

import android.net.Uri;

import java.util.List;
import examples.android.example.com.firebaseauthentication.data.Message;

public interface ChatInterface {

    interface View{
        void setChatMessages(List<Message> chatMessages);

    }
    interface Model{
       void getChatMessages(String partnerID);
       void addMessageToDB(String messageBody,String partnerID);
       void addImageToDB(Uri selectedImageUri);
    }
    interface Presenter{
        void getMessagesFor(String partnerID);
        void callAddMessageToDB(String messageBody,String partnerID);
        void onMessagesReceived(List<Message> chatMessages);
        void callAddImageToDB(Uri selectedImageUri);
    }



}
