package org.exesoft.charbakg.Adapter;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.livestock_list_item, null);
        TextView  uid   = convertView.findViewById(R.id.livestockUid);
        TextView  serial   = convertView.findViewById(R.id.livestockSerial);
        TextView  sex = convertView.findViewById(R.id.livestockSex);
        final ImageView image = convertView.findViewById(R.id.livestockItemImage);
        TextView  spicies = convertView.findViewById(R.id.livestockSpicies);
        TextView  spiciesCount = convertView.findViewById(R.id.livestockSpiciesCount);
        TextView added  = convertView.findViewById(R.id.livestockAdded);
        added.setText( new SimpleDateFormat("dd.MM.yyyy").format(items.get(position).get("added")));
        spicies.setVisibility(View.GONE);
        spiciesCount.setVisibility(View.GONE);
        serial.setText(items.get(position).get("serial").toString());
        uid.setText(items.get(position).get("uid").toString());
        sex.setText(items.get(position).get("sex").toString());
        image.setImageBitmap(icon);
        // TO DO Migrate this to on item click listener
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageRef.child("krs/" + items.get(position).get("uid").toString());
        islandRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                StorageReference iRef  = null;
                for (StorageReference item : listResult.getItems()) {
                    if(item != null) {
                        iRef = item;
                    }
                }
                if(iRef != null) {
                    final long ONE_MEGABYTE = 1024 * 1024;
                    iRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Data for "images/island.jpg" is returns, use this as needed
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            image.setImageBitmap(bmp);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.d(TAG, "On storage failure");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
