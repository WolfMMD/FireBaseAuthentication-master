package examples.android.example.com.firebaseauthentication;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import examples.android.example.com.firebaseauthentication.interfaces.AuthenticationInterface;
import examples.android.example.com.firebaseauthentication.presenters.AuthenticationPresenter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void validateEmail() {
//        String email = "a.b@c.com";
//        AuthenticationPresenter presenter = new AuthenticationPresenter(new AuthenticationInterface.View() {
//            @Override
//            public void showProgressDialog() {
//
//            }
//
//            @Override
//            public void dismissProgressDialog() {
//
//            }
//
//            @Override
//            public void signedInSuccessfully(FirebaseUser currentUser) {
//
//            }
//
//            @Override
//            public void emailValid() {
//
//            }
//
//            @Override
//            public void emailInvalid(String feedback) {
//
//            }
//
//            @Override
//            public void passwordValid() {
//
//            }
//
//            @Override
//            public void passwordInvalid(String feedback) {
//
//            }
//
//            @Override
//            public void showAlertDialog(String message) {
//
//            }
//        }, null);
//        assertTrue(presenter.validateEmail(email));
//    }
}