package examples.android.example.com.firebaseauthentication.aAMessagingTestPckg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;

import examples.android.example.com.firebaseauthentication.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String CHANNEL_ID="firebase_chat_app_channelID";
    @Override
    public void onNewToken(String token) {

        //Log.e("newToken", s);
        UsersManager.getInstance().updateUserToken(token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        //create channel
        createChannel();



        if(remoteMessage.getData().get("msgType")!=null) {
            String parseInt = remoteMessage.getData().get("msgType");
            Log.e("returnType",parseInt);
            if(parseInt!=null && parseInt.equals("1")){
                try {
                    URL url = new URL(remoteMessage.getData().get("body"));
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setContentTitle(remoteMessage.getData().get("title"))
                            .setContentText("sent a photo.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setLargeIcon(image)
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(image)
                                    .bigLargeIcon(null));

                    //show notification
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(NotificationID.getID(), builder.build());

                } catch(IOException e) {
                    System.out.println(e);
                }

            }

            else{
                //build notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("body"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                //show notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(NotificationID.getID(), builder.build());
            }

        }




    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "chat_app_notification_name";
           // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }




}
