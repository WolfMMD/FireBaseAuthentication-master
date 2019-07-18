package examples.android.example.com.firebaseauthentication.models;

import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import examples.android.example.com.firebaseauthentication.interfaces.AuthenticationInterface;
import static com.facebook.AccessTokenManager.TAG;


public class AuthenticationModel implements AuthenticationInterface.Model {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
   // private CallbackManager callbackManager;


    public AuthenticationModel() {
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        currentUser=auth.getCurrentUser();
       // callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void resetPassword(String email, OnCompleteListener<Void> onCompleteListener) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(onCompleteListener);

    }

    @Override
    public boolean isVerified() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            return user.isEmailVerified();
        else return false;

    }


    //need to check

    @Override
    public void addFBGUserToDataBase() {

        if(currentUser!=null) {
            Map<String, Object> user = new HashMap<>();
            if (currentUser.getDisplayName() != null)
                user.put("fullName", currentUser.getDisplayName());
            if (currentUser.getEmail() != null)
                user.put("email", currentUser.getEmail());

            db.collection("users").document(currentUser.getUid()).set(user)
                    .addOnSuccessListener(documentReference -> Log.d("success", "user added to Firestore"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

        }

    }


    @Override
    public void firebaseAuthWithGoogle(AuthCredential credential,OnCompleteListener<AuthResult> onCompleteListener) {

        auth.signInWithCredential(credential)
                .addOnCompleteListener(onCompleteListener);


    }

    @Override
    public void registerCallback(FacebookCallback<LoginResult> facebookCallback, CallbackManager callbackManager) {

        LoginManager.getInstance().registerCallback(callbackManager,facebookCallback);
    }

    @Override
    public void handleFacebookAccessToken(AuthCredential credential, OnCompleteListener<AuthResult> onCompleteListener) {

        auth.signInWithCredential(credential)
                .addOnCompleteListener(onCompleteListener);
    }

}
