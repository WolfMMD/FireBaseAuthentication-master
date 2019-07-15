package examples.android.example.com.firebaseauthentication.models;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.interfaces.ChatInterface;


public class ChatModel implements ChatInterface.Model {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String documentId;
    private ChatInterface.Presenter presenter;
    private List<Message> typedMessageList = new ArrayList<>();
    private boolean isChatExisted;


    public ChatModel(ChatInterface.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void getChatMessages(String partnerID) {


        db.collection("chats").document(partnerID + "_" + currentUser.getUid()).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {


                if (task.getResult() != null && (task.getResult().exists())) {

                    //get messages from msgs sub-collection
                    documentId = task.getResult().getId();
                    db.collection("chats").document(documentId).collection("msgs").orderBy("time").addSnapshotListener(getOnEventListener());

                } else {

                    documentId = currentUser.getUid() + "_" + partnerID;

                    db.collection("chats").document(documentId).get().addOnCompleteListener(task1 -> {

                        if (task.isSuccessful()) {

                            if (task.getResult() != null && task.getResult().exists()) {
                                documentId = task.getResult().getId();

//                                db.collection("chats").document(documentId).collection("msgs").orderBy("time").addSnapshotListener(getOnEventListener());

                            } else {

//                                //sender_receiver chat and receiver_sender chat not existed

                                   isChatExisted=false;


//                                Date date = new Date();
//                                Timestamp ts = new Timestamp(date);
//
//                                Map<String, Object> firstChat = new HashMap<>();
//                                firstChat.put("createdAt", ts);
//                                db.collection("chats").document(documentId).set(firstChat);
                            }

                            db.collection("chats").document(documentId).collection("msgs").orderBy("time").addSnapshotListener(getOnEventListener());



                        }


                    });

                }


            } //task successful

        });

    }

    private EventListener<QuerySnapshot> getOnEventListener() {




        return (queryDocumentSnapshots, e) -> {

            if (e != null) {
                Log.w("falied", "Listen failed.", e);
                return;
            }


            if (queryDocumentSnapshots != null) {
                // Log.d(TAG, "Current data: " + snapshot.getData());



                List<DocumentChange> newestMessages = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange messageChange : newestMessages) {

                    Message message = messageChange.getDocument().toObject(Message.class);
                    if (message.getSender().equals(currentUser.getUid())) {
                        message.setType(0);

                    } else {
                        message.setType(1);

                    }
                    typedMessageList.add(message);
                }
                presenter.onMessagesReceived(typedMessageList);

            } else {


                Log.d("error", "Current data: null");
            }


        };

    }


    @Override
    public void addMessageToDB(String messageBody, String partnerID) {

        if(!isChatExisted){
            Date date = new Date();
            Timestamp ts = new Timestamp(date);

            Map<String, Object> firstChat = new HashMap<>();
            firstChat.put("createdAt", ts);
            db.collection("chats").document(documentId).set(firstChat);
        }


        Date date = new Date();
        Timestamp ts = new Timestamp(date);

        Map<String, Object> message = new HashMap<>();
        message.put("sender", currentUser.getUid());
        message.put("time", ts);
        message.put("body", messageBody);


        db.collection("chats")
                .document(documentId)
                .collection("msgs").document().set(message);

    }

}


