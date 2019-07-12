package examples.android.example.com.firebaseauthentication.models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;

public class ContactsModel implements ContactsInterface.Model {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    //private FirebaseUser currentUser;

    public ContactsModel(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //currentUser= auth.getCurrentUser();
    }

    @Override
    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {

        db.collection("users").get().addOnCompleteListener(onCompleteListener);


    }

//    @Override
//    public void addUserToChat(final UserData userData) {
//
//        Date date= new Date();
//        Timestamp ts = new Timestamp(date);
//
//        Map<String,Object> message=new HashMap<>();
//        message.put("sender",currentUser.getUid()); //take msg from editText
//        message.put("time",ts);
//        message.put("body",)
////
//
//        //?????????????????????????????
//        // the message should be added to msgs not update
//
//        DocumentReference addAnotherMessageRef = db.collection("chats")
//                .document(currentUser.getUid()+"_"+userData.getUserId())
//                .collection("msgs").document();
//
////        Map<String,Object> newMessage=new HashMap<>();
////       addAnotherMessageRef.update("msgs",FieldValue.arrayUnion(newMessage));
////
//
//    }

//    @Override
//    public FirebaseUser getCurrentUser() {
//        return currentUser;
//    }


}
