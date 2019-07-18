package examples.android.example.com.firebaseauthentication.interfaces;

public interface ChatListInterface {

    interface View{
        void setUserChatsList();
    }
    interface Presenter{
        void getChat();
    }
    interface Model{
        void getAllUserChats();
    }
}
