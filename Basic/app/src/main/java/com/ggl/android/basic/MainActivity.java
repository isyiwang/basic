package com.ggl.android.basic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private PhotoSetManager mManager = new PhotoSetManager();
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PHOTOSET_TIMESTAMP_EXTRA = "PHOTOSET_TIMESTAMP_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayPhotoSetFromIntent();
    }

    private void displayPhotoSetFromIntent() {
        if (getIntent() == null) return;

        Long timestamp = getIntent().getExtras().getLong(PHOTOSET_TIMESTAMP_EXTRA, 0);
        PhotoSetManager.PhotoSet photoSet = PhotoSetManager.getInstance().getPhotoSetForTimestamp(timestamp);
        if (photoSet == null) return;

        Log.d(TAG, "Displaying photo set with " + photoSet.size() + " photos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
