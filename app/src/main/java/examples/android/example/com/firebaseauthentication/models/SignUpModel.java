package examples.android.example.com.firebaseauthentication.models;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import examples.android.example.com.firebaseauthentication.interfaces.SignUpInterface;
import static com.facebook.AccessTokenManager.TAG;

public class SignUpModel implements SignUpInterface.Model {


    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser=auth.getCurrentUser();

    public SignUpModel(){

    }

    @Override
    public void signUp(String fullName, String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void addUserToDataBase(String fullName) {

        Map<String, Object> user = new HashMap<>();
        user.put("fullName", fullName);
        if (currentUser.getEmail() !=null)
        user.put("email", currentUser.getEmail());

        db.collection("users").document(currentUser.getUid()).set(user)
                .addOnSuccessListener(documentReference -> Log.d("success", "user added to Firestore"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

    }


    @Override
    public void sendVerificationEmail(OnCompleteListener<Void> verifyCompleteListener) {

        if(auth.getCurrentUser() !=null)
        auth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(verifyCompleteListener);
    }


}
