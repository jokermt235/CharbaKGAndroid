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
import org.exesoft.charbakg.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class MrsAdapter extends BaseAdapter {
    ArrayList<Map<String,Object>> items = new ArrayList();
    private AppCompatActivity activity;

    private Bitmap icon;

    public void setItems(ArrayList<Map<String,Object>> items){
        this.items = items;
    }

    public MrsAdapter(AppCompatActivity activity){
        this.activity = activity;
        icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_sheep_listview_icon_foreground);
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
        convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.mrs_listview_item,parent ,false);
        TextView serial   = convertView.findViewById(R.id.livestockSerial);
        serial.setVisibility(View.GONE);
        TextView  spicies = convertView.findViewById(R.id.livestockSpicies);
        TextView  spiciesCount = convertView.findViewById(R.id.livestockSpiciesCount);
        ImageView image = convertView.findViewById(R.id.livestockItemImage);
        TextView  mrsAdded = convertView.findViewById(R.id.mrsAdded);
        //serial.setText(items.get(position).get());
        spiciesCount.setText(items.get(position).get("amount").toString());
        spicies.setText(items.get(position).get("species").toString());
        mrsAdded.setText( new SimpleDateFormat("dd.MM.yyyy").format(items.get(position).get("added")));
        image.setImageBitmap(icon);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
