package examples.android.example.com.firebaseauthentication.models;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;

public class ContactsModel implements ContactsInterface.Model {


    private DatabaseReference mDatabase;

    public ContactsModel(){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-auth-36aac.firebaseio.com/");
        mDatabase = database.getReference();
    }

    @Override
    public void getAllUsers(ValueEventListener valueEventListener) {

        mDatabase.addValueEventListener(valueEventListener);

    }

    @Override
    public void addUserToChat(UserData userData) {

        //add user's name to contacts of the current user
        //add chat to DB that contains message each message have a sender, body and time

    }

}
