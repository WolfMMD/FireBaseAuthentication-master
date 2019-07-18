package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface PhoneInterface {

    interface View {
        void signedInSuccessfully();

        void setCallback(PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback);
    }

    interface Presenter {

        void callPhoneSignIn(String phone);
    }

    interface Model {
        void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential, OnCompleteListener onCompleteListener);

    }
}
