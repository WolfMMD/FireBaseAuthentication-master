package examples.android.example.com.firebaseauthentication.presenters;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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


    public ContactsPresenter(ContactsInterface.View view) {
        this.view = view;
        model = new ContactsModel();
    }


    @Override
    public void callGetSearchedOnUsers(String searchOn) {

        List<UserData> userDataList = new ArrayList<>();

        if (searchOn.length() == 0) {
            userDataList.clear();

        } else {

            OnCompleteListener<QuerySnapshot> onCompleteListener = task -> {

                if (task.isSuccessful() && task.getResult() != null) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Map<String, Object> user = document.getData();

                        user.get("fullName");
                        UserData userData = new UserData();
                        userData.setFullName(user.get("fullName").toString());
                        userData.setUserId(document.getId());
                        userDataList.add(userData);
                    }

                    view.setAllUsers(userDataList);

                } else {
                    Log.d("error", "Error getting documents: ", task.getException());
                }

            };

            model.getSearchedOnData(searchOn, onCompleteListener);
        }
        view.notifyAdapter();
    }

}
