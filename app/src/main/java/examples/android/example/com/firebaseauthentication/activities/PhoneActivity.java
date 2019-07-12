package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.PhoneAuthBinding;
import examples.android.example.com.firebaseauthentication.interfaces.PhoneInterface;
import examples.android.example.com.firebaseauthentication.presenters.PhonePresenter;

public class PhoneActivity extends AppCompatActivity implements PhoneInterface.View {

    PhoneAuthBinding phoneAuthBinding;
    PhoneInterface.Presenter presenter;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneAuthBinding = DataBindingUtil.setContentView(this, R.layout.phone_auth);

        initPhoneSignUp();
        presenter = new PhonePresenter(this);

      //  PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS,this,mCallback);


    }

    public void initPhoneSignUp(){
        phoneAuthBinding.signUpWithPhone.setOnClickListener(v -> presenter.callPhoneSignIn(phoneAuthBinding.phoneNumber.getText().toString()));

    }

//    public void phoneSignUp(){
//
//
//      String phone= phoneAuthBinding.phoneNumber.getText().toString();
//
//      if(!phone.isEmpty()){
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS,this,mCallback);
//
//      }
//
//      else{
//          Toast.makeText(getApplicationContext(), "please enter your phone number", Toast.LENGTH_LONG).show();
//      }
//
//
//    }


    @Override
    public void signedInSuccessfully() {

        Intent intent=new Intent(this,ContactsActivity.class);
        startActivity(intent);

    }

    @Override
    public void setCallback( PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback) {
        this.mCallback=mCallback;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneAuthBinding.phoneNumber.getText().toString(),60, TimeUnit.SECONDS,this,mCallback);


    }
}
