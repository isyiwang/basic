package com.ggl.android.basic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;


public class MainActivity extends ActionBarActivity {
    private PhotoSetManager mManager = new PhotoSetManager();
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PHOTOSET_TIMESTAMP_EXTRA = "PHOTOSET_TIMESTAMP_EXTRA";
    private ViewPager mViewPager;
    private PhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        displayPhotoSetFromIntent();
    }

    private void displayPhotoSetFromIntent() {
        if (getIntent() == null || getIntent().getExtras() == null) return;

        Long timestamp = getIntent().getExtras().getLong(PHOTOSET_TIMESTAMP_EXTRA, 0);
        PhotoSetManager.PhotoSet photoSet = PhotoSetManager.getInstance().getPhotoSetForTimestamp(timestamp);
        if (photoSet == null) return;

        mAdapter = new PhotoAdapter(getSupportFragmentManager(), photoSet);
        mViewPager.setAdapter(mAdapter);
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

    public static class PhotoAdapter extends FragmentPagerAdapter {
        private final PhotoSetManager.PhotoSet mPhotoSet;

        public PhotoAdapter(FragmentManager fm, PhotoSetManager.PhotoSet photoSet) {
            super(fm);

            mPhotoSet = photoSet;
        }

        @Override
        public Fragment getItem(int position) {
            Photo photo = mPhotoSet.get(position);
            return ImageFragment.newInstance(photo.getPath());
        }

        @Override
        public int getCount() {
            return mPhotoSet.size();
        }
    }

    public static class ImageFragment extends Fragment {
        public static final String PATH_BUNDLE_KEY = "PATH_BUNDLE_KEY";
        private String mPath;

        static ImageFragment newInstance(String path) {
            ImageFragment f = new ImageFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putString(PATH_BUNDLE_KEY, path);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle bundle = getArguments();
            mPath = (bundle == null) ? "" : bundle.getString(PATH_BUNDLE_KEY);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_image, container, false);
            ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
            File imgFile = new File(mPath);
            Picasso.with(getActivity()).load(imgFile).fit().centerInside().into(imageView);

            return v;
        }
    }
}
