package examples.android.example.com.firebaseauthentication.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.PhoneAuthBinding;

public class PhoneActivity extends AppCompatActivity {

    PhoneAuthBinding phoneAuthBinding;
    FirebaseAuth authPhone;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneAuthBinding = DataBindingUtil.setContentView(this, R.layout.phone_auth);

        authPhone = FirebaseAuth.getInstance();

        phoneAuthBinding.signUpWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneSignUp();
            }
        });


        mCallback= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();


                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(getApplicationContext(), "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();

                }

                else
                {
                    Toast.makeText(getApplicationContext(), "verification failed", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                Toast.makeText(getApplicationContext(), "code", Toast.LENGTH_LONG).show();

                // Save verification ID and resending token so we can use them later

                mVerificationId = verificationId;
                mResendToken = token;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, "123456");
                signInWithPhoneAuthCredential(credential);

            }
        };


    }


    public void phoneSignUp(){


      String phone= phoneAuthBinding.phoneNumber.getText().toString();

      if(!phone.isEmpty()){

          PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS,this,mCallback);

      }

      else{
          Toast.makeText(getApplicationContext(), "please enter your phone number", Toast.LENGTH_LONG).show();
      }


    }


    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential){


        authPhone.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(), "Signed In Successfully", Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }


}
