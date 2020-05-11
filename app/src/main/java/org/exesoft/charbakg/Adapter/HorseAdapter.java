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

import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class HorseAdapter extends BaseAdapter {
    ArrayList<Map<String,Object>> items = new ArrayList();
    private AppCompatActivity activity;

    private Bitmap icon ;


    public void setItems(ArrayList<Map<String,Object>> items){
        this.items = items;
    }

    public HorseAdapter(AppCompatActivity activity){
        this.activity = activity;
        icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_horse_listview_icon_foreground);
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
        convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.horse_listview_item, null);
        TextView name   = convertView.findViewById(R.id.horseListViewItemName);
        TextView  sex = convertView.findViewById(R.id.horseListViewItemSex);
        ImageView image = convertView.findViewById(R.id.horseListViewItemImage);
        TextView added = convertView.findViewById(R.id.horseListViewItemAdded);
        Date date = new Date();
        date.setTime((long)items.get(position).get("added"));
        added.setText(DateInput.getFormatedDate(date));
        name.setText(items.get(position).get("name").toString());
        sex.setText(items.get(position).get("sex").toString());

        image.setImageBitmap(icon);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
