package examples.android.example.com.firebaseauthentication.models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import examples.android.example.com.firebaseauthentication.interfaces.EmailAuthContract;

public class Model implements EmailAuthContract.Model {

    private FirebaseAuth auth= FirebaseAuth.getInstance();

    public Model(){

    }


    @Override
    public void signIn(String email,String password,final OnCompleteListener completeListener) {

        auth.signInWithEmailAndPassword(email, password)
                           .addOnCompleteListener(completeListener);
    }

    @Override
    public void signUp(String email, String password,OnCompleteListener completeListener) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(completeListener);

        }

    @Override
    public void sendVerifyEmail(OnCompleteListener verityCompleteListener) {

        auth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(verityCompleteListener);
    }

    @Override
    public void resetPassword(String email, OnCompleteListener resetPasswordCompleteListener) {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(resetPasswordCompleteListener);

    }


    @Override
    public boolean isVerified() {

        FirebaseUser user= auth.getCurrentUser();
        return user.isEmailVerified();

    }


}
