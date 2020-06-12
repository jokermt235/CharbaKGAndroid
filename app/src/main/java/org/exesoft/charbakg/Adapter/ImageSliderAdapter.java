package org.exesoft.charbakg.Adapter;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.exesoft.charbakg.Fragment.SlideImageFragment;

import java.util.ArrayList;

public class ImageSliderAdapter extends FragmentStatePagerAdapter {

    private static String TAG = "ImageSliderAdapter";
    private ArrayList<Bitmap> items = new ArrayList();

    public void addItem(Bitmap bitmap){
        items.add(bitmap);
    }
    public ImageSliderAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem Method");
        return new SlideImageFragment(items,position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public ArrayList<Bitmap> getItems(){
        return  items;
    }
}
