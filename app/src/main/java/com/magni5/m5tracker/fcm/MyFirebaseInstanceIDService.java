package com.magni5.m5tracker.fcm;

/**
 * Created by Manikanta on 10/26/2016.
 */

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Utility.setSharedPrefStringData(this, Constants.DEVICE_TOKEN, refreshedToken);
        Utility.showLog(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Intent intent = new Intent(Constants.KEY_TOKEN_RECEIVER);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra(Constants.KEY_FCM_TOKEN, token);
        broadcastManager.sendBroadcast(intent);
    }
}