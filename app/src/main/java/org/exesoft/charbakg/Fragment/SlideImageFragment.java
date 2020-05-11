package org.exesoft.charbakg.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import org.exesoft.charbakg.R;

import java.util.ArrayList;

public class SlideImageFragment extends Fragment {
    private static String TAG = "SlideImageFragment";
    private ArrayList<Bitmap> items = new ArrayList();
    private Bitmap item;
    private int position;
    public SlideImageFragment(ArrayList<Bitmap> items,int position){
        this.items = items;
        item = items.get(position);
        this.position = position;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView method callback");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.image_layout, container, false);
        ImageView imageView = rootView.findViewById(R.id.sliderImageView);
        imageView.setImageBitmap(items.get(position));
        if(items.get(position) == null){
            Log.d(TAG, "The bitmap image is empty");
        }
        return rootView;
    }

}
