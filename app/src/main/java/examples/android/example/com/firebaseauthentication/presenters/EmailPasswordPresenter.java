package examples.android.example.com.firebaseauthentication.presenters;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import examples.android.example.com.firebaseauthentication.interfaces.EmailPasswordContract;
import examples.android.example.com.firebaseauthentication.models.EmailPasswordModel;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EmailPasswordPresenter implements EmailPasswordContract.Presenter {

   private EmailPasswordContract.Model model;
   private EmailPasswordContract.View view;

   private FirebaseAuth auth= FirebaseAuth.getInstance();
   private FirebaseUser currentUser;

    CallbackManager callbackManager;


    public EmailPasswordPresenter(EmailPasswordContract.View view, CallbackManager callbackManager) {

        this.view=view;
        this.callbackManager=callbackManager;
        model=new EmailPasswordModel();
    }


    @Override
    public void callSignIn(String email, String password) {


        OnCompleteListener completeListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if (isVerified()) {

                        view.setToastMessage("Signed In Successfully");

                         currentUser= auth.getCurrentUser();
                         view.whenSignedIn(currentUser);


                    } else {

                        view.setToastMessage("please verify your account");

                        //testing for chat
                        currentUser= auth.getCurrentUser();
                        view.whenSignedIn(currentUser);
                    }


                } else {

                    view.setToastMessage("Failed to sign in");

                }
            }

        };

        auth.signInWithEmailAndPassword(email, password)
                           .addOnCompleteListener(completeListener);

    }

    @Override
    public void callSignUp(String email, String password, final String fullName) {

       final OnCompleteListener verifyCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    view.setToastMessage("Please check your E-mail to verify your Account");

                    currentUser=auth.getCurrentUser();
                    model.addUserToDataBase(currentUser,fullName);

                }
                else{

                    view.setToastMessage("We seem to have a problem sending you a verification E-mail");

                }

            }
        };


        OnCompleteListener completeListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    view.setToastMessage("Account Created Successfully. A verification E-mail will be sent to your E-mail address");

                    sendVerifyEmail(verifyCompleteListener);


                } else {

                    view.setToastMessage("Error in Creating account");

                }
            }

        };


        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(completeListener);

    }

    @Override
    public void sendVerifyEmail(OnCompleteListener verityCompleteListener) {

        auth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(verityCompleteListener);
    }

    @Override
    public void callResetPassword(String email) {

      OnCompleteListener resetPassListener=  new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                   view.setToastMessage("Password reset E-mail has been sent");

                }
            }
        };

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(resetPassListener);

    }

    @Override
    public void registerCallback() {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookAccessToken(loginResult.getAccessToken());

                        Toast.makeText(getApplicationContext(),"onSuccess", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancel() {

                        Toast.makeText(getApplicationContext(), "OnCancel", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {

                        Toast.makeText(getApplicationContext(), "OnError", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"successfully signed in with facebook",Toast.LENGTH_LONG).show();

                            // view.setCallbackManager(callbackManager);
                            model.addFBGUserToDataBase(auth.getCurrentUser());



                        } else {



                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(getApplicationContext(),"Signed in Successfully",Toast.LENGTH_LONG).show();

                            model.addFBGUserToDataBase(auth.getCurrentUser());


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(),"Signed in Failed",Toast.LENGTH_LONG).show();

                        }

                    }
                });



    }


    @Override
    public boolean isVerified() {

        FirebaseUser user= auth.getCurrentUser();
        return user.isEmailVerified();

    }

}


