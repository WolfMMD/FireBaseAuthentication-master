package examples.android.example.com.firebaseauthentication.activities.chatting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.ChatLayoutBinding;


public class ChatActivity extends AppCompatActivity implements ActivityCallback {

    private DatabaseReference mDatabase;
    private ChatAdapter chatAdapter;
    private ChatLayoutBinding chatLayoutBinding;
    private ChatData data;
    private List<ChatData> conversation= new ArrayList<>();

    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatLayoutBinding = DataBindingUtil.setContentView(this, R.layout.chat_layout);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-auth-36aac.firebaseio.com/");
        mDatabase = database.getReference();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatLayoutBinding.messagesView.setLayoutManager(linearLayoutManager);

        name=getIntent().getStringExtra("name");



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Toast.makeText(getApplicationContext(), "CHAT SUCCESS!", Toast.LENGTH_LONG).show();

                conversation=new ArrayList<>();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    ChatData data = item.getValue(ChatData.class);
                    conversation.add(data);
                }

                chatAdapter=new ChatAdapter(conversation);
                chatAdapter.notifyDataSetChanged();
                chatLayoutBinding.messagesView.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        chatLayoutBinding.actionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = new ChatData();
                data.setMessage(chatLayoutBinding.editText.getText().toString());
//                data.setId("userId");
                data.setName(name);
                chatLayoutBinding.editText.setText(" ");

                //add chats to DB
                mDatabase.child("chats").child("messages").child(String.valueOf(new Date().getTime())).setValue(data);

            }
        });
    }



}
