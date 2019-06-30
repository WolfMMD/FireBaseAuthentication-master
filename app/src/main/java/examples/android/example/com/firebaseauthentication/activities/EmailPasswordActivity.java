package examples.android.example.com.firebaseauthentication.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import examples.android.example.com.firebaseauthentication.R;
import examples.android.example.com.firebaseauthentication.databinding.EmailPasswordAuthBinding;
import examples.android.example.com.firebaseauthentication.interfaces.EmailAuthContract;
import examples.android.example.com.firebaseauthentication.presenters.Presenter;

public class EmailPasswordActivity extends AppCompatActivity implements EmailAuthContract.View {

    EmailPasswordAuthBinding emailPasswordAuthBinding;
    String email, password;
    EmailAuthContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        emailPasswordAuthBinding = DataBindingUtil.setContentView(this, R.layout.email_password_auth);


        presenter = new Presenter(this);

        initView();
        initSignIn();
        initSignUp();
        initResetPassword();

    }

    public void initView(){

        emailPasswordAuthBinding.forgetPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        emailPasswordAuthBinding.signUp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void initSignIn() {

        emailPasswordAuthBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailPasswordAuthBinding.email.getText().toString();
                password = emailPasswordAuthBinding.password.getText().toString();


                if (!email.trim().isEmpty() && !password.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    presenter.callSignIn(email, password);

                } else {

                    setToastMessage("Please fill the required fields");
                }

            }
        });

    }


    public void initSignUp() {

        emailPasswordAuthBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailPasswordAuthBinding.email.getText().toString();
                password = emailPasswordAuthBinding.password.getText().toString();

                if (!email.trim().isEmpty() && !password.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    presenter.callSignUp(email, password);

                } else

                    setToastMessage("Please fill the required fields");
            }
        });
    }


    public void initResetPassword(){


        emailPasswordAuthBinding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailPasswordAuthBinding.email.getText().toString();

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
}
