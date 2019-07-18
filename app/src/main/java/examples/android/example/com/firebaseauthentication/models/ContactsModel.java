package examples.android.example.com.firebaseauthentication.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import examples.android.example.com.firebaseauthentication.data.UserData;
import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;

public class ContactsModel implements ContactsInterface.Model {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ContactsModel() {

    }

    @Override
    public void getSearchedOnData(String searchedOn, OnCompleteListener<QuerySnapshot> onCompleteListener) {

        CollectionReference reference = db.collection("users");

        Query q = reference.orderBy("fullName").startAt(searchedOn.trim()).endAt(searchedOn.trim() + "\uf8ff");

        q.get().addOnCompleteListener(onCompleteListener);


    }

}
