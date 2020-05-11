package org.exesoft.charbakg.Callback;

import java.util.ArrayList;
import java.util.Map;

public class OnSimpleLoaderResult {
    private ArrayList<Map<String, Object>> items = new ArrayList<>();
    public void onResult(ArrayList<Map<String,Object>> items){
        this.items = items;
    }
}
