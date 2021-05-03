/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geekhive.studentsoft.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.DriverDetailsForParents;
import com.geekhive.studentsoft.activities.GuestuserDashboard;
import com.geekhive.studentsoft.activities.ParentsDashboard;
import com.geekhive.studentsoft.activities.StudentDashboard;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    JSONObject jsObj = null;
    //private NotificationTarget notificationTarget;
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. LoginUserData messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. LoginUserData
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleNow();
            /*  if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            sendNotification(remoteMessage);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().toString());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }


    private void sendNotification(final RemoteMessage remoteMessage) {
        if (remoteMessage != null) {
            try {
                /*if (remoteMessage.getNotification().getBody().contains("OfferDetails")) {
                    JSONObject jsonObject = new JSONObject(remoteMessage.getNotification().getBody());
                    JSONArray jsonArray = jsonObject.getJSONObject("OfferDetails").getJSONArray("Offer");
                    jsObj = jsonArray.getJSONObject(0);
                    Log.e("Json Array", jsObj.toString());
                    final String image = WebServices.SM_Services + jsObj.getString("image");
                    new DownloadImage().execute(image);
                } else if (remoteMessage.getNotification().getBody().contains("PromoCodeDetails")) {
                    String channelId = getString(R.string.app_name);
                    Intent intent = new Intent(MyFirebaseMessagingService.this, StudentDashboard.class);

                    intent.putExtra("ms", remoteMessage.getNotification().getBody());

                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                    Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                    final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);
                    JSONObject jsonObject = new JSONObject(remoteMessage.getNotification().getBody());
                    JSONArray jsonArray = jsonObject.getJSONObject("PromoCodeDetails").getJSONArray("PromoCode");
                    jsObj = jsonArray.getJSONObject(0);
                    String name = jsObj.getString("name") + "\n" + "\n" + jsObj.getString("detail");

                    remoteViews.setTextViewText(R.id.remoteview_notification_headline, name);

                    remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                    // build notification
                    final NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle(jsObj.getString("name"))
                                    .setContentText(jsObj.getString("detail"))
                                    .setAutoCancel(true)
                                    .setSound(soundUri)
                                    .setContent(remoteViews)
                                    .setContentIntent(pendingIntent)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH);

                    final Notification notification = mBuilder.build();

                    // set big content view for newer androids
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        notification.bigContentView = remoteViews;
                    }
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                        AudioAttributes attributes = new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                .build();

                        NotificationChannel mChannel = new NotificationChannel(channelId,
                                getApplicationContext().getString(R.string.app_name),
                                NotificationManager.IMPORTANCE_HIGH);

                        // Configure the notification channel.
                        mChannel.setDescription(jsObj.getString("detail"));
                        mChannel.enableLights(true);
                        mChannel.enableVibration(true);
                        mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                        if (mNotificationManager != null)
                            mNotificationManager.createNotificationChannel(mChannel);
                    }


                    mNotificationManager.notify(1, notification);
                } else {*/
                    String channelId = getString(R.string.app_name);
                    if(remoteMessage.getNotification().getTitle().equals("Pick & Drop!")){
                        if(Prefs.getUserType(this).equals("parent")){
                            Intent intent = new Intent(MyFirebaseMessagingService.this, DriverDetailsForParents.class);
                            intent.putExtra("ms", remoteMessage.getNotification().getBody());
                            PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                            final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);

                            String name = remoteMessage.getNotification().getTitle() + "\n" + "\n" + remoteMessage.getNotification().getBody();

                            remoteViews.setTextViewText(R.id.remoteview_notification_headline, name);

                            remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                            // build notification
                            final NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(remoteMessage.getNotification().getTitle())
                                            .setContentText(remoteMessage.getNotification().getBody())
                                            .setAutoCancel(true)
                                            .setSound(soundUri)
                                            .setContent(remoteViews)
                                            .setContentIntent(pendingIntent)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                            final Notification notification = mBuilder.build();

                            // set big content view for newer androids
                            if (android.os.Build.VERSION.SDK_INT >= 16) {
                                notification.bigContentView = remoteViews;
                            }
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                                AudioAttributes attributes = new AudioAttributes.Builder()
                                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                        .build();

                                NotificationChannel mChannel = new NotificationChannel(channelId,
                                        getApplicationContext().getString(R.string.app_name),
                                        NotificationManager.IMPORTANCE_HIGH);

                                // Configure the notification channel.
                                mChannel.setDescription(remoteMessage.getNotification().getBody());
                                mChannel.enableLights(true);
                                mChannel.enableVibration(true);
                                mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                                if (mNotificationManager != null)
                                    mNotificationManager.createNotificationChannel(mChannel);
                            }


                            mNotificationManager.notify(1, notification);
                        }

                    }




               /* }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            Bitmap bitmap = Glide.with(getApplicationContext()).asBitmap()
                    .load(imageUrl).into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL).get();
            return bitmap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DownloadImage AsyncTask
    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
                if (result != null) {
                    if (!(jsObj.getString("name").equals("")) || !(jsObj.getString("name").isEmpty())) {
                        String channelId = getString(R.string.app_name);
                        Intent intent = new Intent(MyFirebaseMessagingService.this, GuestuserDashboard.class);

                        intent.putExtra("ms", jsObj.getString("name"));

                        PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);

                        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.mipmap.ic_launcher);

                        remoteViews.setTextViewText(R.id.remoteview_notification_headline, jsObj.getString("detail"));

                        remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                        remoteViews.setImageViewBitmap(R.id.remoteview_notification_icon, result);

                        // build notification
                        final NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle(jsObj.getString("name"))
                                        .setContentText(jsObj.getString("detail"))
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setContent(remoteViews)
                                        .setContentIntent(pendingIntent)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                        final Notification notification = mBuilder.build();

                        // set big content view for newer androids
                        if (android.os.Build.VERSION.SDK_INT >= 16) {
                            notification.bigContentView = remoteViews;
                        }
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                            AudioAttributes attributes = new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .build();

                            NotificationChannel mChannel = new NotificationChannel(channelId,
                                    getApplicationContext().getString(R.string.app_name),
                                    NotificationManager.IMPORTANCE_HIGH);

                            // Configure the notification channel.
                            mChannel.setDescription(jsObj.getString("name"));
                            mChannel.enableLights(true);
                            mChannel.enableVibration(true);
                            mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                            if (mNotificationManager != null)
                                mNotificationManager.createNotificationChannel(mChannel);
                        }


                        mNotificationManager.notify(1, notification);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

