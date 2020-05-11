package org.exesoft.charbakg.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FeedAdapter extends BaseAdapter {
    private static final  String TAG = "FeedAdapter";
    private AppCompatActivity activity;
    private ArrayList<Map<String,Object>> items = new ArrayList<>();
    public FeedAdapter(AppCompatActivity activity, ArrayList<Map<String,Object>> items){
        this.items  = items;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView method");
        convertView = LayoutInflater.from(activity).inflate(R.layout.feed_list_item,parent,false);
        TextView feedName = convertView.findViewById(R.id.feedListItemName);
        TextView feedDate = convertView.findViewById(R.id.feedListItemTime);
        TextView feedUnit = convertView.findViewById(R.id.feedListItemUnit);
        TextView feedAmount = convertView.findViewById(R.id.feedListItemSum);
        feedAmount.setText(items.get(position).get("amount").toString());
        feedName.setText(items.get(position).get("name").toString());
        Date date = new Date();
        date.setTime((long)items.get(position).get("added"));//items.get(position).get("added");
        feedDate.setText(DateInput.getFormatedDate(date));
        feedUnit.setText(items.get(position).get("unit").toString());
        return convertView;
    }
}
