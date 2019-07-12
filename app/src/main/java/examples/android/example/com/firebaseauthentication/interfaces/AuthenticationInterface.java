package examples.android.example.com.firebaseauthentication.interfaces;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

public interface AuthenticationInterface {

    interface View extends BaseView{
        void signedInSuccessfully();
        void signedInFailed();
        void verifyAccount();
        void resetEmailSent();
        void resetEmailFailed();
        void setCallbackManager(CallbackManager callbackManager);
    }

    interface Model{

        void addFBGUserToDataBase();
        void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener);
        void resetPassword(String email, OnCompleteListener<Void> onCompleteListener);
        boolean isVerified();
        void firebaseAuthWithGoogle(AuthCredential credential, OnCompleteListener<AuthResult> onCompleteListener);
        void registerCallback(FacebookCallback<LoginResult> facebookCallback, CallbackManager callbackManager);
        void handleFacebookAccessToken(AuthCredential credential, OnCompleteListener<AuthResult> onCompleteListener);

    }

    interface Presenter{

        void callSignIn(String email, String password);
        boolean validateEmail(String email);
        boolean validatePassword(String password);
        void callResetPassword(String email);
        void callRegisterCallback();
        void callHandleFacebookAccessToken(AccessToken token);
        void callFireBaseAuthWithGoogle(GoogleSignInAccount acct);
        void callAddFB();



    }


}
