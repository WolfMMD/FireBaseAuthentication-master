package examples.android.example.com.firebaseauthentication.interfaces;


import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

public interface EmailPasswordContract {

    interface View{

        void setToastMessage(String message);
        void whenSignedIn(FirebaseUser currentUser);
    }

    interface Model{

        void addUserToDataBase(FirebaseUser currentUser, String fullName);

        void addFBGUserToDataBase(FirebaseUser currentUser);

    }

    interface Presenter{

        void callSignIn(String email, String password);

        void callSignUp(String email, String password,String fullName);
        void sendVerifyEmail(OnCompleteListener verityCompleteListener);
        boolean isVerified();

        void callResetPassword(String email);

        void registerCallback();

        void handleFacebookAccessToken(AccessToken token);

        void firebaseAuthWithGoogle(GoogleSignInAccount acct);



    }


}
