package examples.android.example.com.firebaseauthentication.aAMessagingTestPckg;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import static com.facebook.AccessTokenManager.TAG;

public class UsersManager {

    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

   private static UsersManager instance=null;
   private  String token;

   public static UsersManager getInstance(){

       return instance == null ? instance = new UsersManager() : instance;

   }

   @SuppressWarnings("deprecation")
   public  String getUserToken(){
       return FirebaseInstanceId.getInstance().getToken();
   }

//    void setUserToken(String token){
//       this.token=token;
//   }

   public void updateUserToken(String token){

       Map<String,Object> updatedUserInfo=new HashMap<>();
       updatedUserInfo.put("token",token);

      if(auth.getCurrentUser()!=null)
       db.collection("users").document(auth.getCurrentUser().getUid()).update(updatedUserInfo)
               .addOnSuccessListener(documentReference -> Log.d("success", "token added to Firestore"))
               .addOnFailureListener(e -> Log.w(TAG, "Error adding token", e));
   }
}
