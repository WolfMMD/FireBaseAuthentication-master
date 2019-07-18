package examples.android.example.com.firebaseauthentication.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.aAMessagingTestPckg.MyFirebaseMessagingService;
import examples.android.example.com.firebaseauthentication.databinding.AuthenticationBinding;
import examples.android.example.com.firebaseauthentication.interfaces.AuthenticationInterface;
import examples.android.example.com.firebaseauthentication.data.Constants;
import examples.android.example.com.firebaseauthentication.presenters.AuthenticationPresenter;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationInterface.View {

    AuthenticationBinding authBinding;
    AuthenticationInterface.Presenter presenter;

    ProgressDialog dialog;
    AlertDialog.Builder alertDialogBuilder;

    Intent intent;
    String email, password;
    private static int RC_SIGN_IN = 123;


    GoogleSignInOptions gso;
    //
    CallbackManager callbackManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(this.getApplicationContext());
        authBinding = DataBindingUtil.setContentView(this, R.layout.authentication);

        presenter = new AuthenticationPresenter(this);

        initView();
        initProgressDialog();

        initSignIn();
        initSignUp();
        initResetPassword();

        initGoogleSignInOptions();
        initGoogleSignIn();

        initFacebookLogin();
        initPhoneSignIn();


        //?
        //onEditTextChanged();



    }

//    public void onEditTextChanged() {
//
//
//        authBinding.email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                authBinding.email.setText(" ");
//                authBinding.emailFeedbackMsg.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//        authBinding.password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                authBinding.password.setText(" ");
//                authBinding.passFeedbackMsg.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }



    public void initView() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton(R.string.ok, null);
        authBinding.forgetPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        authBinding.signUp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initProgressDialog(){
        dialog= new ProgressDialog(this);
        dialog.setMessage(getString(R.string.signInProgressDialog));
    }

    public void initSignIn() {

        authBinding.signIn.setOnClickListener(v -> {
            email = authBinding.email.getText().toString();
            password = authBinding.password.getText().toString();

            boolean isValid = true;

            if (!presenter.validateEmail(email))
                isValid = false;

            if (!presenter.validatePassword(password))
                isValid = false;

            if (isValid)
            presenter.callSignIn(email, password);
        });
    }

    public void initSignUp() {

        authBinding.signUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void initResetPassword() {

        authBinding.forgetPassword.setOnClickListener(v -> {
            email = authBinding.email.getText().toString();

            boolean isValid = true;
            if (!presenter.validateEmail(email))
                isValid = false;

            if (isValid)
            presenter.callResetPassword(email);
        });
    }

    public void initGoogleSignInOptions() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(Constants.idToken)
                .build();
    }

    private void initGoogleSignIn(){

        authBinding.signUpWithGoogle.setOnClickListener(v -> {
            final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            presenter.callFireBaseAuthWithGoogle(account);
        }

        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void initFacebookLogin(){

        authBinding.facebookLoginButton.setOnClickListener(v -> {

           // LoginButton loginButton=new LoginButton()


        });
//        //authBinding.facebookLoginButton.setReadPermissions("email");
//        authBinding.facebookLoginButton.setOnClickListener(v ->
//                presenter.callRegisterCallback());

    }

    private void initPhoneSignIn(){

        authBinding.phoneSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this,PhoneActivity.class);
            startActivity(intent);
        });
    }

    //?
    @Override
    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager=callbackManager;
    }



    //done

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void signedInSuccessfully() {
        intent = new Intent(AuthenticationActivity.this, ContactsActivity.class);
        startActivity(intent);
    }

    @Override
    public void signedInFailed() {
        alertDialogBuilder.setMessage(getString(R.string.signInFailed));
        alertDialogBuilder.show();
    }

    @Override
    public void verifyAccount() {
        alertDialogBuilder.setMessage(getString(R.string.verifyAccountMsg));
        alertDialogBuilder.show();
    }

    @Override
    public void resetEmailSent() {
        alertDialogBuilder.setMessage(getString(R.string.resetEmailSent));
        alertDialogBuilder.show();
    }

    @Override
    public void resetEmailFailed() {
        alertDialogBuilder.setMessage(getString(R.string.resetEmailFailed));
        alertDialogBuilder.show();
    }

    @Override
    public void emailValid() {
        authBinding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
        authBinding.emailFeedbackMsg.setText(" ");
    }

    @Override
    public void emailInvalid(String feedback) {
        authBinding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_black_24dp, 0);
        authBinding.emailFeedbackMsg.setText(feedback);
    }

    @Override
    public void passwordValid() {
        authBinding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_black_24dp, 0);
        authBinding.passFeedbackMsg.setText(" ");

    }

    @Override
    public void passwordInvalid(String feedback) {
        authBinding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_black_24dp, 0);
        authBinding.passFeedbackMsg.setText(feedback);
    }






}
