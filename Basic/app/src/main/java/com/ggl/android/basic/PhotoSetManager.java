package com.ggl.android.basic;

import android.util.Log;

/**
 * Created by isaac on 6/27/15.
 */
public class PhotoSetManager implements GalleryChangeManager.IOnGalleryChangedListener {
    public static String TAG = PhotoSetManager.class.getSimpleName();

    @Override
    public void onPhotoAdded(Photo photo) {
        Log.d(TAG, "Photo received: " + photo);
    }
}
