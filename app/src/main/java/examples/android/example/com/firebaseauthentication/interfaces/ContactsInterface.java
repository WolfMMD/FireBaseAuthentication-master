package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import examples.android.example.com.firebaseauthentication.models.UserData;

public interface ContactsInterface {

    interface View{

        void setAllUsers(List<UserData> userNames);
    }

    interface Presenter
    {
        void callGetAllUsers();
        void callAddUserToChat(UserData userData);
    }

    interface Model
    {
        void getAllUsers(ValueEventListener valueEventListener);

        void addUserToChat(UserData userData);
    }


}
