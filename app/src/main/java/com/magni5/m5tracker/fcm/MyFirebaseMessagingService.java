package com.magni5.m5tracker.fcm;

/**
 * Created by Manikanta on 7/14/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.utils.Utility;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> params = remoteMessage.getData();
        if (params != null && params.size() > 0) {
            Utility.showLog("dataChat", "<><>" + params);
            Utility.showLog(TAG, "From: " + remoteMessage.getFrom());
            Utility.showLog(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    /*  private void sendNotification(String messageBody) {
          Intent intent = new Intent(this, DashBoardActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                  0);


  *//*        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();*//*

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

// this is a my insertion looking for a solution
        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.gym: R.mipmap.dumble;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());

    }*/
    /*@Override
    public void zzm(Intent intent) {
        Intent launchIntent = new Intent(this, MainActivity.class);
        launchIntent.setAction(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* R    equest code *//*, launchIntent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_close_black_24dp)
                .setLargeIcon(rawBitmap)
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setContentText(intent.getStringExtra("gcm.notification.body"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/

    private void sendNotification(String Data) {
        Intent intent = new Intent(this, MainActivity.class);
           /* intent.putExtra(Constants.POST_ID, postId);
            intent.putExtra(Constants.TYPE, type);*/
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_location_on)
                .setContentTitle("FM Council")
                .setContentText(Data)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}