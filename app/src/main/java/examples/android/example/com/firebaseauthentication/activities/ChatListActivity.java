package examples.android.example.com.firebaseauthentication.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.ChatListActivityBinding;
import examples.android.example.com.firebaseauthentication.interfaces.ChatListInterface;
import examples.android.example.com.firebaseauthentication.presenters.ChatListPresenter;

public class ChatListActivity extends AppCompatActivity implements ChatListInterface.View {

    ChatListActivityBinding chatListActivityBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatListActivityBinding= DataBindingUtil.setContentView(this, R.layout.chat_list_activity);

        chatListActivityBinding.search.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_black_24dp,0,0,0);

        ChatListPresenter presenter=new ChatListPresenter(this);

        presenter.getChat();

    }

    @Override
    public void setUserChatsList() {

    }
}
