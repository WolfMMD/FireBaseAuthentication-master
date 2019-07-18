package examples.android.example.com.firebaseauthentication.presenters;

import android.util.Patterns;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import examples.android.example.com.firebaseauthentication.aAMessagingTestPckg.UsersManager;
import examples.android.example.com.firebaseauthentication.interfaces.AuthenticationInterface;
import examples.android.example.com.firebaseauthentication.models.AuthenticationModel;
import examples.android.example.com.firebaseauthentication.data.Constants;
import static com.facebook.FacebookSdk.getApplicationContext;

public class AuthenticationPresenter implements AuthenticationInterface.Presenter {

    private AuthenticationInterface.Model model;
    private AuthenticationInterface.View view;
    private CallbackManager callbackManager;

    public AuthenticationPresenter(AuthenticationInterface.View view) {

        this.view = view;
        model = new AuthenticationModel();
        callbackManager = CallbackManager.Factory.create();
    }


    @Override
    public void callSignIn(String email, String password) {

            view.showProgressDialog();

            OnCompleteListener<AuthResult> completeListener = task -> {

                view.dismissProgressDialog();

                if (task.isSuccessful()) {

                    if (model.isVerified()){

                        view.signedInSuccessfully();
                        UsersManager.getInstance().updateUserToken(UsersManager.getInstance().getUserToken());

                    }



                     else
                        view.verifyAccount();
                        //for testing chat don't block unverified users to enter chat
                        view.signedInSuccessfully();
                        UsersManager.getInstance().updateUserToken(UsersManager.getInstance().getUserToken());

                }
                else
                    view.signedInFailed();
            };

            model.signIn(email, password,completeListener);
    }


    @Override
    public void callResetPassword(String email) {
        OnCompleteListener<Void> resetPassListener = task -> {
            if (task.isSuccessful()) {
                view.resetEmailSent();

            } else {
                view.resetEmailFailed();
            }
        };

        model.resetPassword(email, resetPassListener);
    }

    @Override
    public void callFireBaseAuthWithGoogle(final GoogleSignInAccount acct) {

        OnCompleteListener<AuthResult> onCompleteListener= task -> {
            if (task.isSuccessful()) {

                model.addFBGUserToDataBase();
                view.signedInSuccessfully();



            } else {
                view.signedInFailed();
            }

        };
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        model.firebaseAuthWithGoogle(credential,onCompleteListener);
    }

    @Override
    public void callAddFB() {
        model.addFBGUserToDataBase();
    }

    //check

    @Override
    public void callRegisterCallback() {

        callbackManager = CallbackManager.Factory.create();

       FacebookCallback<LoginResult> facebookCallback= new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                       callHandleFacebookAccessToken(loginResult.getAccessToken());

                        Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancel() {

                        Toast.makeText(getApplicationContext(), "OnCancel", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {

                        Toast.makeText(getApplicationContext(), "OnError", Toast.LENGTH_SHORT).show();

                    }
                };

       model.registerCallback(facebookCallback,callbackManager);
       view.setCallbackManager(callbackManager);

    }

    @Override
    public void callHandleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

                    OnCompleteListener<AuthResult> onCompleteListener= task -> {
                        if (task.isSuccessful()) {

                            view.signedInSuccessfully();
                            model.addFBGUserToDataBase();

                        } else
                            view.signedInFailed();

                    };

          model.handleFacebookAccessToken(credential,onCompleteListener);

    }



    //done

    @Override
    public boolean validateEmail(String email) {

        if (email != null && !email.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            view.emailValid();
            return true;
        } else if (email.isEmpty()) {

            view.emailInvalid(Constants.fillRequiredFields);

            return false;
        } else {
            view.emailInvalid(Constants.enterValidEmail);
            return false;
        }
    }


    @Override
    public boolean validatePassword(String password) {

        if (password != null && !password.isEmpty()) {
            view.passwordValid();
            return true;
        } else {
            view.passwordInvalid(Constants.fillRequiredFields);
            return false;
        }
    }



}


