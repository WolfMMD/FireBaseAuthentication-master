package examples.android.example.com.firebaseauthentication.presenters;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import examples.android.example.com.firebaseauthentication.data.UserData;
import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;
import examples.android.example.com.firebaseauthentication.models.ContactsModel;

public class ContactsPresenter implements ContactsInterface.Presenter {


    private ContactsInterface.View view;
    private ContactsInterface.Model model;
    private List<UserData> userDataList = new ArrayList<>();


   public ContactsPresenter(ContactsInterface.View view){
        this.view=view;
        model=new ContactsModel();
    }


    @Override
    public void callGetAllUsers() {

        OnCompleteListener<QuerySnapshot> onCompleteListener= task -> {

            if (task.isSuccessful() && task.getResult()!=null) {

                for (QueryDocumentSnapshot document : task.getResult()) {

                    Map<String, Object> user =document.getData();

                    user.get("fullName");
                    UserData userData= new UserData();
                    userData.setFullName(user.get("fullName").toString());
                    userData.setUserId(document.getId());
                    userDataList.add(userData);
                }

                view.setAllUsers(userDataList);

            }

            else {

                 Log.d("error", "Error getting documents: ", task.getException());
            }


        };


        model.getAllUsers(onCompleteListener);


    }

//    @Override
//    public void callAddUserToChat(UserData userData) {
//
//       model.addUserToChat(userData);
//       view.whenConversationStarts(model.getCurrentUser(),userData);
//
//
//    }
}
