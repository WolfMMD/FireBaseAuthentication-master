package examples.android.example.com.firebaseauthentication.presenters;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import examples.android.example.com.firebaseauthentication.interfaces.EmailAuthContract;
import examples.android.example.com.firebaseauthentication.models.Model;

public class Presenter implements EmailAuthContract.Presenter {

   private EmailAuthContract.Model model;
   private EmailAuthContract.View view;

    public Presenter(EmailAuthContract.View view) {

        this.view=view;
        model=new Model();
    }


    @Override
    public void callSignIn(String email, String password) {


        OnCompleteListener completeListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if (model.isVerified()) {

                        view.setToastMessage("Signed In Successfully");

                    } else {

                        view.setToastMessage("please verify your account");
                    }


                } else {

                    view.setToastMessage("Failed to sign in");

                }
            }

        };

       model.signIn(email,password,completeListener);

    }

    @Override
    public void callSignUp(String email, String password) {

       final OnCompleteListener verifyCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    view.setToastMessage("Please check your E-mail to verify your Account");
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

                    model.sendVerifyEmail(verifyCompleteListener);


                } else {

                    view.setToastMessage("Error in Creating account");

                }
            }

        };

        model.signUp(email,password,completeListener);

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

      model.resetPassword(email,resetPassListener);

    }


}


