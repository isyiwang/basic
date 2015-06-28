package com.ggl.android.basic;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by fangbrian on 6/27/15.
 */
public class BasicApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, BasicService.class));
    }
}
