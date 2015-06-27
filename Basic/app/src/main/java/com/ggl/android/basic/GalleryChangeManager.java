package com.ggl.android.basic;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.widget.Toast;

/**
 * This class manages listening to changes for a phone's Gallery (image library) and notifies
 * a listener when a photo is added.
 * 
 * Created by isaac on 6/27/15.
 */
public class GalleryChangeManager {
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

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public ImageContentObserver(Handler handler) {
            super(handler);

            Toast.makeText(mContext, "Image Content Observer created on thread: " + handler,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Toast.makeText(mContext, "Change received", Toast.LENGTH_SHORT).show();
        }
    }
}
