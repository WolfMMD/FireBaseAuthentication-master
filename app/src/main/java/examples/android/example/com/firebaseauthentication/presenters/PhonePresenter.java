package examples.android.example.com.firebaseauthentication.presenters;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import examples.android.example.com.firebaseauthentication.interfaces.PhoneInterface;
import examples.android.example.com.firebaseauthentication.models.PhoneModel;


public class PhonePresenter implements PhoneInterface.Presenter {

    private PhoneInterface.Model model;
    private PhoneInterface.View view;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;



    public PhonePresenter(PhoneInterface.View view){

        this.view=view;
        model=new PhoneModel();
    }


    private OnCompleteListener<AuthResult> getOnCompleteListener(){

        return task -> {
            if (task.isSuccessful()) {
                view.signedInSuccessfully();
            }

        };
    }


    @Override

    public void callPhoneSignIn(String phone) {


        // Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();
        // Toast.makeText(getApplicationContext(), "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "verification failed", Toast.LENGTH_LONG).show();
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
        // Toast.makeText(getApplicationContext(), "code", Toast.LENGTH_LONG).show();
        // Save verification ID and resending token so we can use them later

        mCallback= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                model.signInWithPhoneAuthCredential(phoneAuthCredential, getOnCompleteListener());


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Log.e("12","Invaled Request");
                    // Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();


                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.e("122","The SMS quota for the project has been exceeded");
                    // Toast.makeText(getApplicationContext(), "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();

                } else {
                    Log.e("123","The SMS quota for the project has been exceeded");
                    //Toast.makeText(getApplicationContext(), "verification failed", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Toast.makeText(getApplicationContext(), "code", Toast.LENGTH_LONG).show();

                // Save verification ID and resending token so we can use them later

                Log.e("^^","code sent");
                mVerificationId = verificationId;
                mResendToken = token;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, "123456");
                model.signInWithPhoneAuthCredential(credential,getOnCompleteListener());

            }
        };



       view.setCallback(mCallback);


    }



}
