package examples.android.example.com.firebaseauthentication.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.GoogleAuthBinding;

public class GoogleAuthActivity extends AppCompatActivity {

    private static int RC_SIGN_IN=123;
    GoogleAuthBinding googleAuthBinding;
    GoogleSignInOptions gso;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       googleAuthBinding= DataBindingUtil.setContentView(this,R.layout.google_auth);

       auth=FirebaseAuth.getInstance();

       initSignInButton();
        // Build a GoogleSignInClient with the options specified by gso.
       final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleAuthBinding.signUpWithG.setOnClickListener(new View.OnClickListener() {
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
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);



                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Toast.makeText(getApplicationContext(),"Google sign in failed",Toast.LENGTH_LONG).show();

            }



        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(getApplicationContext(),"Signed in Successfully",Toast.LENGTH_LONG).show();


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(),"Signed in Failed",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }


@Override
    protected void onStart(){
        super.onStart();

    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if(account !=null){
        Toast.makeText(getApplicationContext(),"you are already signed in",Toast.LENGTH_LONG).show();
    }
}
}
