package examples.android.example.com.firebaseauthentication.activities;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.PasswordlessAuthBinding;

public class PasswordlessActivity extends AppCompatActivity {  //not working

    PasswordlessAuthBinding passwordlessAuthBinding;
    ActionCodeSettings actionCodeSettings;

    FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        passwordlessAuthBinding= DataBindingUtil.setContentView(this,R.layout.passwordless_auth);

                actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://fir-auth-36aac.firebaseapp.com/signIn")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("examples.android.example.com.firebaseauthentication.ios")
                        .setAndroidPackageName(
                                "examples.android.example.com.firebaseauthentication",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();


//        FirebaseDynamicLinks.getInstance().createDynamicLink().setLink(Uri.parse("https://fir-auth-36aac.firebaseapp.com/"))
//                .setDomainUriPrefix()
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                // Open links with com.example.ios on iOS
//                .setIosParameters(new DynamicLink.IosParameters.Builder("examples.android.example.com.firebaseauthentication.ios").build())
//                .buildDynamicLink();







        passwordlessAuthBinding.signUpWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=passwordlessAuthBinding.phoneNumber.getText().toString();
                if(!email.isEmpty()){


                    //sign in with email Link


                    auth.sendSignInLinkToEmail(email, actionCodeSettings)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Email sent.",Toast.LENGTH_LONG).show();
                                    }

                                    else
                                    {


                                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),4*Toast.LENGTH_LONG).show();

                                        System.out.println("%%"+task.getException().getMessage());


                                    }
                                }


                            });


                }
            }
        });

    }
}
