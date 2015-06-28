package com.ggl.android.basic;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by fangbrian on 6/27/15.
 */
public class BasicService extends IntentService implements PhotoSetManager.IPhotoSetListener {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BasicService(String name) {
        super(name);
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
        // Show notification
    }
}
