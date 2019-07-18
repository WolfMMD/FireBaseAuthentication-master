package examples.android.example.com.firebaseauthentication.presenters;

import examples.android.example.com.firebaseauthentication.interfaces.ChatListInterface;
import examples.android.example.com.firebaseauthentication.models.ChatListModel;

public class ChatListPresenter implements ChatListInterface.Presenter {

    private ChatListInterface.View view;
    private ChatListInterface.Model model;

    public ChatListPresenter(ChatListInterface.View view){
        this.view=view;
        model=new ChatListModel();
    }
    @Override
    public void getChat() {

        model.getAllUserChats();
    }
}
