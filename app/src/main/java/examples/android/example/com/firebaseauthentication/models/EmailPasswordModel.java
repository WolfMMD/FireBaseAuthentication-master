package examples.android.example.com.firebaseauthentication.models;



import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import examples.android.example.com.firebaseauthentication.interfaces.EmailPasswordContract;

public class EmailPasswordModel implements EmailPasswordContract.Model {


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-auth-36aac.firebaseio.com/");

    private DatabaseReference mDatabase = database.getReference();


    public EmailPasswordModel(){

    }


    @Override
    public void addUserToDataBase(FirebaseUser currentUser, String fullName) {

        UserData userData=new UserData();

        userData.setFullName(fullName);
        userData.setUserEmail(currentUser.getEmail());
      //  userData.setUserId(currentUser.getUid());

        mDatabase.child("users").child(currentUser.getUid()).setValue(userData);
       // mDatabase.child("users").setValue(userData);
    }

    @Override
    public void addFBGUserToDataBase(FirebaseUser currentUser) {

        UserData userData=new UserData();

        userData.setFullName(currentUser.getDisplayName());

        userData.setUserEmail(currentUser.getEmail());


        //userData.setUserId(currentUser.getUid());

        Map<String, Object> userDataMap = new HashMap<>();
        userDataMap.put(currentUser.getUid(),userData );


       // mDatabase.child("users").child(currentUser.getUid()).setValue(userData);
        mDatabase.child("users").updateChildren(userDataMap);
    }
}
