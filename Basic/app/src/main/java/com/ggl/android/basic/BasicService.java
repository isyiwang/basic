package com.ggl.android.basic;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by fangbrian on 6/27/15.
 */
public class BasicService extends IntentService implements PhotoSetManager.IPhotoSetListener {

    private int mNotificationId;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BasicService() {
        super("BasicService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GalleryChangeManager.getInstance().initialize(this);
        PhotoSetManager.getInstance().setListener(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onPhotoSetAdded(PhotoSetManager.PhotoSet photoSet) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("New photo set detected")
                .setContentText(photoSet.size() + " photos");

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(mNotificationId++, builder.build());
    }
}
