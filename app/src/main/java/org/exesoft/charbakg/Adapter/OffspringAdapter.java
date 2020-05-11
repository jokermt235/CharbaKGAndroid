package org.exesoft.charbakg.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.exesoft.charbakg.Model.Offspring;
import org.exesoft.charbakg.R;

import java.util.ArrayList;

public class OffspringAdapter extends BaseAdapter {
    ArrayList<Offspring> items = new ArrayList();
    private AppCompatActivity activity;

    public void setItems(ArrayList<Offspring> items){
        this.items = items;
    }

    public OffspringAdapter(AppCompatActivity activity){
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.livestock_list_item, null);
            TextView serial   = convertView.findViewById(R.id.livestockSerial);
            TextView  sex = convertView.findViewById(R.id.livestockSex);
            ImageView image = convertView.findViewById(R.id.livestockItemImage);
            serial.setText(items.get(position).getSerial());
            sex.setText(items.get(position).getSex());
            Bitmap icon  = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_livestock_icon_foreground);
            image.setImageBitmap(icon);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
