package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
   private  ContactsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersBinding= DataBindingUtil.setContentView(this, R.layout.list_users);


        usersBinding.search.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_black_24dp,0,0,0);


        initListOfUsers();
        initSearchEditText();
        presenter = new ContactsPresenter(this);

    }

    private void initSearchEditText() {

        usersBinding.search.setOnClickListener(v -> {
            usersBinding.search.setText(" ");
        });


        usersBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.callGetSearchedOnUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void initListOfUsers(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usersBinding.listOfUsers.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setAllUsers(List<UserData> userNames) {
        ContactsAdapter adapter= new ContactsAdapter(userNames, this);
        usersBinding.listOfUsers.setAdapter(adapter);
    }

    @Override
    public void notifyAdapter() {
        ContactsAdapter adapter=new ContactsAdapter(new ArrayList<>(),this);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void setClickedUser(UserData userData) {
        Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
        intent.putExtra("partnerID",userData.getUserId());
        intent.putExtra("partnerName",userData.getFullName());
        startActivity(intent);
    }
}
