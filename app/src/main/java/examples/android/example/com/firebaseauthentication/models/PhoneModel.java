package examples.android.example.com.firebaseauthentication.models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import examples.android.example.com.firebaseauthentication.interfaces.PhoneInterface;

public class PhoneModel implements PhoneInterface.Model {

    private FirebaseAuth authPhone = FirebaseAuth.getInstance();


    public PhoneModel(){

    }

    @Override
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential, OnCompleteListener onCompleteListener) {


        authPhone.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(onCompleteListener);
    }


}
