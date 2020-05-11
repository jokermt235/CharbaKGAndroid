package org.exesoft.charbakg.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.exesoft.charbakg.Model.Yield;
import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.YieldActivity;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class YieldAdapter extends BaseAdapter {

    private ArrayList<Map<String,Object>> items = new ArrayList();

    private YieldActivity activity;

    public  YieldAdapter(YieldActivity activity){
        this.activity = activity;
    }

    public void setItems(ArrayList<Map<String,Object>> items){
        this.items = items;
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

        //if(convertView == null)
        {
            convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.yield_list_item, null);
            //Set date
            TextView  date = convertView.findViewById(R.id.yieldListItemDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            date.setText(formatter.format(items.get(position).get("added")));
            // Set amount
            TextView  amount = convertView.findViewById(R.id.yieldListItemAmount);
            amount.setText(items.get(position).get("amount").toString());
        }

        return convertView;
    }
}
