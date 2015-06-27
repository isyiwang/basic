package com.ggl.android.basic;

import android.database.Cursor;

/**
 * Simple Photo model class
 *
 * Created by isaac on 6/27/15.
 */
public class Photo {
    private final Integer mTimestamp;
    private final String mPath;
    private final Long mId;

    public Photo(Long id, String path, Integer timestamp) {
        mId = id;
        mPath = path;
        mTimestamp = timestamp;
    }

    public String getPath() {
        return mPath;
    }

    public Long getId() {
        return mId;
    }

    public Integer getTimestamp() {
        return mTimestamp;
    }

    @Override
    public String toString() {
        return "Photo[" + mId + "]: " + mPath;
    }
}
