package examples.android.example.com.firebaseauthentication.models;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.interfaces.ChatInterface;


public class ChatModel implements ChatInterface.Model {

    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser=auth.getCurrentUser();
    private String documentId;
    private ChatInterface.Presenter presenter;

    private List<Message>mdlIst=new ArrayList<>();



    public ChatModel(ChatInterface.Presenter presenter){

        this.presenter=presenter;
    }

    @Override
    public void getChatMessages(String partnerID) {



        db.collection("chats").document(currentUser.getUid()+"_"+partnerID).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){


                DocumentSnapshot document = task.getResult();

                if(document!=null && document.exists()){

                    documentId= task.getResult().getId();

                    db.collection("chats")
                            .document(documentId)
                            .collection("msgs").orderBy("time").get().addOnCompleteListener(getOnCompleteListener());
                }

                else{

                    documentId= partnerID+"_"+currentUser.getUid();

                    db.collection("chats")
                            .document(documentId)
                            .collection("msgs").orderBy("time").get().addOnCompleteListener(getOnCompleteListener());
                }


            }

            else {

                Log.d("^^","Data retrieved failed");
            }
        });

    }

    private OnCompleteListener<QuerySnapshot> getOnCompleteListener() {

        List<Message> typedMessageList=new ArrayList<>();

        return task -> {

            if (task.getResult() !=null) {

                for(Message message: task.getResult().toObjects(Message.class)){

                    if(message.getSender().equals(currentUser.getUid())){
                        message.setType(0);

                    }

                    else {
                        message.setType(1);

                    }
                    typedMessageList.add(message);
                }
                presenter.callSetChatMessage(typedMessageList);
            }

        };
    }

    @Override
    public void addMessageToDB(String messageBody,String partnerID) {


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

