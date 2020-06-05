package org.exesoft.charbakg.Callback.KrsAdapter;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class KrsAdapterItemClick implements View.OnClickListener {
    private static  String TAG = "KrsAdapterItemClick";
    Map<String, Object> item = new HashMap<>();
    public KrsAdapterItemClick(Map<String, Object> item){
        this.item = item;
    }
    @Override
    public void onClick(View v) {
        Log.d(TAG, "Item clicked:" +  item.toString());
    }
}
