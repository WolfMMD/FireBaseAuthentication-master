package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import examples.android.example.com.firebaseauthentication.data.UserData;

public interface ContactsInterface {

    interface View{

        void setAllUsers(List<UserData> userNames);
        //void whenConversationStarts(FirebaseUser currentUser, UserData userData);
    }

    interface Presenter
    {
        void callGetAllUsers();
       // void callAddUserToChat(UserData userData);

    }

    interface Model
    {
        void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener);
       // void addUserToChat(UserData userData);
       // FirebaseUser getCurrentUser();
    }


}
