package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.activities.chatting.ChatActivity;
import examples.android.example.com.firebaseauthentication.databinding.AuthenticationBinding;
import examples.android.example.com.firebaseauthentication.databinding.GoogleAuthBinding;
import examples.android.example.com.firebaseauthentication.interfaces.EmailPasswordContract;
import examples.android.example.com.firebaseauthentication.presenters.EmailPasswordPresenter;

public class AuthenticationActivity extends AppCompatActivity implements EmailPasswordContract.View {

    AuthenticationBinding authBinding;
    Intent intent;
    EmailPasswordContract.Presenter presenter;

    //for Email and Pass
    String email, password,fullName;


    //for FB
    CallbackManager callbackManager;

    //google
    private static int RC_SIGN_IN=123;
    GoogleSignInOptions gso;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authBinding = DataBindingUtil.setContentView(this, R.layout.authentication);

        callbackManager = CallbackManager.Factory.create();

        presenter = new EmailPasswordPresenter(this,callbackManager);

        //FB
        authBinding.facebookLoginButton.setReadPermissions("email");

        authBinding.facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.registerCallback();
            }
        });


        //email
        initView();
        initSignIn();
        initSignUp();
        initResetPassword();

        initSignInButton();

        //Google
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        authBinding.signUpWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();

                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

    }

    public void initSignInButton(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("994131237640-4vjdq2uh90k018l3n778miqb5ickfd1f.apps.googleusercontent.com")
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult();

            presenter.firebaseAuthWithGoogle(account);




        }
    }


    @Override
    protected void onStart(){
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account !=null){
            Toast.makeText(getApplicationContext(),"you are already signed in",Toast.LENGTH_LONG).show();
        }
    }

    public void initView(){

        authBinding.forgetPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        authBinding.signUp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    public void initSignIn() {

        authBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = authBinding.email.getText().toString();
                password = authBinding.password.getText().toString();


                if (!email.trim().isEmpty() && !password.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    presenter.callSignIn(email, password);


                } else {

                    setToastMessage("Please fill the required fields");
                }

            }
        });

    }


    public void initSignUp() {

        authBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = authBinding.email.getText().toString();
                password = authBinding.password.getText().toString();
                fullName= authBinding.fullName.getText().toString();

                if (!email.trim().isEmpty() && !password.trim().isEmpty() && !fullName.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    presenter.callSignUp(email, password,fullName);

                } else

                    setToastMessage("Please fill the required fields");
            }
        });
    }


    public void initResetPassword(){


        authBinding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = authBinding.email.getText().toString();

                if(!(email.isEmpty()) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){


                    presenter.callResetPassword(email);
                }


                else{

                    setToastMessage("Please make sure you're using a valid email address");
                }

            }
        });
    }

    @Override
    public void setToastMessage(String message) {

        Toast.makeText(getApplicationContext(), message, 2 * Toast.LENGTH_LONG).show();


    }

    @Override
    public void whenSignedIn(FirebaseUser currentUser) {


        //to be updated
        intent =new Intent(AuthenticationActivity.this, ChatActivity.class);
        intent.putExtra("name",currentUser.getEmail());
        startActivity(intent);
    }




}
