package com.ggl.android.basic;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * This class manages listening to changes for a phone's Gallery (image library) and notifies
 * a listener when a photo is added.
 * 
 * Created by isaac on 6/27/15.
 */
public class GalleryChangeManager {
    private static final String TAG = GalleryChangeManager.class.getSimpleName();

    private static GalleryChangeManager sManager;
    private static boolean sInitialized = false;
    private Context mContext;
    private ImageContentObserver mObserver;

    public static GalleryChangeManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new GalleryChangeManager();
        return sManager;
    }

    public void initialize(Context context) {
        if (sInitialized) return;
        sInitialized = true;

        mContext = context.getApplicationContext();
        mObserver = new ImageContentObserver(new Handler());
        mContext.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, false, mObserver);
    }

    public void destroy() {
        mContext.getContentResolver().unregisterContentObserver(mObserver);
        mObserver = null;
    }

    private class ImageContentObserver extends ContentObserver {
        private String[] PROJECTION = new String[] {
                BaseColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.MediaColumns.DATA,
        };

        final String ORDER_BY = MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT 1";

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public ImageContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Toast.makeText(mContext, "Change received", Toast.LENGTH_SHORT).show();
            queryAndNotifyLatestPhoto();
        }

        private void queryAndNotifyLatestPhoto() {
            Photo photo = getLatestPhoto();
            // Notify listener of new photo
        }

        private Photo getLatestPhoto() {
            Cursor c = null;
            try {
                c = mContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI,
                        PROJECTION,
                        null,
                        null,
                        ORDER_BY);
                c.moveToFirst();

                Long id = c.getLong(0);
                Integer dateTaken = c.getInt(1);
                String path = c.getString(2);

                Photo photo = new Photo(id, path, dateTaken);
                Log.d(TAG, "Photo created: " + photo);

                return photo;
            } catch (Exception ex) {
                // Noop
            } finally {
                if (c != null) c.close();
            }

            return null;
        }
    }
}
