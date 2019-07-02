package examples.android.example.com.firebaseauthentication.activities.chatting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.ListUsersBinding;
import examples.android.example.com.firebaseauthentication.interfaces.AdapterToActivityInterface;
import examples.android.example.com.firebaseauthentication.interfaces.ContactsInterface;
import examples.android.example.com.firebaseauthentication.models.UserData;
import examples.android.example.com.firebaseauthentication.presenters.ContactsPresenter;

public class ContactsActivity extends AppCompatActivity implements ContactsInterface.View, AdapterToActivityInterface {

    ListUsersBinding usersBinding;
    ContactsAdapter adapter;
    ContactsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersBinding= DataBindingUtil.setContentView(this, R.layout.list_users);

        initListOfUsers();


         presenter=new ContactsPresenter(this);

        presenter.callGetAllUsers();


    }

    private void initListOfUsers(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usersBinding.listOfUsers.setLayoutManager(linearLayoutManager);



    }
    @Override
    public void setAllUsers(List<UserData> userNames) {

       // System.out.println("))"+userNames.get(0));

        adapter=new ContactsAdapter(userNames, this);
        usersBinding.listOfUsers.setAdapter(adapter);

    }


    @Override
    public void setClickedUser(UserData userData) {

        //send to presenter then model to add it on DB (contacts and chat)

        presenter.callAddUserToChat(userData);


    }
}
