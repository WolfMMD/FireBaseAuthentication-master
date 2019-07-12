package examples.android.example.com.firebaseauthentication.presenters;

import android.util.Patterns;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import examples.android.example.com.firebaseauthentication.interfaces.SignUpInterface;
import examples.android.example.com.firebaseauthentication.data.Constants;
import examples.android.example.com.firebaseauthentication.models.SignUpModel;

public class SignUpPresenter implements SignUpInterface.Presenter {

    private SignUpInterface.View view;
    private SignUpInterface.Model model;

    public SignUpPresenter(SignUpInterface.View view){

        this.view=view;
        model=new SignUpModel();
    }

    @Override
    public void callSignUp(final String fullName, String email, String password) {

            view.showProgressDialog();

            OnCompleteListener<AuthResult> completeListener = task -> {

                view.dismissProgressDialog();

                if (task.isSuccessful()) {

                  view.signedUpSuccessfully();
                  callSendVerificationEmail(fullName);

                } else {
                    view.signedUpFailed();
                }
            };

            model.signUp(fullName,email,password,completeListener);


    }
    @Override
    public void callSendVerificationEmail(String fullName) {

        final OnCompleteListener<Void> verifyCompleteListener = task -> {

            if (task.isSuccessful()) {

                view.verifyAccount();
                model.addUserToDataBase(fullName);

            }
            else{
                view.verifyEmailFailed();
            }

        };

        model.sendVerificationEmail(verifyCompleteListener);

    }

    @Override
    public boolean validateEmail(String email) {

        if (email != null && !email.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            view.emailValid();
            return true;
        }

        else if (email!=null && email.isEmpty()){
            view.emailInvalid(Constants.fillRequiredFields);
            return false;
        }

        else {
            view.emailInvalid(Constants.enterValidEmail);
            return false;
        }
    }

    @Override
    public boolean validateFullName(String fullName) {

     if (fullName != null && !fullName.trim().isEmpty()){

        view.fullNameValid();
        return true;
    }

     else {

        view.fullNameInvalid(Constants.fillRequiredFields);
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
