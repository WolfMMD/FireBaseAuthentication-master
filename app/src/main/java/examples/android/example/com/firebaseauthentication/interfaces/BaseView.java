package examples.android.example.com.firebaseauthentication.interfaces;

public interface BaseView {

    void dismissProgressDialog();
    void showProgressDialog();
    void emailValid();
    void emailInvalid(String feedback);
    void passwordValid();
    void passwordInvalid(String feedback);

}
