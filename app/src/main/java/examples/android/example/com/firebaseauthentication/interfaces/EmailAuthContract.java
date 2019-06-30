package examples.android.example.com.firebaseauthentication.interfaces;


import com.google.android.gms.tasks.OnCompleteListener;

public interface EmailAuthContract {

    interface View{

        void setToastMessage(String message);
    }

    interface Model{

        void signIn(String email, String password,OnCompleteListener completeListener);

        void signUp(String email, String password,OnCompleteListener completeListener);

        void sendVerifyEmail(OnCompleteListener verityCompleteListener);

        void resetPassword(String email, OnCompleteListener resetPasswordCompleteListener);

        boolean isVerified();

    }

    interface Presenter{

        void callSignIn(String email, String password);

        void callSignUp(String email, String password);

        void callResetPassword(String email);

    }


}
