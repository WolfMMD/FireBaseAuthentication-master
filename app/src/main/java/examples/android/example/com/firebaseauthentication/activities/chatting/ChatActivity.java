package examples.android.example.com.firebaseauthentication.activities.chatting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import java.util.List;
import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.databinding.ChatLayoutBinding;
import examples.android.example.com.firebaseauthentication.interfaces.ChatInterface;
import examples.android.example.com.firebaseauthentication.presenters.ChatPresenter;


public class ChatActivity extends AppCompatActivity implements ChatInterface.View {

    private ChatLayoutBinding chatLayoutBinding;
    public String partnerID;
    public String partnerName;
    private ChatInterface.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatLayoutBinding = DataBindingUtil.setContentView(this, R.layout.chat_layout);

        initRecyclerView();
        initIntentData();
        initSendMessageButton();
        presenter=new ChatPresenter(this);
        presenter.callGetChatMessages(partnerID);



    }

    public void initIntentData(){
        partnerID =getIntent().getStringExtra("partnerID");
        partnerName =getIntent().getStringExtra("partnerName");
       // currentUser =getIntent().getParcelableExtra("current user");
    }

    public void initRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatLayoutBinding.messagesView.setLayoutManager(linearLayoutManager);
    }

    public void initSendMessageButton(){

        chatLayoutBinding.actionImage.setOnClickListener(v -> {

            String messageBody=chatLayoutBinding.editText.getText().toString();
            if(!messageBody.isEmpty()){
                chatLayoutBinding.editText.setText(" ");
                presenter.callAddMessageToDB(messageBody,partnerID);
            }

        });
    }

    @Override
    public void setChatMessages(List<Message> chatMessages) {

        ChatAdapter chatAdapter = new ChatAdapter(chatMessages);
        chatLayoutBinding.messagesView.setAdapter(chatAdapter);

    }

    //
//    @Override
//    public void messageAddedSuccessfullyToChat() {
//
//    }

}
