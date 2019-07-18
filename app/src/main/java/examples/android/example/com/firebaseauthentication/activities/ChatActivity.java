package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.adapters.ChatAdapter;
import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.databinding.ChatLayoutBinding;
import examples.android.example.com.firebaseauthentication.interfaces.ChatInterface;
import examples.android.example.com.firebaseauthentication.presenters.ChatPresenter;


public class ChatActivity extends AppCompatActivity implements ChatInterface.View {

    private static final int RESULT_IMAGE_PICKED = 1;
    private ChatLayoutBinding chatLayoutBinding;
    public String partnerID;
    public String partnerName;
    private ChatInterface.Presenter presenter;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatLayoutBinding = DataBindingUtil.setContentView(this, R.layout.chat_layout);

        initChatAdapter();
        initRecyclerView();
        initIntentData();
        initSendTextMessageButton();
        initSendPictureButton();
        presenter = new ChatPresenter(this);
        presenter.getMessagesFor(partnerID);


    }

    public void initChatAdapter() {

        chatAdapter = new ChatAdapter(new ArrayList<>());
        chatLayoutBinding.chatMessagesRecyclerView.setAdapter(chatAdapter);
    }

    public void initIntentData() {
        partnerID = getIntent().getStringExtra("partnerID"); //const
        partnerName = getIntent().getStringExtra("partnerName");
        chatLayoutBinding.partnerName.setText(partnerName);
    }

    public void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatLayoutBinding.chatMessagesRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initSendTextMessageButton() {

        chatLayoutBinding.sendMessageIcon.setOnClickListener(v -> {

            String messageBody = chatLayoutBinding.editText.getText().toString();
            if (!messageBody.trim().isEmpty()) {
                chatLayoutBinding.editText.setText("");
                presenter.callAddMessageToDB(messageBody, partnerID);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_IMAGE_PICKED && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            presenter.callAddImageToDB(selectedImage);
        }
    }

    public void initSendPictureButton() {
        chatLayoutBinding.sendPic.setOnClickListener(v -> {

            //send pic
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_IMAGE_PICKED);
        });
    }

    @Override
    public void setChatMessages(List<Message> chatMessages) {

        int position = 0;
        chatAdapter.setMessageList(chatMessages);

        if (chatAdapter.getItemCount() > 0)
            position = chatAdapter.getItemCount() - 1;

        if (chatLayoutBinding.chatMessagesRecyclerView.getLayoutManager() != null)
            chatLayoutBinding.chatMessagesRecyclerView.getLayoutManager()
                    .smoothScrollToPosition(chatLayoutBinding.chatMessagesRecyclerView, null, position);

        chatAdapter.notifyDataSetChanged();


    }

}
