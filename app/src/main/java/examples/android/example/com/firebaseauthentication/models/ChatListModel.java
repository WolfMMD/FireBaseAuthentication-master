package examples.android.example.com.firebaseauthentication.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import examples.android.example.com.firebaseauthentication.interfaces.ChatListInterface;

public class ChatListModel implements ChatListInterface.Model {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void getAllUserChats() {

        db.collection("chats").orderBy("createdAt")
                .startAt(currentUser.getUid()).endAt(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                task.getResult().getDocuments();

                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){

                    Map < String, Object> map= documentSnapshot.getData();

                    map.get("createdAt");

                }
            }
        });
    }
}
