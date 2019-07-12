package examples.android.example.com.firebaseauthentication.interfaces;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public interface SignUpInterface {

    interface View extends BaseView{

        void fullNameValid();
        void fullNameInvalid(String feedback);
        void signedUpSuccessfully();
        void signedUpFailed();
        void verifyAccount();
        void verifyEmailFailed();
    }
    interface Presenter{

        void callSignUp(String fullName,String email, String password);
        void callSendVerificationEmail(String fullName);
        boolean validateEmail(String email);
        boolean validateFullName(String fullName);
        boolean validatePassword(String password);

    }
    interface Model{

        void addUserToDataBase(String fullName);
        void signUp(String fullName, String email, String password, OnCompleteListener<AuthResult> onCompleteListener);
        void sendVerificationEmail(OnCompleteListener<Void> verifyCompleteListener);

    }
}
