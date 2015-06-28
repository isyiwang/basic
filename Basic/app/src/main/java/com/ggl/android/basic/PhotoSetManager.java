package com.ggl.android.basic;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by isaac on 6/27/15.
 */
public class PhotoSetManager implements GalleryChangeManager.IOnGalleryChangedListener {
    public interface IPhotoSetListener  {
        public void onPhotoSetAdded(PhotoSet photoSet, Long timestamp);
    }
    public static class PhotoSet extends ArrayList<Photo> {}
    public static String TAG = PhotoSetManager.class.getSimpleName();

    private static final int PHOTOSET_POST_DELAY_MS = 5000;
    private PhotoSet photoSet = new PhotoSet();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private IPhotoSetListener mListener;
    private HashMap<Long, PhotoSet> mPhotosetMap = new HashMap<>();

    private static PhotoSetManager sManager;

    public static PhotoSetManager getInstance() {
        if (sManager == null) {
            sManager = new PhotoSetManager();
            GalleryChangeManager.getInstance().addListener(sManager);
        }

        return sManager;
    }

    public PhotoSet getPhotoSetForTimestamp(Long timestamp) {
        return mPhotosetMap.get(timestamp);
    }

    @Override
    public void onPhotoAdded(Photo photo) {
        Log.d(TAG, "Photo received: " + photo);
        if (runnable != null) handler.removeCallbacks(runnable);

        photoSet.add(photo);
        runnable = new Runnable() {

            @Override
            public void run() {
                Long ts = System.currentTimeMillis();

                if (mListener != null && photoSet.size() > 1) {
                    mListener.onPhotoSetAdded(photoSet, ts);
                }
                mPhotosetMap.put(ts, photoSet);
                photoSet = new PhotoSet();
            }
        };

        handler.postDelayed(runnable, PHOTOSET_POST_DELAY_MS);
    }

    public void setListener(IPhotoSetListener listener) {
        mListener = listener;
    }


}
