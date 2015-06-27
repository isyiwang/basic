package com.ggl.android.basic;

/**
 * This class manages listening to changes for a phone's Gallery (image library) and notifies
 * a listener when a photo is added.
 * 
 * Created by isaac on 6/27/15.
 */
public class GalleryChangeManager {
    private static GalleryChangeManager sManager;

    public static GalleryChangeManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new GalleryChangeManager();
        return sManager;
    }
}
