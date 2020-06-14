package org.exesoft.charbakg.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.exesoft.charbakg.Callback.KrsAdapter.KrsAdapterItemClick;
import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.LsformActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class LivestockAdapter extends BaseAdapter {
    private static String TAG = "LivestockAdapter";
    ArrayList<Map<String,Object>> items = new ArrayList();
    private AppCompatActivity activity;

    private Bitmap icon;

    public void setItems(ArrayList<Map<String,Object>> items){
        this.items = items;
    }

    public LivestockAdapter(AppCompatActivity activity){
        this.activity = activity;
        icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_krs_listview_icon_foreground);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.livestock_list_item, null);
            holder = new ViewHolder();
            holder.uid   = convertView.findViewById(R.id.livestockUid);
            holder.serial   = convertView.findViewById(R.id.livestockSerial);
            holder.sex = convertView.findViewById(R.id.livestockSex);
            holder.image = convertView.findViewById(R.id.livestockItemImage);
            holder.spicies = convertView.findViewById(R.id.livestockSpicies);
            holder.spiciesCount = convertView.findViewById(R.id.livestockSpiciesCount);
            holder.added  = convertView.findViewById(R.id.livestockAdded);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.added.setText( new SimpleDateFormat("dd.MM.yyyy").format(items.get(position).get("added")));
        holder.spicies.setVisibility(View.GONE);
        holder.spiciesCount.setVisibility(View.GONE);
        holder.serial.setText(items.get(position).get("serial").toString());
        holder.uid.setText(items.get(position).get("uid").toString());
        holder.sex.setText(items.get(position).get("sex").toString());
        holder.image.setImageBitmap(icon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, (String) items.get(position).get("uid"));
                Intent intent = new Intent(activity.getApplicationContext(), LsformActivity.class);
                intent.putExtra("uid", (String) items.get(position).get("uid"));
                activity.startActivity(intent);
                activity.finish();
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView  uid ;
        TextView  serial;
        TextView  sex;
        ImageView image;
        TextView  spicies;
        TextView  spiciesCount;
        TextView added;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
