package examples.android.example.com.firebaseauthentication.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;
import examples.android.example.com.firebaseauthentication.models.ContactsModel;
import examples.android.example.com.firebaseauthentication.models.UserData;

public class ContactsPresenter implements ContactsInterface.Presenter {

    private FirebaseAuth auth= FirebaseAuth.getInstance();
    private ContactsInterface.View view;
    private ContactsInterface.Model model;
    private List<UserData> userDataList = new ArrayList<>();


   public ContactsPresenter(ContactsInterface.View view){

        this.view=view;
        model=new ContactsModel();

    }


    @Override
    public void callGetAllUsers() {

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    for(DataSnapshot userData: item.getChildren()){

                        UserData data =userData.getValue(UserData.class);

                        userDataList.add(data);
                    }

                }

                view.setAllUsers(userDataList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println("%%"+databaseError.getMessage());

            }
        };

        model.getAllUsers(valueEventListener);

       // view.setAllUsers(userDataList);


    }

    @Override
    public void callAddUserToChat(UserData userData) {



    }
}
