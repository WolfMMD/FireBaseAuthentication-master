package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import examples.android.example.com.firebaseauthentication.data.UserData;

public interface ContactsInterface {

    interface View{

        void setAllUsers(List<UserData> users);
        void notifyAdapter();
    }

    interface Presenter
    {
        void callGetSearchedOnUsers(String searchOn);

    }

    interface Model
    {
        void getSearchedOnData(String searchedOn,OnCompleteListener<QuerySnapshot> onCompleteListener);

    }


}
