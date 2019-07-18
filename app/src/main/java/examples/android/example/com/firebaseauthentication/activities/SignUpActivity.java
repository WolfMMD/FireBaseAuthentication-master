package examples.android.example.com.firebaseauthentication.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.SignUpActivityBinding;
import examples.android.example.com.firebaseauthentication.interfaces.SignUpInterface;
import examples.android.example.com.firebaseauthentication.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface.View {

    SignUpActivityBinding signUpActivityBinding;
    SignUpInterface.Presenter presenter;
    AlertDialog.Builder alertDialogBuilder;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpActivityBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity);

        initAlertDialog();
        initProgressDialog();
        initSignUp();
        presenter = new SignUpPresenter(this);

    }

    private void initAlertDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton(getString(R.string.ok), null);
    }

    private void initProgressDialog() {

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.signUpProgressDialog));
    }

    public void initSignUp() {
        signUpActivityBinding.signUp.setOnClickListener(v -> {

            String fullName = signUpActivityBinding.fullName.getText().toString();
            String email = signUpActivityBinding.email.getText().toString();
            String password = signUpActivityBinding.password.getText().toString();
            boolean isValid = true;

            if (!presenter.validateEmail(email)) {
                isValid = false;
            }
            if (!presenter.validateFullName(fullName)) {
                isValid = false;
            }
            if (!presenter.validatePassword(password)) {
                isValid = false;
            }

            if (isValid)
                presenter.callSignUp(fullName, email, password);

        });
    }


    @Override
    public void emailValid() {
        signUpActivityBinding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
        signUpActivityBinding.emailFeedbackMsg.setText(" ");
    }

    @Override
    public void emailInvalid(String feedback) {
        signUpActivityBinding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_black_24dp, 0);
        signUpActivityBinding.emailFeedbackMsg.setText(feedback);
    }

    @Override
    public void passwordValid() {
        signUpActivityBinding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
        signUpActivityBinding.passFeedbackMsg.setText(" ");
    }

    @Override
    public void passwordInvalid(String feedback) {
        signUpActivityBinding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_black_24dp, 0);
        signUpActivityBinding.passFeedbackMsg.setText(feedback);
    }

    @Override
    public void fullNameValid() {
        signUpActivityBinding.fullName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
        signUpActivityBinding.fullNameFeedbackMsg.setText(" ");
    }

    @Override
    public void fullNameInvalid(String feedback) {
        signUpActivityBinding.fullName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_black_24dp, 0);
        signUpActivityBinding.fullNameFeedbackMsg.setText(feedback);
    }

    @Override
    public void signedUpSuccessfully() {
        alertDialogBuilder.setMessage(getString(R.string.signUpSuccessfully));
        alertDialogBuilder.show();
    }

    @Override
    public void signedUpFailed() {
        alertDialogBuilder.setMessage(getString(R.string.signUpError));
        alertDialogBuilder.show();
    }

    @Override
    public void verifyAccount() {
        alertDialogBuilder.setMessage(getString(R.string.verifyAccountMsg));
        alertDialogBuilder.show();
    }

    @Override
    public void verifyEmailFailed() {
        alertDialogBuilder.setMessage(getString(R.string.verifyAccountFailed));
        alertDialogBuilder.show();
    }


    @Override
    public void dismissProgressDialog() {
        dialog.hide();
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }
}
