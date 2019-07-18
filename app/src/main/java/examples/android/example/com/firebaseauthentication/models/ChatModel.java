package examples.android.example.com.firebaseauthentication.models;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

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
                                db.collection("chats").document(documentId).collection("msgs").orderBy("time").addSnapshotListener(getOnEventListener());


                            } else {

                                //sender_receiver chat and receiver_sender chat not existed
                                isChatExisted = false;
                            }
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

                List<DocumentChange> newestMessages = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange messageChange : newestMessages) {

                    Message message = messageChange.getDocument().toObject(Message.class);
                    if (message.getSender().equals(currentUser.getUid())) {
                        message.setSenderOrReceiverType(0);

                    } else {
                        message.setSenderOrReceiverType(1);

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

        if (!isChatExisted) {
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
        message.put("type",0);


        db.collection("chats")
                .document(documentId)
                .collection("msgs").document().set(message);

    }

    @Override
    public void addImageToDB(Uri selectedImageUri) {


        Date date = new Date();
        Timestamp ts = new Timestamp(date);


        StorageReference imagesRef = storageRef.child(documentId).child(ts.toString());
        imagesRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {

            if(taskSnapshot!=null){
            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(uri!=null){
                        String uploadedImageUrl=uri.toString();
                        Log.e("url",uploadedImageUrl);

                        Map<String, Object> message = new HashMap<>();
                        message.put("sender", currentUser.getUid());
                        message.put("time", ts);
                        message.put("body", uploadedImageUrl);
                        message.put("type",1);


                        db.collection("chats")
                                .document(documentId)
                                .collection("msgs").document().set(message);
                    }
                }
            });

//                Map<String, Object> message = new HashMap<>();
//                message.put("sender", currentUser.getUid());
//                message.put("time", ts);
//                message.put("body", uploadedImageUrl);
//                message.put("type",1);
//
//
//                db.collection("chats")
//                        .document(documentId)
//                        .collection("msgs").document().set(message);
            }

        });

    }

}


