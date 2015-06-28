package com.ggl.android.basic;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 6/27/15.
 */
public class PhotoSetManager implements GalleryChangeManager.IOnGalleryChangedListener {
    public interface IPhotoSetListener  {
        public void onPhotoSetAdded(PhotoSet photoSet);
    }
    public static class PhotoSet extends ArrayList<Photo> {}
    public static String TAG = PhotoSetManager.class.getSimpleName();

    private static final int PHOTOSET_POST_DELAY_MS = 5000;
    private PhotoSet photoSet = new PhotoSet();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private IPhotoSetListener mListener;

    private static PhotoSetManager sManager;

    public static PhotoSetManager getInstance() {
        if (sManager == null) {
            sManager = new PhotoSetManager();
            GalleryChangeManager.getInstance().addListener(sManager);
        }

        return sManager;
    }

    @Override
    public void onPhotoAdded(Photo photo) {
        Log.d(TAG, "Photo received: " + photo);
        if (runnable != null) handler.removeCallbacks(runnable);

        photoSet.add(photo);
        runnable = new Runnable() {

            @Override
            public void run() {
                // TODO: Send push notification
                if (mListener != null) {
                    mListener.onPhotoSetAdded(photoSet);
                }
                photoSet.clear();

            }
        };

        handler.postDelayed(runnable, PHOTOSET_POST_DELAY_MS);
    }

    public void setListener(IPhotoSetListener listener) {
        mListener = listener;
    }


}
