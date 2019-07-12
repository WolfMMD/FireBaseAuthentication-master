package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import java.util.List;
import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.activities.chatting.ChatActivity;
import examples.android.example.com.firebaseauthentication.adapters.ContactsAdapter;
import examples.android.example.com.firebaseauthentication.data.UserData;
import examples.android.example.com.firebaseauthentication.databinding.ListUsersBinding;
import examples.android.example.com.firebaseauthentication.interfaces.AdapterToActivityInterface;
import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;
import examples.android.example.com.firebaseauthentication.presenters.ContactsPresenter;

public class ContactsActivity extends AppCompatActivity implements ContactsInterface.View, AdapterToActivityInterface {

   private ListUsersBinding usersBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersBinding= DataBindingUtil.setContentView(this, R.layout.list_users);

        initListOfUsers();
        ContactsPresenter presenter = new ContactsPresenter(this);
        presenter.callGetAllUsers();
    }

    private void initListOfUsers(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usersBinding.listOfUsers.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setAllUsers(List<UserData> userNames) {
        ContactsAdapter adapter = new ContactsAdapter(userNames, this);
        usersBinding.listOfUsers.setAdapter(adapter);
    }

//    @Override
//    public void whenConversationStarts(FirebaseUser currentUser, UserData userData) {
//
//
//        Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
//
//        intent.putExtra("current user",currentUser);
//        intent.putExtra("partnerID",userData.getUserId());
//        intent.putExtra("partnerName",userData.getFullName());
//
//        startActivity(intent);
//    }


    @Override
    public void setClickedUser(UserData userData) {


        Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);

        intent.putExtra("partnerID",userData.getUserId());
        intent.putExtra("partnerName",userData.getFullName());

        startActivity(intent);


    }
}
